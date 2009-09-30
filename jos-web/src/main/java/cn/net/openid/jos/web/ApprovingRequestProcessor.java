/**
 * Copyright (c) 2006-2009, Redv.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Redv.com nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * Created on 2008-5-29 17:08:36
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
 */
public class ApprovingRequestProcessor {
	/**
	 * The logger.
	 */
	private static final Log LOG = LogFactory
			.getLog(ApprovingRequestProcessor.class);

	/**
	 * The value of allow auto.
	 */
	public static final int ALLOW_AUTO = 0;

	/**
	 * The value of allow once.
	 */
	public static final int ALLOW_ONCE = 1;

	/**
	 * The value of allow forever.
	 */
	public static final int ALLOW_FOREVER = 2;

	/**
	 * The value of deny.
	 */
	public static final int DENY = -1;

	/**
	 * The JOS service.
	 */
	private final JosService josService;

	/**
	 * The OpenID server manager.
	 */
	private final ServerManager serverManager;

	/**
	 * The HTTP Servlet request.
	 */
	private final HttpServletRequest httpReq;

	/**
	 * The HTTP Servlet response.
	 */
	private final HttpServletResponse httpResp;

	/**
	 * The user session.
	 */
	private final UserSession userSession;

	/**
	 * The user.
	 */
	private final User user;

	/**
	 * The approving request.
	 */
	private final ApprovingRequest checkIdRequest;

	/**
	 * The OpenID authentication request.
	 */
	private final AuthRequest authRequest;

	/**
	 * The realm.
	 */
	private final String realm;

	/**
	 * Construct a new {@link ApprovingRequestProcessor}.
	 * 
	 * @param httpReq
	 *            the http request
	 * @param httpResp
	 *            the http response
	 * @param josService
	 *            the jos service
	 * @param serverManager
	 *            the serverManager
	 * @param checkIdRequest
	 *            the approving request
	 */
	public ApprovingRequestProcessor(final HttpServletRequest httpReq,
			final HttpServletResponse httpResp, final JosService josService,
			final ServerManager serverManager,
			final ApprovingRequest checkIdRequest) {
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

	/**
	 * Execute OpenID checkId.
	 * 
	 * @throws IOException
	 *             if HTTP error
	 */
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
	 *            the allow type: once, forever or deny
	 * @param persona
	 *            the persona selected
	 * @throws IOException
	 *             if HTTP error
	 */
	public void checkId(final int allowType, final Persona persona)
			throws IOException {
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

	/**
	 * Check the logged in user own the claimed ID.
	 * 
	 * @return true if own, otherwise false.
	 */
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
	 * @throws IOException if HTTP error
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
	 * @param approved is approved
	 * @param persona the persona to send to the request realm
	 * @throws IOException if HTTP error
	 */
	private void redirectToReturnToPage(final boolean approved,
			final Persona persona) throws IOException {
		Message response;
		// interact with the user and obtain data needed to continue

		String userSelectedClaimedId = null;
		Boolean authenticatedAndApproved = approved;

		String opLocalId = null;
		// if the user chose a different claimed_id than the one in request
		if (userSelectedClaimedId != null
				&& userSelectedClaimedId.equals(authRequest.getClaimed())) {
			// TODO
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
					LOG.error("", e);
				}
			}

			try {
				serverManager.sign((AuthSuccess) response);
			} catch (ServerException e) {
				LOG.error("", e);
			} catch (AssociationException e) {
				LOG.error("", e);
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

	/**
	 * Write string to the HTTP response out stream.
	 * 
	 * @param response
	 *            the response string to write to
	 * @return always returns null
	 * @throws IOException
	 *             if write failed
	 */
	private String directResponse(final String response) throws IOException {
		WebUtils.writeResponse(httpResp, response);
		return null;
	}

	/**
	 * Add Simple Register extension message to the response message.
	 * 
	 * @param response
	 *            the response message to add to
	 * @param persona
	 *            the persona
	 * @throws MessageException
	 *             if add extension failed
	 */
	private void addSRegExtension(final Message response, final Persona persona)
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

	/**
	 * Add extension to the response message.
	 * 
	 * @param response
	 *            the response message to add to
	 * @throws MessageException
	 *             if add failed
	 */
	@SuppressWarnings("unchecked")
	private void addExtension(final Message response) throws MessageException {
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
			} else { // if (ext instanceof StoreRequest)
				throw new UnsupportedOperationException("TODO");
			}
		}
	}
}
