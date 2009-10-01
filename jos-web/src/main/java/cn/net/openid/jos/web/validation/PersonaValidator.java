/**
 * Copyright (c) 2006-2009, Redv.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Redv.com nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * Created on 2006-11-5 22:29:59
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
 * Persona validator.
 * 
 * @author Sutra Zhou
 */
public class PersonaValidator implements Validator {
	/**
	 * The date of birth checking pattern.
	 */
	private static final Pattern DOB_PATTERN = Pattern
			.compile("(\\d{4})-(\\d{2})-(\\d{2})");

	/**
	 * ISO countries.
	 */
	private static Collection<String> isoCountries;

	/**
	 * ISO languages.
	 */
	private static Collection<String> isoLanguages;

	/**
	 * Genders.
	 */
	private static Collection<String> genders;

	/**
	 * Total count of genders for choosing.
	 */
	private static final int GENDER_COUNT = 5;

	static {
		String[] c = Locale.getISOCountries();
		isoCountries = new ArrayList<String>(c.length);
		Collections.addAll(isoCountries, c);

		c = Locale.getISOLanguages();
		isoLanguages = new ArrayList<String>(c.length);
		Collections.addAll(isoLanguages, c);

		genders = new ArrayList<String>(GENDER_COUNT);
		genders.add("U");
		genders.add("M");
		genders.add("F");
		genders.add("A");
		genders.add("E");
	}

	/**
	 * The email address checking pattern.
	 */
	private Pattern emailAddressPattern;

	/**
	 * Initializes persona validator.
	 * 
	 * @param emailAddressPattern
	 *            the email address checking pattern to set
	 */
	public PersonaValidator(Pattern emailAddressPattern) {
		this.emailAddressPattern = emailAddressPattern;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(final Class clazz) {
		return Persona.class.isAssignableFrom(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(final Object target, final Errors errors) {
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
			Matcher matcher = DOB_PATTERN.matcher(persona.getDob());
			if (!matcher.matches()) {
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
