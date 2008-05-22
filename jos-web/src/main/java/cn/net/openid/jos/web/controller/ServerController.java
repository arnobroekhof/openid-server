/**
 * Created on 2006-10-5 下午05:58:31
 */
package cn.net.openid.jos.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.message.AuthRequest;
import org.openid4java.message.DirectError;
import org.openid4java.message.Message;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.openid4java.server.ServerManager;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Persona;
import cn.net.openid.jos.domain.Site;
import cn.net.openid.jos.web.AbstractJosController;
import cn.net.openid.jos.web.UserSession;
import cn.net.openid.jos.web.WebUtils;

/**
 * @author Sutra Zhou
 * 
 */
public class ServerController extends AbstractJosController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2796635946888123803L;

	public static void writeResponse(HttpServletResponse httpResp,
			String response) throws IOException {
		ServletOutputStream os = httpResp.getOutputStream();
		try {
			os.write(response.getBytes());
		} finally {
			os.close();
		}
	}

	public static void redirectToReturnToPage(ServerManager manager,
			HttpServletRequest httpReq, HttpServletResponse httpResp,
			AuthRequest authReq, boolean approved, Persona persona)
			throws MessageException, IOException {
		ApprovingController.response(manager, httpReq, httpResp, authReq,
				approved, persona);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Received a request.");
		try {
			String responseText = processRequest(request, response);

			if (responseText != null) {
				writeResponse(response, responseText);
			}
		} catch (Exception e) {
			log.error("error.", e);
			throw new ServletException(e);
		}
		return null;
	}

	private String processRequest(HttpServletRequest httpReq,
			HttpServletResponse httpResp) throws Exception {
		// extract the parameters from the request
		ParameterList request = new ParameterList(httpReq.getParameterMap());

		String mode = request.getParameterValue("openid.mode");

		Message response;
		String responseText;

		if ("associate".equals(mode)) {
			// --- process an association request ---
			response = this.serverManager.associationResponse(request);
			responseText = response.keyValueFormEncoding();
		} else if ("checkid_setup".equals(mode)
				|| "checkid_immediate".equals(mode)) {
			AuthRequest authReq = AuthRequest.createAuthRequest(request,
					this.serverManager.getRealmVerifier());
			this.checkId(httpReq, httpResp, authReq);
			responseText = null;
		} else if ("check_authentication".equals(mode)) {
			// --- processing a verification request ---
			response = this.serverManager.verify(request);
			responseText = response.keyValueFormEncoding();
		} else {
			// --- error response ---
			response = DirectError.createDirectError("Unknown request");
			responseText = response.keyValueFormEncoding();
		}

		// return the result to the user
		return responseText;
	}

	private void checkId(HttpServletRequest httpReq,
			HttpServletResponse httpResp, AuthRequest authReq)
			throws IOException {
		UserSession userSession = WebUtils.getOrCreateUserSession(httpReq
				.getSession());

		if (userSession.isLoggedIn()) {
			checkId(httpReq, httpResp, authReq, userSession);
		} else {
			// redirect to login page.
			httpResp.sendRedirect("login?token="
					+ userSession.addRequest(authReq));
		}
	}

	private void checkId(HttpServletRequest httpReq,
			HttpServletResponse httpResp, AuthRequest authReq,
			UserSession loggedInUserSession) throws IOException {
		Site site = this.josService.getSite(loggedInUserSession.getUserId(),
				authReq.getRealm());
		if (site != null && site.isAlwaysApprove()) {
			this.josService.updateApproval(loggedInUserSession.getUserId(),
					authReq.getRealm());
			// return to `return_to' page.
			try {
				redirectToReturnToPage(this.serverManager, httpReq, httpResp,
						authReq, true, site.getPersona());
			} catch (MessageException e) {
				log.error("", e);
			}
		} else {
			// redirect to approving page.
			httpResp.sendRedirect("approving?token="
					+ loggedInUserSession.addRequest(authReq));
		}
	}
}
