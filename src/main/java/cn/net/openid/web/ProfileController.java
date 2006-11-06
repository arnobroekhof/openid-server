/**
 * Created on 2006-10-29 下午01:43:33
 */
package cn.net.openid.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cn.net.openid.User;
import cn.net.openid.dao.DaoFacade;

/**
 * @author Shutra
 * 
 */
public class ProfileController extends SimpleFormController {

	private DaoFacade daoFacade;

	private MessageSource messageSource;

	private LocaleResolver localeResolver;

	private String formatOffset(int offset) {
		int m = offset / (1000 * 60);
		int h = m / 60;
		StringBuffer sb = new StringBuffer();
		if (offset > 0) {
			sb.append('+');
		} else {
			sb.append('-');
		}
		if (Math.abs(h) < 10) {
			sb.append('0');
		}
		sb.append(Math.abs(h)).append(":");
		m = m - h * 60;
		sb.append(Math.abs(m));
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		String userId = userSession.getUserId();
		User user = this.daoFacade.getUser(userId);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#initBinder(javax.servlet.http.HttpServletRequest,
	 *      org.springframework.web.bind.ServletRequestDataBinder)
	 */
	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		binder.registerCustomEditor(Date.class, "dob", new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#onBindAndValidate(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		super.onBindAndValidate(request, command, errors);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java.lang.Object)
	 */
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		User user = (User) command;
		this.daoFacade.updateUser(user);
		return super.onSubmit(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map map = super.referenceData(request);
		if (map == null) {
			map = new HashMap<Object, Object>();
		}

		Locale locale = this.localeResolver.resolveLocale(request);

		final Map<String, String> timezones = new LinkedHashMap<String, String>();
		final Map<String, String> countries = new LinkedHashMap<String, String>();
		final Map<String, String> languages = new LinkedHashMap<String, String>();

		Locale[] locales = Locale.getAvailableLocales();
		countries.put("", "--");
		languages.put("", "--");
		for (Locale l : locales) {
			if (!StringUtils.isEmpty(l.getCountry())
					&& !countries.containsKey(l.getCountry())) {
				countries.put(l.getCountry(), l.getDisplayCountry(locale));
			}
			if (!StringUtils.isEmpty(l.getLanguage())
					&& !languages.containsKey(l.getLanguage())) {
				languages.put(l.getLanguage(), l.getDisplayLanguage(locale));
			}
		}

		map.put("countries", countries);
		map.put("languages", languages);

		String[] timezoneIds = TimeZone.getAvailableIDs();
		timezones.put("", "--");
		for (String timeZoneId : timezoneIds) {
			TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
			String longName = timeZone.getDisplayName(locale);
			int offset = timeZone.getRawOffset();
			timezones.put(timeZone.getID(), String.format(this.messageSource
					.getMessage("timezone", new String[] {}, locale), this
					.formatOffset(offset), longName, timeZone.getID()));
		}

		map.put("timezones", timezones);
		return map;
	}

	/**
	 * @param daoFacade
	 *            the daoFacade to set
	 */
	public void setDaoFacade(DaoFacade daoFacade) {
		this.daoFacade = daoFacade;
	}

	/**
	 * @param localeResolver
	 *            the localeResolver to set
	 */
	public void setLocaleResolver(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
