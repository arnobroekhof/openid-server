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
 * Created on 2008-6-19 01:00:18
 */
package cn.net.openid.jos.web.validation;

import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.web.MessageCodes;

/**
 * @author Sutra Zhou
 */
public class EmailValidator implements Validator {
	/**
	 * The logger.
	 */
	private static final Log LOG = LogFactory.getLog(EmailValidator.class);

	/**
	 * The regular expression pattern to check email address.
	 */
	private Pattern emailAddressPattern;

	/**
	 * Set the email address checking pattern.
	 * 
	 * @param emailAddressPattern
	 *            the email address checking pattern to set
	 */
	public void setEmailAddressPattern(final String emailAddressPattern) {
		this.emailAddressPattern = Pattern.compile(emailAddressPattern.trim());
		if (LOG.isDebugEnabled()) {
			LOG.debug("setEmailAddressPattern, emailAddressPattern.pattern: "
					+ this.emailAddressPattern.pattern());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(final Class clazz) {
		return Email.class.isAssignableFrom(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(final Object target, final Errors errors) {
		Email email = (Email) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address",
				"required", "Field is required.");
		if (!errors.hasFieldErrors("address")
				&& !emailAddressPattern.matcher(email.getAddress()).matches()) {
			errors.rejectValue("address", MessageCodes.Email.Error.ADDRESS,
					new String[] { email.getAddress() },
					"\"{0}\" is not a valid e-mail address.");
		}
	}
}
