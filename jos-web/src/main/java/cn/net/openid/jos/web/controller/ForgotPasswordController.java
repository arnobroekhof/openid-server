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
 * Created on 2008-9-3 05:41:51
 */
package cn.net.openid.jos.web.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.interceptor.CaptchaInterceptor;

/**
 * @author Sutra Zhou
 * 
 */
public class ForgotPasswordController extends AbstractJosSimpleFormController {
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onBindAndValidate(final HttpServletRequest request,
			final Object command, final BindException errors) throws Exception {
		super.onBindAndValidate(request, command, errors);

		Email email = (Email) command;
		if (StringUtils.isEmpty(email.getUser().getUsername())) {
			errors.rejectValue("user.username", "required");
			return;
		}

		User user = this.getJosService().getUser(this.getDomain(request),
				email.getUser().getUsername());
		String errorCode = "password.error.usernameOrEmailAddressIncorrect";
		if (user == null) {
			errors.rejectValue("address", errorCode);
		} else {
			Collection<Email> emails = this.getJosService().getEmails(user);
			boolean ok = false;
			for (Email e : emails) {
				if (e.isConfirmed()
						&& e.getAddress().equals(email.getAddress())) {
					ok = true;
					e.setUser(user);
					this.getJosService().generateOneTimePassword(user, e);
					break;
				}
			}
			if (!ok) {
				errors.rejectValue("address", errorCode);
			}
		}

		if (!errors.hasErrors()) {
			CaptchaInterceptor.setHuman(request, false);
		}
	}
}
