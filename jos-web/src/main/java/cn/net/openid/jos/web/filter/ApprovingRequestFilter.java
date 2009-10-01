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
 * Created on 2008-5-31 05:52:07
 */
package cn.net.openid.jos.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.net.openid.jos.web.UserSession;
import cn.net.openid.jos.web.WebUtils;

/**
 * @author Sutra Zhou
 */
public class ApprovingRequestFilter extends OncePerRequestFilter {
	/**
	 * Token attribute name in HTTP request.
	 */
	private static final String TOKEN_ATTRIBUTE_NAME = "token";

	/**
	 * Approving request attribute name in HTTP request.
	 */
	private static final String APPROVING_REQUEST_ATTRIBUTE_NAME =
		"approvingRequest";

	/**
	 * Indicate the logger is in debug mode.
	 */
	private final boolean debug = logger.isDebugEnabled();

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(final HttpServletRequest request,
			final HttpServletResponse response, final FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getParameter("token");
		request.setAttribute(TOKEN_ATTRIBUTE_NAME, StringUtils.defaultString(
				token, StringUtils.EMPTY));
		HttpSession session = request.getSession();
		UserSession userSession = WebUtils.getOrCreateUserSession(session);
		request.setAttribute(APPROVING_REQUEST_ATTRIBUTE_NAME, userSession
				.getApprovingRequest(token));
		if (debug) {
			logger.debug("Add attribute `token' to request: " + token);
		}
		filterChain.doFilter(request, response);
	}
}
