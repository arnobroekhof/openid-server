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
 * Created on 2006-10-7 12:05:13
 */
package cn.net.openid.jos.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.openid4java.server.ServerManager;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.ApprovingRequest;
import cn.net.openid.jos.web.ApprovingRequestProcessor;
import cn.net.openid.jos.web.MessageCodes;
import cn.net.openid.jos.web.UserSession;
import cn.net.openid.jos.web.form.LoginForm;
import cn.net.openid.jos.web.interceptor.CaptchaInterceptor;

/**
 * @author Sutra Zhou
 * 
 */
public class LoginController extends AbstractJosSimpleFormController {
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onBindAndValidate(final HttpServletRequest request,
			final Object command, final BindException errors) throws Exception {
		LoginForm lf = (LoginForm) command;
		User user = getJosService().login(this.getDomain(request),
				lf.getUsername(), lf.getPassword());
		if (user == null) {
			String errorCode = MessageCodes.User.Error.LOGIN_FAILED;
			errors.rejectValue("username", errorCode);
		} else {
			// Comment as this logic has moved to JosService.
			// user.setDomain(this.getDomain(request));

			UserSession userSession = getUserSession(request);
			userSession.setUser(user);
			userSession.setLoggedIn(true);
		}
		super.onBindAndValidate(request, command, errors);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {
		String token = (String) request.getAttribute("token");
		UserSession userSession = getUserSession(request);
		ApprovingRequest checkIdRequest = userSession
				.getApprovingRequest(token);
		if (checkIdRequest != null) {
			Domain domain = this.getDomain(request);
			ServerManager serverManager = this.getJosService()
					.getServerManager(domain);
			new ApprovingRequestProcessor(request, response, getJosService(),
					serverManager, checkIdRequest).checkId();
		}
		CaptchaInterceptor.setHuman(request, false);

		if (userSession.isLoggedIn()) {
			request.setAttribute("topSites", this.getJosService().getTopSites(
					this.getUser(request), 10));
			request.setAttribute("latestSites", this.getJosService()
					.getLatestSites(this.getUser(request), 10));
			request.setAttribute("latestRealms", this.getJosService()
					.getLatestRealms(10));
		}

		return super.onSubmit(request, response, command, errors);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Map<Object, Object> referenceData(
			final HttpServletRequest request, final Object command,
			final Errors errors) throws Exception {
		LoginForm form = (LoginForm) command;

		if (StringUtils.isEmpty(form.getUsername())) {
			form.setUsername(request.getParameter("username"));
		}

		return null;
	}
}
