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

import cn.net.openid.jos.domain.JosConfiguration;
import cn.net.openid.jos.web.form.RegisterForm;

/**
 * @author Sutra Zhou
 * 
 */
public class RegisterFormValidator implements Validator {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(RegisterFormValidator.class);

	private Pattern usernamePattern;
	private Pattern reservedUsernamePatter;
	private Pattern unallowableUsernamePattern;

	public void setDomainConfiguration(JosConfiguration config) {
		this.usernamePattern = Pattern.compile(config.getUsernamePattern());
		this.reservedUsernamePatter = Pattern.compile(config
				.getReservedUsernamePattern(), Pattern.CASE_INSENSITIVE);
		this.unallowableUsernamePattern = Pattern.compile(config
				.getUnallowableUsernamePattern(), Pattern.CASE_INSENSITIVE);
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

			m = this.reservedUsernamePatter.matcher(user.getUsername());
			if (m.matches()) {
				errors.rejectValue("username", "error.username.reserved",
						"Username is reserved.");
			}

			m = this.unallowableUsernamePattern.matcher(user.getUsername());
			if (m.matches()) {
				errors.rejectValue("username", "error.username.unallowable",
						"Username is unallowable.");
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
