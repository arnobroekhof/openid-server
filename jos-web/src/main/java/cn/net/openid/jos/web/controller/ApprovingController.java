/**
 * Created on 2008-5-12 00:55:46
 */
package cn.net.openid.jos.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.message.sreg.SRegRequest;
import org.openid4java.message.sreg.SRegResponse;
import org.openid4java.server.ServerException;
import org.openid4java.server.ServerManager;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.UserSession;
import cn.net.openid.jos.web.WebUtils;
import cn.net.openid.jos.web.form.ApprovingForm;
import cn.net.openid.jos.web.form.ApprovingForm.Attribute;

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
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.Errors)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request,
			Object command, Errors errors) throws Exception {
		ApprovingForm form = (ApprovingForm) command;
		String token = request.getParameter("token");
		form.setToken(token);

		AuthRequest authReq = WebUtils.getOrCreateUserSession(
				request.getSession()).getRequest(token);
		if (authReq.hasExtension(SRegMessage.OPENID_NS_SREG)) {
			MessageExtension ext = authReq
					.getExtension(SRegMessage.OPENID_NS_SREG);
			if (ext instanceof SRegRequest) {
				SRegRequest sregReq = (SRegRequest) ext;
				List<String> required = sregReq.getAttributes(true);
				List<String> optional = sregReq.getAttributes(false);
				for (String attributeName : required) {
					Attribute attribute = form.new Attribute(attributeName, "",
							true, "label." + attributeName);
					form.getAttributes().add(attribute);
				}
				for (String attributeName : optional) {
					Attribute attribute = form.new Attribute(attributeName, "",
							false, "label." + attributeName);
					form.getAttributes().add(attribute);
				}
			}
		}
		return super.referenceData(request, command, errors);
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
		UserSession userSession = WebUtils.getOrCreateUserSession(request
				.getSession());
		AuthRequest authReq = userSession.removeRequest(request
				.getParameter("token"));

		if (request.getParameter("allow_once") != null) {
			approved = Boolean.TRUE;
			this.josService.allow(userSession.getUserId(), authReq.getRealm(),
					false);
		} else if (request.getParameter("allow_forever") != null) {
			approved = Boolean.TRUE;
			this.josService.allow(userSession.getUserId(), authReq.getRealm(),
					true);
		} else if (request.getParameter("deny") != null) {
			approved = Boolean.FALSE;
		} else {
			approved = Boolean.FALSE;
		}

		response(this.serverManager, request, response, authReq, approved);
		return null;
	}

	public static void response(ServerManager manager,
			HttpServletRequest httpReq, HttpServletResponse httpResp,
			AuthRequest authReq, Boolean approved) throws MessageException,
			IOException {
		Message response;
		// interact with the user and obtain data needed to continue

		String userSelectedClaimedId = null;
		Boolean authenticatedAndApproved = approved;

		String opLocalId = null;
		// if the user chose a different claimed_id than the one in request
		if (userSelectedClaimedId != null
				&& userSelectedClaimedId.equals(authReq.getClaimed())) {
			// opLocalId = lookupLocalId(userSelectedClaimedId);
		}

		boolean signNow = false;
		response = manager.authResponse(authReq, opLocalId,
				userSelectedClaimedId, authenticatedAndApproved.booleanValue(),
				signNow);

		if (response instanceof DirectError) {
			directResponse(httpResp, response.keyValueFormEncoding());
		} else {
			addExt(manager, response, authReq);
			addSRegExtension(manager, response, authReq, httpReq);
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

	private static Map<String, String> backingData(
			Map<String, Object> parameterMap) {
		Map<String, String> ret = new LinkedHashMap<String, String>();

		for (Iterator<Map.Entry<String, Object>> iter = parameterMap.entrySet()
				.iterator(); iter.hasNext();) {
			Map.Entry<String, Object> entry = iter.next();
			String name = entry.getKey();
			Object v = entry.getValue();

			String value;
			if (v instanceof String[]) {
				String[] values = (String[]) v;
				value = values.length > 0 ? values[0] : null;
			} else if (v instanceof String) {
				value = (String) v;
			} else {
				value = "";
			}

			ret.put(name, value);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	private static void addSRegExtension(ServerManager manager,
			Message response, AuthRequest authReq, HttpServletRequest request)
			throws MessageException {
		if (authReq.hasExtension(SRegMessage.OPENID_NS_SREG)) {
			MessageExtension ext = authReq
					.getExtension(SRegMessage.OPENID_NS_SREG);
			if (ext instanceof SRegRequest) {
				SRegRequest sregReq = (SRegRequest) ext;
				// data released by the user
				Map<String, String> userDataSReg = backingData(request
						.getParameterMap());

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
