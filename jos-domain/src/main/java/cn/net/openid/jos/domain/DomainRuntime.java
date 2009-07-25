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
