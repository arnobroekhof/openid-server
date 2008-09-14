/**
 * Created on 2006-11-5 下午10:29:59
 */
package cn.net.openid.jos.web.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cn.net.openid.jos.domain.Persona;
import cn.net.openid.jos.web.MessageCodes;

/**
 * @author Sutra Zhou
 * 
 */
public class PersonaValidator implements Validator {
	private static final Pattern dobPattern = Pattern
			.compile("(\\d{4})-(\\d{2})-(\\d{2})");
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

	private Pattern emailAddressPattern;

	public void setEmailAddressPattern(String emailAddressPattern) {
		this.emailAddressPattern = Pattern.compile(emailAddressPattern.trim());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return Persona.class.isAssignableFrom(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		Persona persona = (Persona) target;

		// Name is required.
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required",
				"Field is required.");

		// Country range check.
		if (StringUtils.isNotEmpty(persona.getCountry())) {
			if (!isoCountries.contains(persona.getCountry())) {
				errors.rejectValue("country", "notCandidate", persona
						.getCountry()
						+ " is not a candidate.");
			}
		}

		// Language range check.
		if (StringUtils.isNotEmpty(persona.getLanguage())) {
			if (!isoLanguages.contains(persona.getLanguage())) {
				errors.rejectValue("language", "notCandidate", persona
						.getLanguage()
						+ " is not a candidate.");
			}
		}

		// Gender range check.
		if (StringUtils.isNotEmpty(persona.getGender())) {
			if (!genders.contains(persona.getGender())) {
				errors.rejectValue("gender", "notCandidate",
						new Object[] { persona.getGender() }, persona
								.getGender()
								+ " is not a candidate.");
			}
		}

		// dob check.
		if (StringUtils.isNotEmpty(persona.getDob())) {
			Matcher matcher = dobPattern.matcher(persona.getDob());
			if (matcher.matches()) {

			} else {
				errors.rejectValue("dob", MessageCodes.Persona.Error.DOB,
						"Incorrect birth date.");
			}
		}

		// E-mail address check.
		if (StringUtils.isNotEmpty(persona.getEmail())
				&& !emailAddressPattern.matcher(persona.getEmail()).matches()) {
			errors.rejectValue("email", MessageCodes.Email.Error.ADDRESS,
					new String[] { persona.getEmail() },
					"\"{0}\" is not a valid e-mail address.");
		}

	}
}
