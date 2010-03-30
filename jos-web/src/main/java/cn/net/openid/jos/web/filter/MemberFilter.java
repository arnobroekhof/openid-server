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
 * Created on 2006-10-18 23:49:06
 */
package cn.net.openid.jos.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.net.openid.jos.domain.Domain;

/**
 * @author Sutra Zhou
 */
public class MemberFilter extends OncePerRequestServiceFilter {
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(final HttpServletRequest request,
			final HttpServletResponse response, final FilterChain filterChain)
			throws ServletException, IOException {
		getLog().debug("Begin of member filter.");
		Domain domain = null;
		domain = DomainFilter.getDomain(request.getSession(false));

		getLog().debug("Parse username from the request.");
		final String username;
		if (domain != null) {
			username = getService().parseUsername(domain, request);
		} else {
			username = null;
		}

		if (getLog().isDebugEnabled()) {
			getLog().debug(String.format("username@domain: %1$s@%2$s", username,
					domain));
		}
		if (username == null) {
			getLog().debug("The url is not matches.");
			filterChain.doFilter(request, response);
		} else {
			this.dispatch(request, response, username);
		}
		getLog().debug("End of member filter.");
	}

	/**
	 * Dispatch to the identity page for the user.
	 * 
	 * @param request
	 *            the HTTP request
	 * @param response
	 *            the HTTp response
	 * @param username
	 *            the username of the user
	 * @throws ServletException
	 *             if dispatch failed
	 * @throws IOException
	 *             indicate IO error
	 */
	private void dispatch(final ServletRequest request,
			final ServletResponse response, final String username)
			throws ServletException, IOException {
		String path = "/member/" + username;
		if (getLog().isDebugEnabled()) {
			getLog().debug("path: " + path);
		}
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
				path);
		rd.forward(request, response);
	}
}
