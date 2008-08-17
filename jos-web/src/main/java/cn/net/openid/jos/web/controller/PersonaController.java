/**
 * Created on 2006-10-29 下午01:43:33
 */
package cn.net.openid.jos.web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Persona;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.service.TimeZoneOffsetFormat;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.filter.UserAgentLocalesFilter;

/**
 * @author Sutra Zhou
 * 
 */
public class PersonaController extends AbstractJosSimpleFormController {
	private TimeZoneOffsetFormat offsetFormat = new TimeZoneOffsetFormat();
	private LocaleResolver localeResolver;

	/**
	 * @param localeResolver
	 *            the localeResolver to set
	 */
	public void setLocaleResolver(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject
	 * (javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.BaseCommandController#initBinder(
	 * javax.servlet.http.HttpServletRequest,
	 * org.springframework.web.bind.ServletRequestDataBinder)
	 */
	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		/*
		 * binder.registerCustomEditor(Date.class, "dob", new CustomDateEditor(
		 * new SimpleDateFormat("yyyy-MM-dd"), true));
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.BaseCommandController#onBindAndValidate
	 * (javax.servlet.http.HttpServletRequest, java.lang.Object,
	 * org.springframework.validation.BindException)
	 */
	@Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		Persona persona = (Persona) command;
		if (log.isDebugEnabled()) {
			log.debug("persona dob: " + persona.getDob());
		}
		super.onBindAndValidate(request, command, errors);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#referenceData
	 * (javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map data = super.referenceData(request);
		if (data == null) {
			data = new HashMap<Object, Object>();
		}

		final Locale locale = this.localeResolver.resolveLocale(request);

		// Locale
		String c, l;
		final Map<String, String> relatedCountries = new LinkedHashMap<String, String>();
		final Map<String, String> relatedLanguages = new LinkedHashMap<String, String>();

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

		final Map<String, String> countries = new LinkedHashMap<String, String>();
		final Map<String, String> languages = new LinkedHashMap<String, String>();

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
		final Map<String, String> timezones = new LinkedHashMap<String, String>();
		String[] timezoneIds = TimeZone.getAvailableIDs();
		String format = this.getMessageSourceAccessor().getMessage("timeZone");
		for (String timeZoneId : timezoneIds) {
			TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
			String shortName = timeZone.getDisplayName(false, TimeZone.SHORT,
					locale);
			String longName = timeZone.getDisplayName(false, TimeZone.LONG,
					locale);
			int offset = timeZone.getRawOffset();
			String displayTimeZone = String.format(format, offsetFormat
					.format(offset), shortName, longName, timeZone.getID());
			timezones.put(timeZone.getID(), displayTimeZone);
		}
		data.put("timezones", timezones);

		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		Persona persona = (Persona) command;
		if (StringUtils.isEmpty(persona.getId())) {
			getJosService().insertPersona(getUser(request), persona);
		} else {
			getJosService().updatePersona(getUser(request), persona);
		}
		return super.onSubmit(request, response, command, errors);
	}

}
