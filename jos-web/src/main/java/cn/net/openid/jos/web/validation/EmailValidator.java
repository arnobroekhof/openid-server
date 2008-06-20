/**
 * Created on 2008-6-19 上午01:00:18
 */
package cn.net.openid.jos.web.validation;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cn.net.openid.jos.domain.Email;

/**
 * @author Sutra Zhou
 */
public class EmailValidator implements Validator {
	private Pattern pattern;

	public void setPattern(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return Email.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		Email email = (Email) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address",
				"required", "Field is required.");
		if (!errors.hasFieldErrors("address")
				&& !pattern.matcher(email.getAddress()).matches()) {
			errors.rejectValue("address", "email.error.address",
					new String[] { email.getAddress() },
					"\"{0}\" is not a valid e-mail address.");
		}
	}
}
