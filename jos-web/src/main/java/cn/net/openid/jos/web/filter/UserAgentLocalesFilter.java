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
 * Created on 2008-6-26 01:47:18
 */
package cn.net.openid.jos.web.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Gets all locales from user agent and retains all that in the human language
 * selection list, then stores it in the request attributes.
 * </p>
 * 
 * When I using
 * 
 * <pre>
 * &lt;div id="hl"&gt;
 * &lt;ul&gt;
 * #foreach($l in $request.locales)
 * &lt;li&gt;&lt;a href=
 * "?hl=$l#if($!request.getParameter('self'))&amp;self=$!request
 * .getParameter('self')#end"
 * &gt;$l.getDisplayName($l)&lt;/a&gt;&lt;/li&gt;
 * #end &lt;li class="last-child"&gt;&lt;a href=
 * "hl#if($!request.getParameter('self'))?self=$!request
 * .getParameter('self')#end"
 * >»&lt;/a&gt;&lt;/li&gt;
 * &lt;/ul&gt;
 * &lt;/div&gt;
 * </pre>
 * 
 * but got a warning:
 * <code>
 * [org.apache.velocity.app.VelocityEngine:46] - Warning!
 * The iterative  is an Enumeration in the #foreach() loop at [0,0] in template
 * header.vm. Because it's not resetable, if used in more than once, this may
 * lead to unexpected results.
 * </code>
 * 
 * So I create this filter.
 * 
 * @author Sutra Zhou
 */
public class UserAgentLocalesFilter extends OncePerRequestServiceFilter {
	/**
	 * The user agent locales attribute name in HTTP request.
	 */
	private static final String USER_AGENT_LOCALES_ATTRIBUTE_NAME
		= "userAgentLocales";

	/**
	 * Gets user agent locales from the HTTP reques.
	 * 
	 * @param request
	 *            the HTTP request
	 * @return user agent locales
	 */
	@SuppressWarnings("unchecked")
	public static Collection<Locale> getUserAgentLocales(
			final ServletRequest request) {
		return (Collection<Locale>) request
				.getAttribute(USER_AGENT_LOCALES_ATTRIBUTE_NAME);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(final HttpServletRequest request,
			final HttpServletResponse response, final FilterChain filterChain)
			throws ServletException, IOException {
		request.setAttribute(USER_AGENT_LOCALES_ATTRIBUTE_NAME, this
				.getLocales(request));
		filterChain.doFilter(request, response);
	}

	/**
	 * Get locales from the HTTP request, and removed all that not is
	 * unavailable.
	 * 
	 * @param request
	 *            the HTTP request.
	 * @return a collection
	 */
	@SuppressWarnings("unchecked")
	private Collection<Locale> getLocales(final HttpServletRequest request) {
		Enumeration<Locale> localesEnum = request.getLocales();
		List<Locale> localesList = Collections.list(localesEnum);

		// Use LinkedHashSet to eliminate duplication as the user agent may send
		// duplicated locales.
		Collection<Locale> localesCollection = new LinkedHashSet(localesList);

		localesCollection.retainAll(this.getService().getAvailableLocales());
		return localesCollection;
	}
}
