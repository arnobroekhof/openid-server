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
 * Created on 2006-10-15 21:20:29
 */
package cn.net.openid.jos.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Password;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.MessageCodes;
import cn.net.openid.jos.web.form.RegisterForm;
import cn.net.openid.jos.web.interceptor.CaptchaInterceptor;

/**
 * @author Sutra Zhou
 */
public class RegisterController extends AbstractJosSimpleFormController {
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Map<Object, Object> referenceData(
			final HttpServletRequest request, final Object command,
			final Errors errors) throws Exception {
		RegisterForm form = (RegisterForm) command;

		// Set current domain to the user.
		form.getUser().setDomain(this.getDomain(request));

		form.getUser().setUsername(request.getParameter("username"));
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {
		RegisterForm form = (RegisterForm) super.formBackingObject(request);

		// Set current domain to the user.
		form.getUser().setDomain(this.getDomain(request));

		return form;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onBindAndValidate(final HttpServletRequest request,
			final Object command, final BindException errors) throws Exception {
		RegisterForm form = (RegisterForm) command;

		String username = form.getUser().getUsername();

		if (!errors.hasErrors()) {
			User user = this.getJosService().getUser(getDomain(request),
					username);
			if (user != null) {
				errors.rejectValue("user.username",
						MessageCodes.User.Error.REGISTER_USER_ALREADY_EXISTS);
			}
		}

		super.onBindAndValidate(request, command, errors);

		// Register success, remove the human flag.
		if (!errors.hasErrors()) {
			CaptchaInterceptor.setHuman(request, false);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelAndView onSubmit(final Object command,
			final BindException errors) throws Exception {
		RegisterForm form = (RegisterForm) command;
		User user = form.getUser();
		String passwordShaHex = DigestUtils.shaHex(form.getPassword());
		Password password = new Password(user);
		String defaultPasswordMessage = this.getMessageSourceAccessor()
				.getMessage(MessageCodes.Password.Title.DEFAULT_PASSWORD);
		password.setName(defaultPasswordMessage);
		password.setPlaintext(form.getPassword());
		password.setShaHex(passwordShaHex);
		this.getJosService().insertUser(user, password);
		return super.onSubmit(command, errors);
	}
}
