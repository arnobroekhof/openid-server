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
 * Created on 2006-10-29 13:43:33
 */
package cn.net.openid.jos.web.controller;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Attribute;
import cn.net.openid.jos.domain.Persona;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.service.TimeZoneOffsetFormat;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.filter.UserAgentLocalesFilter;

/**
 * The controller to add/modify a persona.
 * 
 * @author Sutra Zhou
 */
public class PersonaController extends AbstractJosSimpleFormController {
	/**
	 * The logger.
	 */
	private static final Log LOG = LogFactory.getLog(PersonaController.class);

	/**
	 * The formatter for offset.
	 */
	private TimeZoneOffsetFormat offsetFormat = new TimeZoneOffsetFormat();

	/**
	 * The locale resolver.
	 */
	private LocaleResolver localeResolver;

	/**
	 * @param localeResolver
	 *            the localeResolver to set
	 */
	public void setLocaleResolver(final LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {
		User user = getUser(request);
		String id = request.getParameter("id");
		Persona persona;
		if (StringUtils.isEmpty(id)) {
			persona = new Persona(user);
		} else {
			persona = getJosService().getPersona(user, id);
			if (persona == null) {
				persona = new Persona(user);
			}
		}
		return persona;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initBinder(final HttpServletRequest request,
			final ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		/*
		 * binder.registerCustomEditor(Date.class, "dob", new CustomDateEditor(
		 * new SimpleDateFormat("yyyy-MM-dd"), true));
		 */
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onBindAndValidate(final HttpServletRequest request,
			final Object command, final BindException errors) throws Exception {
		Persona persona = (Persona) command;

		persona.clearAttributes();
		addAttributes(persona, request);

		if (LOG.isDebugEnabled()) {
			LOG.debug("isSessionForm: " + isSessionForm());
			LOG.debug("persona dob: " + persona.getDob());
			LOG.debug("---- attibutes ----");
			LOG.debug("attribute count: " + persona.getAttributes().size());
			for (Iterator<Attribute> iterator = persona.getAttributes()
					.iterator(); iterator.hasNext();) {
				Attribute attribute = iterator.next();
				LOG.debug(String.format("%1$s(%2$s)=%3$s",
						attribute.getAlias(), attribute.getType(), attribute
								.getValues()));
			}

		}
		super.onBindAndValidate(request, command, errors);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData(final HttpServletRequest request)
			throws Exception {
		Map data = super.referenceData(request);
		if (data == null) {
			data = new HashMap<Object, Object>();
		}

		final Locale locale = this.localeResolver.resolveLocale(request);

		// Locale
		String c, l;
		final Map<String, String> relatedCountries =
			new LinkedHashMap<String, String>();
		final Map<String, String> relatedLanguages =
			new LinkedHashMap<String, String>();

		c = locale.getCountry();
		l = locale.getLanguage();
		if (!StringUtils.isEmpty(c)) {
			relatedCountries.put(c, locale.getDisplayCountry(locale));
		}
		if (!StringUtils.isEmpty(l)) {
			relatedLanguages.put(l, locale.getDisplayLanguage(locale));
		}

		final Collection<Locale> userAgentLocales = UserAgentLocalesFilter
				.getUserAgentLocales(request);
		final Set<String> userAgentCountries = new HashSet<String>();
		final Set<String> userAgentLanguages = new HashSet<String>();
		for (Locale theLocale : userAgentLocales) {
			c = theLocale.getCountry();
			l = theLocale.getLanguage();
			if (!StringUtils.isEmpty(c) && !relatedCountries.containsKey(c)) {
				relatedCountries.put(c, theLocale.getDisplayCountry(locale));
			}
			if (!StringUtils.isEmpty(l) && !relatedLanguages.containsKey(l)) {
				relatedLanguages.put(l, theLocale.getDisplayLanguage(locale));
			}

			userAgentCountries.add(c);
			userAgentLanguages.add(l);
		}

		final Map<String, String> countries =
			new LinkedHashMap<String, String>();
		final Map<String, String> languages =
			new LinkedHashMap<String, String>();

		Locale[] locales = Locale.getAvailableLocales();
		String displayCountry, displayLanguage;
		boolean related;
		for (Locale theLocale : locales) {
			c = theLocale.getCountry();
			l = theLocale.getLanguage();
			related = (userAgentCountries.contains(c) || userAgentLanguages
					.contains(l));
			if (!StringUtils.isEmpty(c)) {
				displayCountry = theLocale.getDisplayCountry(locale);
				if (related && !relatedCountries.containsKey(c)) {
					relatedCountries.put(c, displayCountry);
				}
				if (!countries.containsKey(c)) {
					countries.put(c, displayCountry);
				}
			}
			if (!StringUtils.isEmpty(l)) {
				displayLanguage = theLocale.getDisplayLanguage(locale);
				if (related && !relatedLanguages.containsKey(l)) {
					relatedLanguages.put(c, displayLanguage);
				}
				if (!languages.containsKey(l)) {
					languages.put(l, displayLanguage);
				}
			}
		}

		data.put("relatedCountries", relatedCountries);
		data.put("relatedLanguages", relatedLanguages);

		Map m = new LinkedHashMap();
		m.put(this.getMessageSourceAccessor().getMessage("persona.title.all"),
				countries);
		data.put("countries", m);

		m = new LinkedHashMap();
		m.put(this.getMessageSourceAccessor().getMessage("persona.title.all"),
				languages);
		data.put("languages", m);

		// TimeZone
		final Map<String, String> relatedTimeZones =
			new LinkedHashMap<String, String>();
		Integer userAgentOffset = org.apache.commons.lang.math.NumberUtils
				.createInteger(request.getParameter("offset"));
		int userAgentOffsetValue = userAgentOffset != null ? userAgentOffset
				.intValue() : 0;
		final Map<String, String> timeZones =
			new LinkedHashMap<String, String>();
		String[] timeZoneIds = TimeZone.getAvailableIDs();
		String format = this.getMessageSourceAccessor().getMessage("timeZone");
		for (String timeZoneId : timeZoneIds) {
			TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
			String id = timeZone.getID();
			String shortName = timeZone.getDisplayName(false, TimeZone.SHORT,
					locale);
			String longName = timeZone.getDisplayName(false, TimeZone.LONG,
					locale);
			int offset = timeZone.getRawOffset();
			String displayTimeZone = String.format(format, offsetFormat
					.format(offset), shortName, longName, timeZone.getID());

			if (userAgentOffset != null && userAgentOffsetValue == offset) {
				relatedTimeZones.put(id, displayTimeZone);
			}

			timeZones.put(id, displayTimeZone);
		}
		data.put("relatedTimeZones", relatedTimeZones);

		m = new LinkedHashMap();
		m.put(this.getMessageSourceAccessor().getMessage("persona.title.all"),
				timeZones);
		data.put("timeZones", m);

		return data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {
		Persona persona = (Persona) command;
		if (StringUtils.isEmpty(persona.getId())) {
			getJosService().insertPersona(getUser(request), persona);
		} else {
			getJosService().updatePersona(getUser(request), persona);
		}
		return super.onSubmit(request, response, command, errors);
	}

	/**
	 * Adds attributes from request to persona.
	 * 
	 * @param persona
	 *            the persona
	 * @param request
	 *            the HTTP servlet request
	 */
	@SuppressWarnings("unchecked")
	private static void addAttributes(final Persona persona,
			final HttpServletRequest request) {
		String key, id, alias, type;
		String[] values;
		for (Enumeration<String> names = request.getParameterNames(); names
				.hasMoreElements();) {
			String name = names.nextElement();
			if (name.startsWith("attribute.alias.")) {
				key = name.substring("attribute.alias.".length());
				id = request.getParameter("attribute.id." + key);
				alias = request.getParameter("attribute.alias." + key);
				type = request.getParameter("attribute.type." + key);
				values = request.getParameterValues("attribute.value." + key);

				// Remove all empty strings.
				int oldLen = 0;
				int newLen = values.length;
				do {
					oldLen = newLen;
					values = (String[]) ArrayUtils.removeElement(values,
							StringUtils.EMPTY);
					newLen = values.length;
				} while (newLen != oldLen);

				if (StringUtils.isNotEmpty(alias)
						&& !ArrayUtils.isEmpty(values)) {
					Attribute attribute = new Attribute(persona, alias, type,
							values);
					if (StringUtils.isNotEmpty(id)) {
						attribute.setId(id);
					}
					persona.addAttribute(attribute);
					if (LOG.isDebugEnabled()) {
						LOG.debug(String.format(
								"id: %1$s, alias: %2$s, values: %3$s.",
								attribute.getId(), attribute.getAlias(),
								attribute.getValues()));
					}
				}
			}
		}
	}
}
