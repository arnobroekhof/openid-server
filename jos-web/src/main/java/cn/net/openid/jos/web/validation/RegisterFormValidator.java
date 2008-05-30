/**
 * Created on 2006-11-5 下午10:29:59
 */
package cn.net.openid.jos.web.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cn.net.openid.jos.web.form.RegisterForm;

/**
 * @author Sutra Zhou
 * 
 */
public class RegisterFormValidator implements Validator {
	private static final Log log = LogFactory
			.getLog(RegisterFormValidator.class);

	private Pattern usernamePattern;

	/**
	 * @param usernamePattern
	 *            the usernamePattern to set
	 */
	public void setUsernamePattern(String usernamePattern) {
		log.debug("usernamePattern: " + usernamePattern);
		this.usernamePattern = Pattern.compile(usernamePattern);
	}

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
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		RegisterForm user = (RegisterForm) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
				"required", "Field is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"required", "Field is required.");
		if (user.getUsername() != null) {
			Matcher m = this.usernamePattern.matcher(user.getUsername());
			if (!m.matches()) {
				errors.rejectValue("username", "error.username.format",
						"Username format not allowed.");
			}
		}

		if (!StringUtils.equals(user.getPassword(), user
				.getConfirmingPassword())) {
			errors.rejectValue("confirmingPassword",
					"confirmingPassword.notEquals",
					"Confirming password is not equals to the password.");
		}
	}
}
