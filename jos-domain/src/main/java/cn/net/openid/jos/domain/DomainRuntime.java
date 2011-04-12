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
 * Created on 2008-8-18 12:59:53
 */
package cn.net.openid.jos.domain;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

/**
 * Represents {@link Domain} runtime properties.
 * 
 * @author Sutra Zhou
 */
public class DomainRuntime implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2165382191028480331L;

	/**
	 * Build server base URL.
	 * 
	 * @param domain
	 *            the domain
	 * @param requestUrl
	 *            the HTTP request URL
	 * @param requestContextPath
	 *            the HTTP request context path
	 * @return the base URL of the server
	 */
	public static URL buildServerBaseUrl(final Domain domain,
			final URL requestUrl, final String requestContextPath) {
		// protocol
		final String protocol = requestUrl.getProtocol();

		// host
		StringBuilder host = new StringBuilder();
		if (!StringUtils.isEmpty(domain.getServerHost())) {
			host.append(domain.getServerHost()).append('.');
		}
		host.append(domain.getName());

		// port
		final int port = requestUrl.getPort();

		// path
		final String path = requestContextPath + "/";

		try {
			return new URL(protocol, host.toString(), port, path);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Build OP(OpenID Provider) Endpoint URL.
	 * 
	 * @param domain
	 *            the domain
	 * @param serverBaseUrl
	 *            the server base URL
	 * @return the OpenID server URL
	 */
	public static URL buildEndpointUrl(final Domain domain,
			final URL serverBaseUrl) {
		// protocol
		Boolean httpsEnabled = domain.getBooleanAttribute("https.endpoint.enabled");
		final String protocol;
		if (httpsEnabled != null) {
			protocol = httpsEnabled.booleanValue() ? "https" : "http";
		} else {
			protocol = serverBaseUrl.getProtocol();
		}

		// host
		final String host = serverBaseUrl.getHost();

		// port
		final int port = serverBaseUrl.getPort();

		// path
		final String path = serverBaseUrl.getPath() + "server";

		try {
			return new URL(protocol, host, port, path);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Server base URL.
	 */
	private URL serverBaseUrl;

	/**
	 * OP Endpoint URL.
	 * <p>
	 * The URL which accepts OpenID Authentication protocol messages, obtained
	 * by performing discovery on the User-Supplied Identifier. This value MUST
	 * be an absolute HTTP or HTTPS URL.
	 * </p>
	 */
	private URL endpointUrl;

	/**
	 * @return the serverBaseUrl
	 */
	public URL getServerBaseUrl() {
		return serverBaseUrl;
	}

	/**
	 * @param serverBaseUrl
	 *            the serverBaseUrl to set
	 */
	public void setServerBaseUrl(final URL serverBaseUrl) {
		this.serverBaseUrl = serverBaseUrl;
	}

	/**
	 * @return the OP Endpoint URL
	 */
	public URL getEndpointUrl() {
		return endpointUrl;
	}

	/**
	 * @param endpointUrl
	 *            the OP Endpoint URL to set
	 */
	public void setEndpointUrl(final URL endpointUrl) {
		this.endpointUrl = endpointUrl;
	}

}
