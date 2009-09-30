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
 * Created on 2008-9-3 02:17:45
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
 */
public class DomainSessionLocaleResolver extends SessionLocaleResolver {
	/**
	 * The logger.
	 */
	private static final Log LOG = LogFactory
			.getLog(DomainSessionLocaleResolver.class);

	/**
	 * Indicate if the logger is in debug mode.
	 */
	private static final boolean DEBUG = LOG.isDebugEnabled();

	/**
	 * The JOS service.
	 */
	private JosService josService;

	/**
	 * @param josService
	 *            the josService to set
	 */
	public void setJosService(final JosService josService) {
		this.josService = josService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Locale determineDefaultLocale(final HttpServletRequest request) {
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

	/**
	 * Find user agent locale which is available in JOS.
	 * 
	 * @param request
	 *            the HTTP request
	 * @return the locale sent from the user agent which is available in JOS,
	 *         null if use agent does not provide locale information or no one
	 *         is available
	 */
	@SuppressWarnings("unchecked")
	private Locale findUserAgentLocale(final HttpServletRequest request) {
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
			LOG.debug("find user agent locale: " + ret);
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
	private Locale getDomainDefaultLocale(final HttpServletRequest request) {
		Domain domain = DomainFilter.getDomain(request);
		if (domain != null) {
			String defaultLocale = domain.getConfiguration().get(
					"defaultLocale");
			if (DEBUG) {
				LOG.debug("domain default locale: " + defaultLocale);
			}
			return LocaleUtils.toLocale(defaultLocale);
		} else {
			if (DEBUG) {
				LOG.debug("domain is null.");
			}
			return null;
		}
	}
}
