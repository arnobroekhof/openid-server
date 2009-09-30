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
 * Created on 2006-10-18 22:30:43
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.web.AbstractJosController;
import cn.net.openid.jos.web.UserSession;

/**
 * @author Sutra Zhou
 */
public class HomeController extends AbstractJosController {
	/**
	 * {@inheritDoc}
	 */
	public ModelAndView handleRequest(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		if (username != null) {
			String redirectPath;
			if (request.getParameter("login") != null) {
				redirectPath = "/login?username=" + username;
			} else if (request.getParameter("index.login") != null) {
				redirectPath = "/index.login?username=" + username;
			} else {
				redirectPath = ("/register?username=" + username);
			}
			response.sendRedirect(response.encodeRedirectURL(request
					.getContextPath()
					+ redirectPath));
		}
		ModelAndView modelAndView = new ModelAndView("home");
		UserSession userSession = this.getUserSession(request);
		if (userSession.isLoggedIn()) {
			modelAndView.addObject("topSites", this.getJosService()
					.getTopSites(this.getUser(request), 10));
			modelAndView.addObject("latestSites", this.getJosService()
					.getLatestSites(this.getUser(request), 10));
			modelAndView.addObject("latestRealms", this.getJosService()
					.getLatestRealms(10));
		}
		return modelAndView;
	}
}
