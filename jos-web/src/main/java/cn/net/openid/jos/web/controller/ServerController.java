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
 * Created on 2006-10-5 17:58:31
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.message.AuthRequest;
import org.openid4java.message.DirectError;
import org.openid4java.message.Message;
import org.openid4java.message.ParameterList;
import org.openid4java.server.ServerManager;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.web.AbstractJosController;
import cn.net.openid.jos.web.ApprovingRequest;
import cn.net.openid.jos.web.ApprovingRequestProcessor;
import cn.net.openid.jos.web.WebUtils;

/**
 * @author Sutra Zhou
 */
public class ServerController extends AbstractJosController {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2796635946888123803L;

	/**
	 * {@inheritDoc}
	 */
	public ModelAndView handleRequest(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		getLog().debug("Received a request.");
		try {
			String responseText = processRequest(request, response);
			WebUtils.writeResponse(response, responseText);
		} catch (Exception e) {
			getLog().error("error.", e);
			throw new ServletException(e);
		}
		return null;
	}

	/**
	 * Process the request.
	 * 
	 * @param httpReq
	 *            the HTTP request
	 * @param httpResp
	 *            the HTTp resposne
	 * @return process result string
	 * @throws Exception
	 *             indicate authenticate error
	 */
	private String processRequest(final HttpServletRequest httpReq,
			final HttpServletResponse httpResp) throws Exception {
		Domain domain = this.getDomain(httpReq);
		if (getLog().isDebugEnabled()) {
			getLog().debug("domain: " + domain);
		}
		ServerManager serverManager =
			this.getJosService().getServerManager(domain);

		// extract the parameters from the request
		ParameterList request = new ParameterList(httpReq.getParameterMap());

		String mode = request.getParameterValue("openid.mode");

		Message response;
		String responseText;

		if ("associate".equals(mode)) {
			// --- process an association request ---
			response = serverManager.associationResponse(request);
			responseText = response.keyValueFormEncoding();
		} else if ("checkid_setup".equals(mode)
				|| "checkid_immediate".equals(mode)) {
			AuthRequest authReq = AuthRequest.createAuthRequest(request,
					serverManager.getRealmVerifier());
			new ApprovingRequestProcessor(httpReq, httpResp, getJosService(),
					serverManager, new ApprovingRequest(authReq)).checkId();
			responseText = null;
		} else if ("check_authentication".equals(mode)) {
			// --- processing a verification request ---
			response = serverManager.verify(request);
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
