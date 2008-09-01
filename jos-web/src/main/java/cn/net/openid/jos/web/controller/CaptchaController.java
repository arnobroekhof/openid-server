/**
 * Created on 2008-9-2 上午05:07:13
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.filter.CaptchaFilter;

/**
 * @author Sutra Zhou
 * 
 */
public class CaptchaController extends AbstractJosSimpleFormController {

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
		reCaptcha.setPrivateKey("6Lcd_gIAAAAAAPKYJjS4t7LuoDshWtzEJUJgKGq-");

		ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr,
				request.getParameter("recaptcha_challenge_field"), request
						.getParameter("recaptcha_response_field"));

		if (!reCaptchaResponse.isValid()) {
			FieldError fieldError = new FieldError("command", "captcha",
					request.getParameter("recaptcha_response_field"), false,
					new String[] { "errors.badCaptcha" }, null,
					"Please try again.");
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
		CaptchaFilter.setHuman(request, true);
		response.sendRedirect(response.encodeRedirectURL(CaptchaFilter
				.getFrom(request)));
		return null;
	}

}
