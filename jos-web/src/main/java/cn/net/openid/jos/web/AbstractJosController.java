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
 * @author Sutra Zhou
 * 
 */
public abstract class AbstractJosController implements Controller {
	protected final Log log = LogFactory.getLog(getClass());

	private JosService josService;

	/**
	 * @return the josService
	 */
	public JosService getJosService() {
		return josService;
	}

	/**
	 * @param josService
	 *            the josService to set
	 */
	public void setJosService(JosService josService) {
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
	public Domain getDomain(HttpServletRequest request) {
		return DomainFilter.getDomain(request);
	}

	/* User Session */

	public UserSession getUserSession(HttpSession session) {
		return WebUtils.getOrCreateUserSession(session);
	}

	public UserSession getUserSession(HttpServletRequest request) {
		return getUserSession(request.getSession());
	}

	public User getUser(HttpSession session) {
		return this.getUserSession(session).getUser();
	}

	public User getUser(HttpServletRequest request) {
		return this.getUserSession(request).getUser();
	}

}
