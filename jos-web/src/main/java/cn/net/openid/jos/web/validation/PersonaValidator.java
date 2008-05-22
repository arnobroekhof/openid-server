/**
 * Created on 2006-11-5 下午10:29:59
 */
package cn.net.openid.jos.web.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cn.net.openid.jos.domain.Persona;

/**
 * @author Sutra Zhou
 * 
 */
public class PersonaValidator implements Validator {

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
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {
		Persona persona = (Persona) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required",
				"Field is required.");
		if (!StringUtils.isEmpty(persona.getCountry())) {
			if (!isoCountries.contains(persona.getCountry())) {
				errors.rejectValue("country", "required", "Field is required.");
			}
		}

		if (!StringUtils.isEmpty(persona.getLanguage())) {
			if (!isoLanguages.contains(persona.getLanguage())) {
				errors.rejectValue("language", "notCandidate", persona
						.getLanguage()
						+ " is not a candidate.");
			}
		}

		if (!StringUtils.isEmpty(persona.getGender())) {
			if (!genders.contains(persona.getGender())) {
				errors.rejectValue("gender", "notCandidate",
						new Object[] { persona.getGender() }, persona
								.getGender()
								+ " is not a candidate.");
			}
		}

		/*
		if (persona.getDob() != null) {
			if (persona.getDob().after(new Date())) {
				errors.rejectValue("dob", "error.dateOfBirth.future",
						"Will birth?");
			}
		}
		*/
	}
}
