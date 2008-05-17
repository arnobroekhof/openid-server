/**
 * Created on 2008-5-12 00:55:46
 */
package cn.net.openid.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.association.AssociationException;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.DirectError;
import org.openid4java.message.Message;
import org.openid4java.message.MessageException;
import org.openid4java.message.MessageExtension;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.message.sreg.SRegRequest;
import org.openid4java.message.sreg.SRegResponse;
import org.openid4java.server.ServerException;
import org.openid4java.server.ServerManager;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.web.AbstractJosSimpleFormController;
import cn.net.openid.web.UserSession;

/**
 * @author Sutra Zhou
 * 
 */
public class ApprovingController extends AbstractJosSimpleFormController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#isFormSubmission(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean isFormSubmission(HttpServletRequest request) {
		return request.getParameter("allow_once") != null
				|| request.getParameter("allow_forever") != null
				|| request.getParameter("deny") != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Map<String, List<String>> referenceData(HttpServletRequest request)
			throws Exception {
		Map<String, List<String>> referenceData = new HashMap<String, List<String>>(
				2);
		ParameterList openidRequest = (ParameterList) request.getSession()
				.getAttribute("request");
		AuthRequest authReq = AuthRequest.createAuthRequest(openidRequest,
				this.serverManager.getRealmVerifier());
		if (authReq.hasExtension(SRegMessage.OPENID_NS_SREG)) {
			MessageExtension ext = authReq
					.getExtension(SRegMessage.OPENID_NS_SREG);
			if (ext instanceof SRegRequest) {
				SRegRequest sregReq = (SRegRequest) ext;
				referenceData.put("required", sregReq.getAttributes(true));
				referenceData.put("optional", sregReq.getAttributes(false));
			}
		}
		return referenceData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		Boolean approved;
		if (request.getParameter("allow_once") != null) {
			approved = Boolean.TRUE;
		} else if (request.getParameter("allow_forever") != null) {
			approved = Boolean.TRUE;
			UserSession us = (UserSession) request.getSession().getAttribute(
					"userSession");
			// TODO: this.daoFacade.allowForever(us.getUserId(), );
		} else if (request.getParameter("deny") != null) {
			approved = Boolean.FALSE;
		} else {
			approved = Boolean.FALSE;
		}

		response(this.serverManager, request, response, (ParameterList) request
				.getSession().getAttribute("request"), approved);
		return null;
	}

	public static void response(ServerManager manager,
			HttpServletRequest httpReq, HttpServletResponse httpResp,
			ParameterList request, Boolean approved) throws MessageException,
			IOException {
		Message response;
		// interact with the user and obtain data needed to continue

		String userSelectedClaimedId = null;
		Boolean authenticatedAndApproved = approved;
		String email = "user@example.org";

		// --- process an authentication request ---
		AuthRequest authReq = AuthRequest.createAuthRequest(request, manager
				.getRealmVerifier());

		String opLocalId = null;
		// if the user chose a different claimed_id than the one in request
		if (userSelectedClaimedId != null
				&& userSelectedClaimedId.equals(authReq.getClaimed())) {
			// opLocalId = lookupLocalId(userSelectedClaimedId);
		}

		boolean signNow = false;
		response = manager.authResponse(request, opLocalId,
				userSelectedClaimedId, authenticatedAndApproved.booleanValue(),
				signNow);

		if (response instanceof DirectError) {
			directResponse(httpResp, response.keyValueFormEncoding());
		} else {
			addExt(manager, response, authReq);
			addSRegExtension(manager, response, authReq);
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
	}

	private static String directResponse(HttpServletResponse httpResp,
			String response) throws IOException {
		ServerController.writeResponse(httpResp, response);
		return null;
	}

	private static void addSRegExtension(ServerManager manager,
			Message response, AuthRequest authReq) throws MessageException {
		if (authReq.hasExtension(SRegMessage.OPENID_NS_SREG)) {
			MessageExtension ext = authReq
					.getExtension(SRegMessage.OPENID_NS_SREG);
			if (ext instanceof SRegRequest) {
				SRegRequest sregReq = (SRegRequest) ext;
				List required = sregReq.getAttributes(true);
				List optional = sregReq.getAttributes(false);
				if (required.contains("email") || optional.contains("email")) {
					// data released by the user
					Map userDataSReg = new HashMap();
					userDataSReg.put("email", "user@example.com");

					SRegResponse sregResp = SRegResponse.createSRegResponse(
							sregReq, userDataSReg);
					// (alternatively) manually add attribute values
					// sregResp.addAttribute("email", email);
					response.addExtension(sregResp);
					try {
						manager.sign((AuthSuccess) response);
					} catch (ServerException e) {
					} catch (AssociationException e) {
					}
				}
			} else {
				throw new UnsupportedOperationException("TODO");
			}
		}
	}

	private static void addExt(ServerManager manager, Message response,
			AuthRequest authReq) throws MessageException {
		if (authReq.hasExtension(AxMessage.OPENID_NS_AX)) {
			MessageExtension ext = authReq.getExtension(AxMessage.OPENID_NS_AX);
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
