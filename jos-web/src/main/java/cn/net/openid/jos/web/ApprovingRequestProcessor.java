/**
 * Created on 2008-5-29 下午05:08:36
 */
package cn.net.openid.jos.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openid4java.association.AssociationException;
import org.openid4java.message.AuthFailure;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.DirectError;
import org.openid4java.message.Message;
import org.openid4java.message.MessageException;
import org.openid4java.message.MessageExtension;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.message.sreg.SRegRequest;
import org.openid4java.message.sreg.SRegResponse;
import org.openid4java.server.ServerException;
import org.openid4java.server.ServerManager;

import cn.net.openid.jos.domain.Persona;
import cn.net.openid.jos.domain.Site;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.service.JosService;

/**
 * Approving request processor.
 * 
 * @author Sutra Zhou
 * 
 */
public class ApprovingRequestProcessor {
	private static final Log log = LogFactory
			.getLog(ApprovingRequestProcessor.class);

	public static final int ALLOW_AUTO = 0;
	public static final int ALLOW_ONCE = 1;
	public static final int ALLOW_FOREVER = 2;
	public static final int DENY = -1;

	private final JosService josService;
	private final ServerManager serverManager;

	private final HttpServletRequest httpReq;
	private final HttpServletResponse httpResp;

	private final UserSession userSession;
	private final User user;

	private final ApprovingRequest checkIdRequest;
	private final AuthRequest authRequest;
	private final String realm;

	/**
	 * @param httpReq
	 *            the http request
	 * @param httpResp
	 *            the http response
	 * @param josService
	 *            the jos service
	 * @param serverManager
	 *            the serverManager
	 * @param userSession
	 * @param authRequest
	 */
	public ApprovingRequestProcessor(HttpServletRequest httpReq,
			HttpServletResponse httpResp, JosService josService,
			ServerManager serverManager, ApprovingRequest checkIdRequest) {
		this.httpReq = httpReq;
		this.httpResp = httpResp;

		this.josService = josService;
		this.serverManager = serverManager;

		this.userSession = WebUtils.getOrCreateUserSession(this.httpReq
				.getSession());
		this.user = userSession.getUser();

		this.checkIdRequest = checkIdRequest;
		this.authRequest = checkIdRequest.getAuthRequest();
		this.realm = this.authRequest.getRealm();
	}

	public void checkId() throws IOException {
		if (this.isLoggedInUserOwnClaimedId()) {
			this.checkApproval();
		} else {
			// redirect to login page.
			String url = "login?token="
					+ userSession.addApprovingRequest(checkIdRequest);
			httpResp.sendRedirect(url);
		}
	}

	/**
	 * If user logged in, do check site, otherwise redirect to login page.
	 * 
	 * @param allowType
	 * @param persona
	 * @throws IOException
	 */
	public void checkId(int allowType, Persona persona) throws IOException {
		if (this.isLoggedInUserOwnClaimedId()) {
			switch (allowType) {
			case ALLOW_ONCE:
				josService.allow(this.user, this.realm, persona, false);
				this.redirectToReturnToPage(true, persona);
				break;
			case ALLOW_FOREVER:
				josService.allow(this.user, this.realm, persona, true);
				this.redirectToReturnToPage(true, persona);
				break;
			case DENY:
			default:
				this.redirectToReturnToPage(false, null);
				break;
			}
		} else {
			// redirect to login page.
			String url = "login?token="
					+ userSession.addApprovingRequest(checkIdRequest);
			httpResp.sendRedirect(url);
		}
	}

	private boolean isLoggedInUserOwnClaimedId() {
		boolean ret;
		if (userSession.isLoggedIn()
				&& this.authRequest.getIdentity().equals(
						userSession.getUser().getIdentifier())) {
			ret = true;
		} else {
			ret = false;
		}
		return ret;
	}

	/**
	 * If this site is always approve, redirect to return_to page, otherwise
	 * redirect to approving page.
	 * 
	 * @throws IOException
	 */
	private void checkApproval() throws IOException {
		boolean approved;
		Site site = josService.getSite(user, authRequest.getRealm());
		if (site != null && site.isAlwaysApprove()) {
			boolean sreg = authRequest.hasExtension(SRegMessage.OPENID_NS_SREG);
			if ((sreg && site.getPersona() != null)
					|| (!sreg && site.getPersona() == null)) {
				approved = true;
			} else {
				approved = false;
			}
		} else {
			approved = false;
		}

		if (approved) {
			josService.updateApproval(user, authRequest.getRealm());

			// return to `return_to' page.
			redirectToReturnToPage(true, site.getPersona());
		} else {
			// redirect to approving page.
			String url = "approving?token="
					+ userSession.addApprovingRequest(checkIdRequest);
			httpResp.sendRedirect(url);
		}
	}

	/**
	 * Redirect to return_to page.
	 * 
	 * @param httpReq
	 * @param httpResp
	 * @param approved
	 * @param persona
	 * @throws MessageException
	 * @throws IOException
	 */
	private void redirectToReturnToPage(boolean approved, Persona persona)
			throws IOException {
		Message response;
		// interact with the user and obtain data needed to continue

		String userSelectedClaimedId = null;
		Boolean authenticatedAndApproved = approved;

		String opLocalId = null;
		// if the user chose a different claimed_id than the one in request
		if (userSelectedClaimedId != null
				&& userSelectedClaimedId.equals(authRequest.getClaimed())) {
			// opLocalId = lookupLocalId(userSelectedClaimedId);
		}

		boolean signNow = false;
		response = this.serverManager.authResponse(authRequest, opLocalId,
				userSelectedClaimedId, authenticatedAndApproved.booleanValue(),
				signNow);

		if (response instanceof DirectError) {
			directResponse(response.keyValueFormEncoding());
		} else if (response instanceof AuthFailure) {
			httpResp.sendRedirect(response.getDestinationUrl(true));
		} else {
			if (authenticatedAndApproved) {
				try {
					addExtension(response);
					addSRegExtension(response, persona);
				} catch (MessageException e) {
					log.error("", e);
				}
			}

			try {
				serverManager.sign((AuthSuccess) response);
			} catch (ServerException e) {
				log.error("", e);
			} catch (AssociationException e) {
				log.error("", e);
			}

			// caller will need to decide which of the following to use:

			// option1: GET HTTP-redirect to the return_to URL
			String destUrl = response.getDestinationUrl(true);
			httpResp.sendRedirect(destUrl);

			// option2: HTML FORM Redirection
			// RequestDispatcher dispatcher =
			// getServletContext().getRequestDispatcher("formredirection.jsp");
			// httpReq.setAttribute("prameterMap",
			// response.getParameterMap());
			// httpReq.setAttribute("destinationUrl",
			// response.getDestinationUrl(false));
			// dispatcher.forward(request, response);
			// return null;
		}

		userSession.removeApprovingRequest(checkIdRequest.getToken());
	}

	private String directResponse(String response) throws IOException {
		WebUtils.writeResponse(httpResp, response);
		return null;
	}

	private void addSRegExtension(Message response, Persona persona)
			throws MessageException {
		if (authRequest.hasExtension(SRegMessage.OPENID_NS_SREG)) {
			MessageExtension ext = authRequest
					.getExtension(SRegMessage.OPENID_NS_SREG);
			if (ext instanceof SRegRequest) {
				SRegRequest sregReq = (SRegRequest) ext;
				// data released by the user
				if (persona != null) {
					Map<String, String> userDataSReg = persona.toMap();

					SRegResponse sregResp = SRegResponse.createSRegResponse(
							sregReq, userDataSReg);
					// (alternatively) manually add attribute values
					// sregResp.addAttribute("email", email);
					response.addExtension(sregResp);
				}
			} else {
				throw new UnsupportedOperationException("TODO");
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void addExtension(Message response) throws MessageException {
		if (authRequest.hasExtension(AxMessage.OPENID_NS_AX)) {
			MessageExtension ext = authRequest
					.getExtension(AxMessage.OPENID_NS_AX);
			if (ext instanceof FetchRequest) {
				FetchRequest fetchReq = (FetchRequest) ext;
				Map required = fetchReq.getAttributes(true);
				Map optional = fetchReq.getAttributes(false);
				if (required.containsKey("email")
						|| optional.containsKey("email")) {
					Map userDataExt = new HashMap();
					// userDataExt.put("email", userData.get(3));

					FetchResponse fetchResp = FetchResponse
							.createFetchResponse(fetchReq, userDataExt);
					// (alternatively) manually add attribute values
					fetchResp.addAttribute("email",
							"http://schema.openid.net/contact/email", "email");
					response.addExtension(fetchResp);
				}
			} else // if (ext instanceof StoreRequest)
			{
				throw new UnsupportedOperationException("TODO");
			}
		}
	}
}
