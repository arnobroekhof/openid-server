/**
 * Created on 2006-11-5 下午10:29:59
 */
package cn.net.openid.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cn.net.openid.domain.User;

/**
 * @author Shutra
 * 
 */
public class UserValidator implements Validator {

	private static Collection<String> isoCountries;

	private static Collection<String> isoLanguages;

	private static Collection<String> genders;
	static {
		String[] c = Locale.getISOCountries();
		isoCountries = new ArrayList<String>(c.length);
		Collections.addAll(isoCountries, c);

		c = Locale.getISOLanguages();
		isoLanguages = new ArrayList<String>(c.length);
		Collections.addAll(isoLanguages, c);

		genders = new ArrayList<String>(5);
		genders.add("U");
		genders.add("M");
		genders.add("F");
		genders.add("A");
		genders.add("E");
	}

	private Pattern usernamePattern;

	/**
	 * @param usernamePattern
	 *            the usernamePattern to set
	 */
	public void setUsernamePattern(String usernamePattern) {
		this.usernamePattern = Pattern.compile(usernamePattern);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
				"required", "Field is required.");
		if (user.getUsername() != null) {
			Matcher m = this.usernamePattern.matcher(user.getUsername());
			if (!m.matches()) {
				errors.rejectValue("username", "error.username.format",
						"Username format not allowed.");
			}
		}
		if (!StringUtils.isEmpty(user.getCountry())) {
			if (!isoCountries.contains(user.getCountry())) {
				errors.rejectValue("country", "required", "Field is required.");
			}
		}

		if (!StringUtils.isEmpty(user.getLanguage())) {
			if (!isoLanguages.contains(user.getLanguage())) {
				errors.rejectValue("language", "notCandidate", user
						.getLanguage()
						+ " is not a candidate.");
			}
		}

		if (!StringUtils.isEmpty(user.getGender())) {
			if (!genders.contains(user.getGender())) {
				errors.rejectValue("gender", "notCandidate",
						new Object[] { user.getGender() }, user.getGender()
								+ " is not a candidate.");
			}
		}

		if (user.getDob() != null) {
			if (user.getDob().after(new Date())) {
				errors.rejectValue("dob", "error.dateOfBirth.future",
						"Will birth?");
			}
		}
	}
}
