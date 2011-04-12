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
 * Created on 2008-9-2 04:49:36
 */
package cn.net.openid.jos.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.web.filter.DomainFilter;

/**
 * @author Sutra Zhou
 */
public class CaptchaInterceptor extends HandlerInterceptorAdapter {
	/**
	 * The session name of human flag, the flag indicates that the request if
	 * the HTTP request is sent by human.
	 */
	private static final String HUMAN_SESSION_NAME = CaptchaInterceptor.class
			.getName()
			+ "IS_HUMAN";

	/**
	 * The captcha previous page.
	 */
	private static final String FROM_SESSION_NAME = CaptchaInterceptor.class
			.getName()
			+ "FROM";

	/**
	 * Returns if the request is sent by human.
	 * 
	 * @param request
	 *            the HTTP request
	 * @return true if is by human, otherwise false
	 */
	public static boolean isHuman(final HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return session != null
				&& session.getAttribute(HUMAN_SESSION_NAME) != null;
	}

	/**
	 * Sets the human value to specified value.
	 * 
	 * @param request
	 *            the HTTP request
	 * @param value
	 *            the value to set
	 */
	public static void setHuman(final HttpServletRequest request,
			final boolean value) {
		if (value) {
			request.getSession().setAttribute(HUMAN_SESSION_NAME, value);
		} else {
			request.getSession().removeAttribute(HUMAN_SESSION_NAME);
		}
	}

	/**
	 * Get the captcha previous page, if null return contextPath.
	 * 
	 * @param request
	 *            the HTTP request
	 * @return the path of the previous page
	 */
	public static String getFrom(final HttpServletRequest request) {
		String ret = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			ret = (String) session.getAttribute(FROM_SESSION_NAME);
			session.removeAttribute(FROM_SESSION_NAME);
		}
		return StringUtils.defaultString(ret, request.getContextPath());
	}

	/**
	 * Sets the from.
	 * 
	 * @param request
	 *            the HTTP request
	 * @param value
	 *            the value to set
	 */
	private static void setFrom(final HttpServletRequest request,
			final String value) {
		request.getSession().setAttribute(FROM_SESSION_NAME, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean preHandle(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler)
			throws Exception {
		boolean ret;

		Domain domain = DomainFilter.getDomain(request);
		boolean captcha = domain.getBooleanAttribute("captcha", Boolean.FALSE)
				.booleanValue();
		if (!captcha || isHuman(request)) {
			ret = true;
		} else {
			setFrom(request, request.getRequestURI());
			response.sendRedirect(response.encodeRedirectURL(request
					.getContextPath()
					+ "/captcha"));
			ret = false;
		}

		return ret;
	}
}
