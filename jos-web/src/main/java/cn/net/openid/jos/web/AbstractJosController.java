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
 * 
 */
package cn.net.openid.jos.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.Controller;

import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.service.JosService;
import cn.net.openid.jos.web.filter.DomainFilter;

/**
 * Abstract JOS controller, provides domain and session accessor.
 * 
 * @author Sutra Zhou
 */
public abstract class AbstractJosController implements Controller {
	/**
	 * The logger.
	 */
	private final Log log = LogFactory.getLog(getClass());

	/**
	 * The JOS service.
	 */
	private JosService josService;

	/**
	 * Gets the logger.
	 * 
	 * @return the logger
	 */
	public Log getLog() {
		return log;
	}

	/**
	 * Get the JOS service.
	 * 
	 * @return the josService
	 */
	public JosService getJosService() {
		return josService;
	}

	/**
	 * Set the JOS service.
	 * 
	 * @param josService
	 *            the josService to set
	 */
	public void setJosService(final JosService josService) {
		this.josService = josService;
	}

	/**
	 * Get the current domain.
	 * 
	 * @param request
	 *            the HTTP request
	 * @return current domain which parsed from the request url by
	 *         {@link DomainFilter}.
	 */
	public Domain getDomain(final HttpServletRequest request) {
		return DomainFilter.getDomain(request);
	}

	/* User Session */

	/**
	 * Get user session object.
	 * 
	 * @param session
	 *            the HTTP session.
	 * @return User session object in the HTTP session, if not found, create a
	 *         new one
	 */
	public UserSession getUserSession(final HttpSession session) {
		return WebUtils.getOrCreateUserSession(session);
	}

	/**
	 * Get user session object.
	 * 
	 * @param request
	 *            the HTTP Servlet request.
	 * @return User session object in the HTTP session of the sepcifeid HTTP
	 *         request, if not found, create a new one
	 */
	public UserSession getUserSession(final HttpServletRequest request) {
		return getUserSession(request.getSession());
	}

	/**
	 * Get the user object from the HTTP session.
	 * 
	 * @param session
	 *            the HTTP session
	 * @return the user object
	 */
	public User getUser(final HttpSession session) {
		return this.getUserSession(session).getUser();
	}

	/**
	 * Get the user object from the HTTP Servlet request.
	 * 
	 * @param request
	 *            the HTTP Servlet request
	 * @return the user object
	 */
	public User getUser(final HttpServletRequest request) {
		return this.getUserSession(request).getUser();
	}

}
