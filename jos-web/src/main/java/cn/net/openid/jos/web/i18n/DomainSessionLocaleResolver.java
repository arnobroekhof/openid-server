/**
 * Created on 2008-9-3 上午02:17:45
 */
package cn.net.openid.jos.web.i18n;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.service.JosService;
import cn.net.openid.jos.web.filter.DomainFilter;

/**
 * Try locales one by one which user-agent provides in available locales, if all
 * failed, use domain default locale, if domain default locale is not specified,
 * use request.getLocale().
 * 
 * @author Sutra Zhou
 * 
 */
public class DomainSessionLocaleResolver extends SessionLocaleResolver {
	private static final Log log = LogFactory
			.getLog(DomainSessionLocaleResolver.class);
	private static final boolean DEBUG = log.isDebugEnabled();

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
	 * @seeorg.springframework.web.servlet.i18n.SessionLocaleResolver#
	 * determineDefaultLocale(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Locale determineDefaultLocale(HttpServletRequest request) {
		Locale defaultLocale = getDefaultLocale();
		if (defaultLocale == null) {
			// Find prefer locale of UserAgent which in available languages.
			defaultLocale = this.findUserAgentLocale(request);
			if (defaultLocale == null) {
				// Find domain default locale.
				defaultLocale = this.getDomainDefaultLocale(request);
				if (defaultLocale == null) {
					// Fallback
					defaultLocale = request.getLocale();
				}
			}
		}
		return defaultLocale;
	}

	@SuppressWarnings("unchecked")
	private Locale findUserAgentLocale(HttpServletRequest request) {
		Locale ret = null;
		Collection<Locale> availableLocales = this.josService
				.getAvailableLocales();
		Enumeration<Locale> enumeration = request.getLocales();
		Locale locale;
		while (enumeration.hasMoreElements()) {
			locale = enumeration.nextElement();
			if (availableLocales.contains(locale)) {
				ret = locale;
				break;
			}
		}
		if (DEBUG) {
			log.debug("find user agent locale: " + ret);
		}
		return ret;
	}

	/**
	 * Get the defaultLocale of current domain.
	 * 
	 * @param request
	 *            the HTTP request
	 * @return the defaultLocale of current domain, null if not specified or
	 *         domain is null.
	 */
	private Locale getDomainDefaultLocale(HttpServletRequest request) {
		Domain domain = DomainFilter.getDomain(request);
		if (domain != null) {
			String defaultLocale = domain.getConfiguration().get(
					"defaultLocale");
			if (DEBUG) {
				log.debug("domain default locale: " + defaultLocale);
			}
			return LocaleUtils.toLocale(defaultLocale);
		} else {
			if (DEBUG) {
				log.debug("domain is null.");
			}
			return null;
		}
	}
}
