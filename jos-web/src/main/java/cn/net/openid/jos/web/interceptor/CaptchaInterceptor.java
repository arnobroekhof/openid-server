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
 * 
 */
public class CaptchaInterceptor extends HandlerInterceptorAdapter {
	private static final String HUMAN_SESSION_NAME = CaptchaInterceptor.class
			.getName()
			+ "IS_HUMAN";
	private static final String FROM_SESSION_NAME = CaptchaInterceptor.class
			.getName()
			+ "FROM";

	public static boolean isHuman(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return session != null
				&& session.getAttribute(HUMAN_SESSION_NAME) != null;
	}

	public static void setHuman(HttpServletRequest request, boolean value) {
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
	 * @return
	 */
	public static String getFrom(HttpServletRequest request) {
		String ret = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			ret = (String) session.getAttribute(FROM_SESSION_NAME);
			session.removeAttribute(FROM_SESSION_NAME);
		}
		return StringUtils.defaultString(ret, request.getContextPath());
	}

	private static void setFrom(HttpServletRequest request, String value) {
		request.getSession().setAttribute(FROM_SESSION_NAME, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		boolean ret;

		Domain domain = DomainFilter.getDomain(request);
		boolean captcha = domain.getBooleanAttribute("captcha");
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
