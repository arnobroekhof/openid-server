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

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

/**
 * @author Sutra Zhou
 */
public class DomainRuntime {
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
		StringBuilder sb = new StringBuilder();
		if (!StringUtils.isEmpty(domain.getServerHost())) {
			sb.append(domain.getServerHost()).append('.');
		}
		sb.append(domain.getName());

		try {
			return new URL(requestUrl.getProtocol(), sb.toString(), requestUrl
					.getPort(), requestContextPath + "/");
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Server base URL.
	 */
	private URL serverBaseUrl;
	/**
	 * OpenID Server URL.
	 */
	private URL openidServerUrl;

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
	 * @return the openidServerUrl
	 */
	public URL getOpenidServerUrl() {
		return openidServerUrl;
	}

	/**
	 * @param openidServerUrl
	 *            the openidServerUrl to set
	 */
	public void setOpenidServerUrl(final URL openidServerUrl) {
		this.openidServerUrl = openidServerUrl;
	}

}
