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
 * Created on 2008-8-5 23:01:40
 */
package cn.net.openid.jos.domain;

import java.net.URL;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * Virtual domain model.
 * <p>
 * The following are the types. The parameter "contextPath" depends on your
 * webapp, usually we installed in the ROOT directory, the contextPath is "".
 * </p>
 * <ol>
 * <li>type = 1:
 * <table border="1">
 * <tr>
 * <td>server base url:</td>
 * <td>
 * <code>
 * http(s)://[&lt;serverHost&gt;.]&lt;your-domain&gt;[:&lt;port&gt;]contextPath/
 * </code>
 * </td>
 * </tr>
 * <tr>
 * <td>identifier:</td>
 * <td>
 * <code>
 * http(s)://&lt;username&gt;.&lt;your-domain&gt;[:&lt;port&gt;]contextPath/[&lt;memberPath&gt;/]
 * </code>
 * </td>
 * </tr>
 * </table>
 * Examples:
 * <ul>
 * <li>
 * <table border="1">
 * <tr>
 * <td>your-domain:</td>
 * <td><code>example.com</code></td>
 * </tr>
 * <tr>
 * <td>serverHost:</td>
 * <td>null or empty</td>
 * </tr>
 * <tr>
 * <td>memberPath:</td>
 * <td>null or empty</td>
 * </tr>
 * <tr>
 * <td>server base url:</td>
 * <td><code>http(s)://example.com/</code></td>
 * </tr>
 * <tr>
 * <td>identifier:</td>
 * <td><code>http(s)://username.example.com/</code></td>
 * </tr>
 * </table>
 * </li>
 * <li>
 * <table border="1">
 * <tr>
 * <td>your-domain:</td>
 * <td><code>example.com</code></td>
 * </tr>
 * <tr>
 * <td>serverHost:</td>
 * <td><code>www</code></td>
 * </tr>
 * <tr>
 * <td>memberPath:</td>
 * <td>null or empty</td>
 * </tr>
 * <tr>
 * <td>server base url:</td>
 * <td><code>http(s)://www.example.com/</code></td>
 * </tr>
 * <tr>
 * <td>identifier:</td>
 * <td><code>http(s)://username.example.com/</code></td>
 * </tr>
 * </table>
 * </li>
 * <li>
 * <table border="1">
 * <tr>
 * <td>your-domain:</td>
 * <td><code>example.com</code></td>
 * </tr>
 * <tr>
 * <td>serverHost:</td>
 * <td>null or empty</td>
 * <tr>
 * <td>memberPath:</td>
 * <td><code>member</code></td>
 * <tr>
 * <td>server base url:</td>
 * <td><code>http(s)://example.com/</code></td>
 * </tr>
 * <tr>
 * <td>identifier:</td>
 * <td>
 * <code>http(s)://username.example.com/member/</code></td>
 * </tr>
 * </table>
 * </li>
 * <li>
 * <table border="1">
 * <tr>
 * <td>your-domain:</td>
 * <td><code>example.com</code></td>
 * </tr>
 * <tr>
 * <td>serverHost:</td>
 * <td><code>www</code></td>
 * <tr>
 * <td>memberPath:</td>
 * <td><code>member</code></td>
 * <tr>
 * <td>server base url:</td>
 * <td><code>http(s)://www.example.com/</code></td>
 * </tr>
 * <tr>
 * <td>identifier:</td>
 * <td>
 * <code>http(s)://username.example.com/member/</code></td>
 * </tr>
 * </table>
 * </li>
 * </ul>
 * </li>
 * <li>type = 2:
 * <table border="1">
 * <tr>
 * <td>server base url:</td>
 * <td>
 * <code>
 * http(s)://[&lt;serverHost&gt;.]&lt;your-domain&gt;[:&lt;port&gt;]contextPath/
 * </code>
 * </td>
 * </tr>
 * <tr>
 * <td>identifier:</td>
 * <td>
 * <code>
 * http(s)://&lt;your-domain&gt;[:&lt;port&gt;]contextPath/[&lt;memberPath&gt;/]&lt;username&gt;
 * </code>
 * </td>
 * </tr>
 * </table>
 * Examples:
 * <ul>
 * <li>
 * <table border="1">
 * <tr>
 * <td>your-domain:</td>
 * <td><code>openid.example.com</code></td>
 * </tr>
 * <tr>
 * <td>sercerHost:</td>
 * <td>null or empty</td>
 * </tr>
 * <tr>
 * <td>memberPath:</td>
 * <td>null or empty</td>
 * </tr>
 * <tr>
 * <td>server base url:</td>
 * <td><code>http(s)://openid.example.com/</code></td>
 * </tr>
 * <tr>
 * <td>identifier:</td>
 * <td>
 * <code>http(s)://openid.example.com/username</code></td>
 * </tr>
 * </table>
 * </li>
 * <li>
 * <table border="1">
 * <tr>
 * <td>your-domain:</td>
 * <td><code>openid.example.com</code></td>
 * </tr>
 * <tr>
 * <td>serverHost:</td>
 * <td><code>www</code></td>
 * </tr>
 * <tr>
 * <td>memberPath:</td>
 * <td>null or empty</td>
 * </tr>
 * <tr>
 * <td>server base url:</td>
 * <td><code>http(s)://www.openid.example.com/</code></td>
 * </tr>
 * <tr>
 * <td>identifier:</td>
 * <td>
 * <code>http(s)://openid.example.com/username</code></td>
 * </tr>
 * </table>
 * </li>
 * <li>
 * <table border="1">
 * <tr>
 * <td>your-domain:</td>
 * <td><code>openid.example.com</code></td>
 * </tr>
 * <tr>
 * <td>serverHost:</td>
 * <td>null or empty</td>
 * </tr>
 * <tr>
 * <td>memberPath:</td>
 * <td><code>member</code></td>
 * </tr>
 * <tr>
 * <td>server base url:</td>
 * <td><code>http(s)://openid.example.com/</code></td>
 * </tr>
 * <tr>
 * <td>identifier:</td>
 * <td>
 * <code>http(s)://openid.example.com/member/username</code></td>
 * </tr>
 * </table>
 * </li>
 * <li>
 * <table border="1">
 * <tr>
 * <td>your-domain:</td>
 * <td><code>openid.example.com</code></td>
 * </tr>
 * <tr>
 * <td>serverHost:</td>
 * <td><code>www</code></td>
 * </tr>
 * <tr>
 * <td>memberPath:</td>
 * <td><code>member</code></td>
 * </tr>
 * <tr>
 * <td>server base url:</td>
 * <td><code>http(s)://www.openid.example.com/</code></td>
 * </tr>
 * <tr>
 * <td>identifier:</td>
 * <td>
 * <code>http(s)://openid.example.com/member/username</code></td>
 * </tr>
 * </table>
 * </li>
 * </ul>
 * </li>
 * </ol>
 * 
 * @author Sutra Zhou
 */
public class Domain extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7341781651134648946L;

	/**
	 * The prime value for {@code #hashCode()}.
	 */
	private static final int PRIME = 31;

	/**
	 * Username as a subdomain.
	 */
	public static final int TYPE_SUBDOMAIN = 1;

	/**
	 * Username as a subdirectory.
	 */
	public static final int TYPE_SUBDIRECTORY = 2;

	/**
	 * your-domain.
	 */
	private String name;

	/**
	 * Domain type.
	 */
	private int type;

	/**
	 * The server host of the domain.
	 */
	private String serverHost;

	/**
	 * Member page path.
	 */
	private String memberPath;

	/**
	 * Username configuration.
	 */
	private UsernameConfiguration usernameConfiguration =
		new UsernameConfiguration();

	/**
	 * Extended configuration.
	 */
	private Map<String, String> configuration;

	/**
	 * Domain runtime information.
	 */
	private DomainRuntime runtime;

	/**
	 * Construct a new domain.
	 */
	public Domain() {
		runtime = new DomainRuntime();
	}

	/**
	 * Construct a new domain.
	 * 
	 * @param name the name
	 * @param type the type
	 */
	public Domain(final String name, final int type) {
		this();
		this.name = name;
		this.type = type;
	}

	/**
	 * Construct a new domain.
	 * 
	 * @param name the name
	 * @param type the type
	 * @param serverHost the server host
	 */
	public Domain(final String name, final int type, final String serverHost) {
		this(name, type);
		this.serverHost = serverHost;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(final int type) {
		this.type = type;
	}

	/**
	 * @return the serverHost
	 */
	public String getServerHost() {
		return serverHost;
	}

	/**
	 * @param serverHost
	 *            the serverHost to set
	 */
	public void setServerHost(final String serverHost) {
		this.serverHost = serverHost;
	}

	/**
	 * @return the memberPath
	 */
	public String getMemberPath() {
		return memberPath;
	}

	/**
	 * @param memberPath
	 *            the memberPath to set
	 */
	public void setMemberPath(final String memberPath) {
		this.memberPath = memberPath;
	}

	/**
	 * @return the usernameConfiguration
	 */
	public UsernameConfiguration getUsernameConfiguration() {
		return usernameConfiguration;
	}

	/**
	 * @param usernameConfiguration
	 *            the usernameConfiguration to set
	 */
	public void setUsernameConfiguration(
			final UsernameConfiguration usernameConfiguration) {
		this.usernameConfiguration = usernameConfiguration;
		usernameConfiguration.setDomain(this);
	}

	/**
	 * @return the configuration
	 */
	public Map<String, String> getConfiguration() {
		return configuration;
	}

	/**
	 * @param configuration
	 *            the configuration to set
	 */
	public void setConfiguration(final Map<String, String> configuration) {
		this.configuration = configuration;
	}

	/**
	 * @return the runtime
	 */
	public DomainRuntime getRuntime() {
		return runtime;
	}

	/**
	 * @param runtime
	 *            the runtime to set
	 */
	public void setRuntime(final DomainRuntime runtime) {
		this.runtime = runtime;
	}

	/**
	 * @return the identifierPrefix
	 */
	public String getIdentifierPrefix() {
		StringBuilder sb = new StringBuilder();
		URL baseUrl = this.getRuntime().getServerBaseUrl();

		// protocol
		Boolean httpsEnabled = getBooleanAttribute("https.identifier.enabled");
		final String protocol;
		if (httpsEnabled != null) {
			protocol = httpsEnabled.booleanValue() ? "https" : "http";
		} else {
			protocol = baseUrl.getProtocol();
		}
		sb.append(protocol).append("://");

		switch (getType()) {
		case Domain.TYPE_SUBDOMAIN:
			break;
		case Domain.TYPE_SUBDIRECTORY:
			sb.append(getName());
			appendPort(sb, baseUrl);
			sb.append(baseUrl.getPath());
			appendMemberPath(sb);
			break;
		default:
			break;
		}

		return sb.toString();
	}

	/**
	 * @return the identifierSuffix
	 */
	public String getIdentifierSuffix() {
		StringBuilder sb = new StringBuilder();
		URL baseUrl = this.getRuntime().getServerBaseUrl();

		switch (getType()) {
		case Domain.TYPE_SUBDOMAIN:
			sb.append('.').append(getName());
			appendPort(sb, baseUrl);
			sb.append(baseUrl.getPath());
			appendMemberPath(sb);
			break;
		case Domain.TYPE_SUBDIRECTORY:
			break;
		default:
			break;
		}

		return sb.toString();
	}

	/**
	 * Get the value of an extended attribute.
	 * 
	 * @param attributeName
	 *            the attribute name
	 * @return the value of the attribute, null if no such attribute
	 */
	public String getAttribute(final String attributeName) {
		return getAttribute(attributeName, null);
	}

	/**
	 * Get the value of the extended attribute.
	 * 
	 * @param attributeName
	 *            the attribute name
	 * @param defaultValue
	 *            the default value while no such attribute
	 * @return the value of the attribute, returns the <code>defaultValue</code>
	 *         if no such attribute
	 */
	public String getAttribute(final String attributeName,
			final String defaultValue) {
		Map<String, String> configuration = this.getConfiguration();
		final String attributeValue;
		if (configuration != null) {
			attributeValue = configuration.get(attributeName);
		} else {
			attributeValue = defaultValue;
		}
		return attributeValue;
	}

	/**
	 * Get boolean value of an extended attribute.
	 * 
	 * @param attributeName
	 *            the attribute name
	 * @return boolean value of the attribute. null, if no such attribute
	 */
	public Boolean getBooleanAttribute(final String attributeName) {
		return getBooleanAttribute(attributeName, null);
	}

	/**
	 * Get boolean value of an extended attribute.
	 * 
	 * @param attributeName
	 *            the attribute name
	 * @param defaultValue
	 *            the default value while no such attribute
	 * @return boolean value of the attribute. Returns the
	 *         <code>defaultValue</code> if no such attribute
	 */
	public Boolean getBooleanAttribute(final String attributeName,
			final Boolean defaultValue) {
		final String attributeValue = getAttribute(attributeName);
		if (attributeValue != null) {
			return Boolean.parseBoolean(attributeValue);
		} else {
			return defaultValue;
		}
	}

	/**
	 * Get int value of an extended attribute.
	 * 
	 * @param attributeName
	 *            the attribute name
	 * @return int value of the attribute
	 */
	public int getIntAttribute(final String attributeName) {
		return NumberUtils.toInt(this.getConfiguration().get(attributeName));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result = 1;
		result = PRIME * result
				+ ((getName() == null) ? 0 : getName().hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Domain)) {
			return false;
		}
		Domain other = (Domain) obj;
		if (getName() == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!getName().equals(other.getName())) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getName();
	}

	/**
	 * Append the port, if the port is not the default port.
	 * 
	 * @param sb
	 *            the {@code StringBuilder} to appended to
	 * @param serverBaseUrl
	 *            the base url of the server
	 */
	private void appendPort(final StringBuilder sb, final URL serverBaseUrl) {
		int port = serverBaseUrl.getPort();
		if (port != -1 && port != serverBaseUrl.getDefaultPort()) {
			sb.append(':').append(port);
		}
	}

	/**
	 * Append member path if the memberPath is not empty.
	 * 
	 * @param sb
	 *            the {@code StringBuilder} to appended to
	 */
	private void appendMemberPath(final StringBuilder sb) {
		if (!StringUtils.isEmpty(this.getMemberPath())) {
			sb.append(this.getMemberPath()).append('/');
		}
	}

}
