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
 * Created on 2008-8-6 12:16:34
 */
package cn.net.openid.jos.web.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.net.openid.jos.domain.Domain;

/**
 * Domain filter. Parse domain information from request URL, and put into
 * session and request.
 * 
 * @author Sutra Zhou
 */
public class DomainFilter extends OncePerRequestServiceFilter {
	/**
	 * The logger.
	 */
	private static final Log LOG = LogFactory.getLog(DomainFilter.class);

	/**
	 * Domain attribute name in HTTP session.
	 */
	private static final String DOMAIN_ATTRIBUTE_NAME = "domain";

	/**
	 * The pattern for URL that should be skip from this filter.
	 */
	private Pattern skipPattern;

	/**
	 * @param skipPattern
	 *            the skipPattern to set
	 */
	public void setSkipPattern(final String skipPattern) {
		LOG.debug("skipPattern setted: " + skipPattern.trim());
		this.skipPattern = Pattern.compile(skipPattern.trim());
	}

	/**
	 * Get domain from <code>request.getSession(false)</code>.
	 * 
	 * @param request
	 *            the HTTP request
	 * @return the domain in the session, null if session is null or not found.
	 * @see DomainFilter#getDomain(HttpSession)
	 */
	public static Domain getDomain(final HttpServletRequest request) {
		return getDomain(request.getSession(false));
	}

	/**
	 * Get domain from the session/request.
	 * 
	 * @param session
	 *            the HTTP session
	 * @return the domain in the session, null if session is null or not found.
	 */
	public static Domain getDomain(final HttpSession session) {
		return session != null ? checkRuntime((Domain) session
				.getAttribute(DOMAIN_ATTRIBUTE_NAME)) : null;
	}

	/**
	 * Check the domain runtime.
	 * <p>
	 * As {@link Domain#getRuntime()} is transient, we should check if it is
	 * null before using. Otherwise, if the <code>domain</code> is restored from
	 * a stream, the {@link Domain#getRuntime()} will be null. For example, <a
	 * href="http://tomcat.apache.org/">Apache Tomcat</a> usually write the
	 * objects in HTTP sessions while stopping though serialization mechanism,
	 * and restore them while re-starting.
	 * </p>
	 * 
	 * @param domain
	 *            the domain to check runtime
	 * @return null if runtime is null, else itself.
	 * @see java.io.Serializable
	 */
	private static Domain checkRuntime(final Domain domain) {
		return (domain != null && domain.getRuntime() != null) ? domain : null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(final HttpServletRequest request,
			final HttpServletResponse response, final FilterChain filterChain)
			throws ServletException, IOException {
		LOG.debug("Begin of domain filter.");
		if (!skip(request)) {
			this.parseDomain(request);

			// Put domain to request.
			this.setDomain(request, getDomain(request.getSession(false)));
		} else if (LOG.isDebugEnabled()) {
			LOG.debug("Skipped domain parsing.");
		}
		filterChain.doFilter(request, response);
		LOG.debug("End of domain filter.");
	}

	/**
	 * Checks if the HTTP request URL should be skip from this filter.
	 * 
	 * @param request
	 *            the HTTP request
	 * @return true if the request URL should be skipped
	 */
	private boolean skip(final HttpServletRequest request) {
		String path = request.getRequestURI().substring(
				request.getContextPath().length());
		return this.skipPattern.matcher(path).matches();
	}

	/**
	 * Parse domain from the HTTP request.
	 * 
	 * @param request
	 *            the HTTP request
	 */
	private void parseDomain(final HttpServletRequest request) {
		if (getDomain(request.getSession(false)) == null) {
			Domain domain = getService().parseDomain(request);
			this.setDomain(request.getSession(), domain);
		}
	}

	/**
	 * Set domain to the HTTP session.
	 * 
	 * @param session
	 *            the HTTP session to set to
	 * @param domain
	 *            the domain to set
	 */
	private void setDomain(final HttpSession session, final Domain domain) {
		LOG.debug("Put domain info into session.");
		session.setAttribute(DOMAIN_ATTRIBUTE_NAME, domain);
	}

	/**
	 * Set domain to the HTTP request.
	 * 
	 * @param request
	 *            the HTTP request to set to
	 * @param domain
	 *            the domain to set
	 */
	private void setDomain(final HttpServletRequest request,
			final Domain domain) {
		LOG.debug("Put domain info into request.");
		request.setAttribute(DOMAIN_ATTRIBUTE_NAME, domain);
	}
}
