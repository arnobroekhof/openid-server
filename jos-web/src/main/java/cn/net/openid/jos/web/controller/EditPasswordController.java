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
 * Created on 2006-10-29 02:58:32
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Password;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.form.EditPasswordForm;

/**
 * @author Sutra Zhou
 */
public class EditPasswordController extends AbstractJosSimpleFormController {
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {
		EditPasswordForm editPasswordForm = (EditPasswordForm) super
				.formBackingObject(request);

		String passwordId = request.getParameter("password.id");
		if (!StringUtils.isEmpty(passwordId)) {
			editPasswordForm.setPassword(getJosService().getPassword(
					getUser(request), passwordId));
		}

		return editPasswordForm;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onBindAndValidate(final HttpServletRequest request,
			final Object command, final BindException errors) throws Exception {
		super.onBindAndValidate(request, command, errors);
		EditPasswordForm editPasswordForm = (EditPasswordForm) command;
		if (StringUtils.isEmpty(editPasswordForm.getPassword().getName())) {
			errors.rejectValue("password.name", "password.error.nameRequired");
		}
		if (StringUtils.isEmpty(editPasswordForm.getPassword()
				.getPlaintext())) {
			errors.rejectValue("password.plaintext",
					"password.error.plaintextRequired");
		}
		if (!StringUtils.equals(editPasswordForm.getPassword().getPlaintext(),
				editPasswordForm.getRetypedPassword())) {
			errors.rejectValue("retypedPassword", "password.error.notEquals");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {
		EditPasswordForm editPasswordForm = (EditPasswordForm) command;
		Password password = editPasswordForm.getPassword();
		getJosService().updatePassword(getUser(request), password.getId(),
				password.getName(), password.getPlaintext());
		return super.onSubmit(request, response, command, errors);
	}
}
