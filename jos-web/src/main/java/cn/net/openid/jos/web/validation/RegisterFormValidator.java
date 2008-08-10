/**
 * Created on 2006-11-5 下午10:29:59
 */
package cn.net.openid.jos.web.validation;

import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cn.net.openid.jos.web.MessageCodes;
import cn.net.openid.jos.web.form.RegisterForm;

/**
 * @author Sutra Zhou
 * 
 */
public class RegisterFormValidator implements Validator {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(RegisterFormValidator.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return RegisterForm.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		RegisterForm form = (RegisterForm) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.username",
				MessageCodes.Error.REQUIRED);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				MessageCodes.Error.REQUIRED);
		Matcher m = form.getUser().getDomain().getUsernamePattern().matcher(
				form.getUser().getUsername());
		if (!m.matches()) {
			errors.rejectValue("user.username",
					MessageCodes.User.Error.USERNAME_FORMAT);
		}

		m = form.getUser().getDomain().getReservedUsernamePattern().matcher(
				form.getUser().getUsername());
		if (m.matches()) {
			errors.rejectValue("user.username",
					MessageCodes.User.Error.USERNAME_RESERVED);
		}

		m = form.getUser().getDomain().getUnallowableUsernamePattern().matcher(
				form.getUser().getUsername());
		if (m.matches()) {
			errors.rejectValue("user.username",
					MessageCodes.User.Error.USERNAME_UNALLOWABLE);
		}

		if (!StringUtils.equals(form.getPassword(), form
				.getConfirmingPassword())) {
			errors.rejectValue("confirmingPassword",
					MessageCodes.User.Error.CONFIRMING_PASSWORD_NOT_EQUALS);
		}
	}
}
