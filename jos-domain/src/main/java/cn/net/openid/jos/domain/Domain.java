/**
 * Created on 2008-8-5 下午11:01:40
 */
package cn.net.openid.jos.domain;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

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
 * <code>http(s)://[&lt;serverHost&gt;.]&lt;your-domain&gt;[:&lt;port&gt;]contextPath/</code>
 * </td>
 * </tr>
 * <tr>
 * <td>identifier:</td>
 * <td>
 * <code>http(s)://&lt;username&gt;.&lt;your-domain&gt;[:&lt;port&gt;]contextPath/[&lt;memberPath&gt;/]</code>
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
 * <code>http(s)://[&lt;serverHost&gt;.]&lt;your-domain&gt;[:&lt;port&gt;]contextPath/</code>
 * </td>
 * </tr>
 * <tr>
 * <td>identifier:</td>
 * <td>
 * <code>http(s)://&lt;your-domain&gt;[:&lt;port&gt;]contextPath/[&lt;memberPath&gt;/]&lt;username&gt;</code>
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
 * 
 */
public class Domain extends DomainConfiguration implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7341781651134648946L;

	/**
	 * Username as a subdomain.
	 */
	public static final int TYPE_SUBDOMAIN = 1;

	/**
	 * Username as a subdirectory.
	 */
	public static final int TYPE_SUBDIRECTORY = 2;

	private String id;

	/**
	 * your-domain.
	 */
	private String name;
	private int type;
	private String serverHost;
	private String memberPath;

	private boolean openRegistration;
	private Date creationDate = new Date();

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	public void setName(String name) {
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
	public void setType(int type) {
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
	public void setServerHost(String serverHost) {
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
	public void setMemberPath(String memberPath) {
		this.memberPath = memberPath;
	}

	/**
	 * @return the openRegistration
	 */
	public boolean isOpenRegistration() {
		return openRegistration;
	}

	/**
	 * @param openRegistration
	 *            the openRegistration to set
	 */
	public void setOpenRegistration(boolean openRegistration) {
		this.openRegistration = openRegistration;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Append the port, if the port is not the default port.
	 * 
	 * @param sb
	 * @param serverBaseUrl
	 */
	private void appendPort(StringBuilder sb, URL serverBaseUrl) {
		int port = serverBaseUrl.getPort();
		if (port != serverBaseUrl.getDefaultPort()) {
			sb.append(':').append(port);
		}
	}

	/**
	 * Append member path if the memberPath is not empty.
	 * 
	 * @param sb
	 */
	private void appendMemberPath(StringBuilder sb) {
		if (!StringUtils.isEmpty(this.getMemberPath())) {
			sb.append(this.getMemberPath()).append('/');
		}
	}

	/**
	 * @return the identifierPrefix
	 */
	public String getIdentifierPrefix() {
		StringBuilder sb = new StringBuilder();
		URL baseUrl = this.getServerBaseUrl();

		switch (getType()) {
		case Domain.TYPE_SUBDOMAIN:
			sb.append(baseUrl.getProtocol()).append("://");
			break;
		case Domain.TYPE_SUBDIRECTORY:
			sb.append(baseUrl.getProtocol()).append("://");
			sb.append(getName());
			appendPort(sb, baseUrl);
			sb.append(baseUrl.getPath()).append("/");
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
		URL baseUrl = this.getServerBaseUrl();

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getName() == null) ? 0 : getName().hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Domain))
			return false;
		Domain other = (Domain) obj;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}

}
