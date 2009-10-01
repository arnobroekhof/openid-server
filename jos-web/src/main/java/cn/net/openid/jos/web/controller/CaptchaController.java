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
 * Created on 2008-9-2 05:07:13
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.MessageCodes;
import cn.net.openid.jos.web.form.CaptchaForm;
import cn.net.openid.jos.web.interceptor.CaptchaInterceptor;

/**
 * @author Sutra Zhou
 */
public class CaptchaController extends AbstractJosSimpleFormController {
	/**
	 * The locale resolver.
	 */
	private LocaleResolver localeResolver;

	/**
	 * Constructs captcha controller.
	 * 
	 * @param localeResolver
	 *            the local resolver
	 */
	public CaptchaController(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {
		CaptchaForm captchaForm = (CaptchaForm) super
				.formBackingObject(request);
		captchaForm.setLocale(this.localeResolver.resolveLocale(request));
		return captchaForm;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onBindAndValidate(final HttpServletRequest request,
			final Object command, final BindException errors) throws Exception {
		// Validate the reCAPTCHA
		String remoteAddr = request.getRemoteAddr();

		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();

		// Probably don't want to hardcode your private key here but
		// just to get it working is OK...
		Domain domain = this.getDomain(request);
		reCaptcha.setPrivateKey(domain.getConfiguration().get(
				"recaptcha.private_key"));

		ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr,
				request.getParameter("recaptcha_challenge_field"), request
						.getParameter("recaptcha_response_field"));

		if (!reCaptchaResponse.isValid()) {
			FieldError fieldError = new FieldError("command", "captcha",
					request.getParameter("recaptcha_response_field"), false,
					new String[] { MessageCodes.Captcha.Error.BAD_CAPTCHA },
					null, "Please try again.");
			errors.addError(fieldError);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {
		CaptchaInterceptor.setHuman(request, true);
		response.sendRedirect(response.encodeRedirectURL(CaptchaInterceptor
				.getFrom(request)));
		return null;
	}
}
