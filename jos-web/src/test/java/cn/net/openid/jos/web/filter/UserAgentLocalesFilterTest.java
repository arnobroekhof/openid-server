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
 * Created on 2010-10-30 17:22:15
 */
package cn.net.openid.jos.web.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.ServletException;

import junitx.framework.ArrayAssert;

import org.apache.commons.lang.LocaleUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import cn.net.openid.jos.service.JosServiceImpl;

/**
 * Test methods for {@link cn.net.openid.jos.web.filter.UserAgentLocalesFilter}.
 * 
 * @author Sutra Zhou
 */
public class UserAgentLocalesFilterTest {

	private class LocalesMockHttpServletRequest extends MockHttpServletRequest {

		private Vector<Locale> locales = new Vector<Locale>();

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Enumeration<Locale> getLocales() {
			return locales.elements();
		}

		public void addLocale(Locale locale) {
			locales.add(locale);
		}
	}

	private class LocalesService extends JosServiceImpl {

		private Collection<Locale> locales = new HashSet<Locale>();

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Collection<Locale> getAvailableLocales() {
			return locales;
		}

		public void addLocale(Locale locale) {
			locales.add(locale);
		}
	}

	private LocalesMockHttpServletRequest request;

	private MockHttpServletResponse response;

	private MockFilterChain filterChain;

	private UserAgentLocalesFilter ualf;

	private LocalesService service;

	@Before
	public void setUp() {
		request = new LocalesMockHttpServletRequest();
		response = new MockHttpServletResponse();
		filterChain = new MockFilterChain();

		service = new LocalesService();

		ualf = new UserAgentLocalesFilter();
		ualf.setService(service);
	}

	@Test
	public void testDoFilterInternalEmpty() throws ServletException,
			IOException {
		service.addLocale(LocaleUtils.toLocale("en_US"));

		assertEquals(0, Collections.list(request.getLocales()).size());
		assertEquals(1, service.getAvailableLocales().size());

		ualf.doFilterInternal(request, response, filterChain);

		Collection<Locale> locales = UserAgentLocalesFilter
				.getUserAgentLocales(request);
		assertTrue(locales.isEmpty());
	}

	@Test
	public void testDoFilterInternalDuplication() throws ServletException,
			IOException {
		request.addLocale(LocaleUtils.toLocale("en_US"));
		request.addLocale(LocaleUtils.toLocale("zh_CN"));
		request.addLocale(LocaleUtils.toLocale("en_US"));

		service.addLocale(LocaleUtils.toLocale("en_US"));
		service.addLocale(LocaleUtils.toLocale("ja_JP"));
		service.addLocale(LocaleUtils.toLocale("zh_CN"));
		service.addLocale(LocaleUtils.toLocale("zh_TW"));

		assertEquals(3, Collections.list(request.getLocales()).size());
		assertEquals(4, service.getAvailableLocales().size());

		ualf.doFilterInternal(request, response, filterChain);

		Collection<Locale> actualLocales = UserAgentLocalesFilter
				.getUserAgentLocales(request);
		assertEquals(2, actualLocales.size());

		Collection<Locale> expectedLocales = new LinkedHashSet<Locale>();
		expectedLocales.add(LocaleUtils.toLocale("en_US"));
		expectedLocales.add(LocaleUtils.toLocale("zh_CN"));
		ArrayAssert.assertEquals(expectedLocales.toArray(), actualLocales
				.toArray());
	}

}
