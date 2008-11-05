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
 * 
 */
public class CaptchaController extends AbstractJosSimpleFormController {
	private LocaleResolver localeResolver;

	/**
	 * @param localeResolver
	 *            the localeResolver to set
	 */
	public void setLocaleResolver(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject
	 * (javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		CaptchaForm captchaForm = (CaptchaForm) super
				.formBackingObject(request);
		captchaForm.setLocale(this.localeResolver.resolveLocale(request));
		return captchaForm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.BaseCommandController#onBindAndValidate
	 * (javax.servlet.http.HttpServletRequest, java.lang.Object,
	 * org.springframework.validation.BindException)
	 */
	@Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		CaptchaInterceptor.setHuman(request, true);
		response.sendRedirect(response.encodeRedirectURL(CaptchaInterceptor
				.getFrom(request)));
		return null;
	}

}
