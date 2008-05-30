/**
 * Created on 2006-10-5 下午05:58:31
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.message.AuthRequest;
import org.openid4java.message.DirectError;
import org.openid4java.message.Message;
import org.openid4java.message.ParameterList;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.web.AbstractJosController;
import cn.net.openid.jos.web.ApprovingRequest;
import cn.net.openid.jos.web.ApprovingRequestProcessor;
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
			WebUtils.writeResponse(response, responseText);
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
			new ApprovingRequestProcessor(httpReq, httpResp, josService,
					serverManager, new ApprovingRequest(authReq)).checkLoggedIn();
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
}
