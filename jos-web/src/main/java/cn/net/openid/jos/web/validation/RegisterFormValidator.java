/**
 * Created on 2006-11-5 下午10:29:59
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
	private static final Log log = LogFactory
			.getLog(RegisterFormValidator.class);

	private JosService josService;

	/**
	 * @param josService
	 *            the josService to set
	 */
	public void setJosService(JosService josService) {
		this.josService = josService;
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
	 * org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		final RegisterForm form = (RegisterForm) target;
		if (log.isDebugEnabled()) {
			log.debug("Username configuration: "
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
