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
 * Created on 2006-11-5 22:29:59
 */
package cn.net.openid.jos.web.validation;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cn.net.openid.jos.service.JosService;
import cn.net.openid.jos.web.MessageCodes;
import cn.net.openid.jos.web.form.RegisterForm;

/**
 * @author Sutra Zhou
 * 
 */
public class RegisterFormValidator implements Validator {
	/**
	 * The logger.
	 */
	private static final Log LOG = LogFactory
			.getLog(RegisterFormValidator.class);

	/**
	 * The JOS service.
	 */
	private JosService josService;

	/**
	 * @param josService
	 *            the josService to set
	 */
	public void setJosService(final JosService josService) {
		this.josService = josService;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(final Class clazz) {
		return RegisterForm.class.isAssignableFrom(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(final Object target, final Errors errors) {
		final RegisterForm form = (RegisterForm) target;
		if (LOG.isDebugEnabled()) {
			LOG.debug("Username configuration: "
					+ form.getUser().getDomain().getUsernameConfiguration());
		}

		// Username is required.
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.username",
				MessageCodes.Error.REQUIRED);

		// Password is required.
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				MessageCodes.Error.REQUIRED);

		// Is not a username?
		if (!form.getUser().getDomain().getUsernameConfiguration().isUsername(
				form.getUser().getUsername())) {
			errors.rejectValue("user.username",
					MessageCodes.User.Error.USERNAME_FORMAT);
		}

		// Is username reserved?
		if (StringUtils.equalsIgnoreCase(form.getUser().getDomain()
				.getServerHost(), form.getUser().getUsername())
				|| this.josService.isSystemReservedWord(form.getUser()
						.getUsername())
				|| form.getUser().getDomain().getUsernameConfiguration()
						.isReserved((form.getUser().getUsername()))) {
			errors.rejectValue("user.username",
					MessageCodes.User.Error.USERNAME_RESERVED);
		}

		// Is username unallowable?
		if (form.getUser().getDomain().getUsernameConfiguration()
				.isUnallowable(form.getUser().getUsername())) {
			errors.rejectValue("user.username",
					MessageCodes.User.Error.USERNAME_UNALLOWABLE);
		}

		// Confirmed password?
		if (!StringUtils.equals(form.getPassword(), form
				.getConfirmingPassword())) {
			errors.rejectValue("confirmingPassword",
					MessageCodes.User.Error.CONFIRMING_PASSWORD_NOT_EQUALS);
		}
	}
}
