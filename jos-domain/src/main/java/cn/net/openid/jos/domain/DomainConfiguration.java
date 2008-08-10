/**
 * Created on 2008-05-18 06:09:04
 */
package cn.net.openid.jos.domain;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author Sutra Zhou
 * 
 */
public abstract class DomainConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -422179045234771173L;

	private URL serverBaseUrl;
	private URL openidServerUrl;

	/* Username patterns */
	private String usernameRegex = "[a-z]{1,16}";
	private Pattern usernamePattern = Pattern.compile(usernameRegex);
	private String reservedUsernameRegex = "root|toor|wheel|staff|admin|administrator";
	private Pattern reservedUsernamePattern = Pattern.compile(
			reservedUsernameRegex, Pattern.CASE_INSENSITIVE);
	private String unallowableUsernameRegex = "w+|home|server|approve.*|approving|register|login|logout|email.*|password.*|persona.*|site.*|attribute.*|hl|member|news|jos|mail|smtp|pop3|pop|.*fuck.*";
	private Pattern unallowableUsernamePattern = Pattern.compile(
			unallowableUsernameRegex, Pattern.CASE_INSENSITIVE);

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
	public void setServerBaseUrl(URL serverBaseUrl) {
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
	public void setOpenidServerUrl(URL openidServerUrl) {
		this.openidServerUrl = openidServerUrl;
	}

	/**
	 * @return the usernameRegex
	 */
	public String getUsernameRegex() {
		return usernameRegex;
	}

	/**
	 * @param usernameRegex
	 *            the usernameRegex to set
	 */
	public void setUsernameRegex(String usernameRegex) {
		this.usernameRegex = usernameRegex;
		this.usernamePattern = null;
	}

	/**
	 * @return the usernamePattern
	 */
	public Pattern getUsernamePattern() {
		if (this.usernamePattern == null && this.getUsernameRegex() != null) {
			this.usernamePattern = Pattern.compile(this.getUsernameRegex());
		}
		return usernamePattern;
	}

	/**
	 * @return the reservedUsernameRegex
	 */
	public String getReservedUsernameRegex() {
		return reservedUsernameRegex;
	}

	/**
	 * @param reservedUsernameRegex
	 *            the reservedUsernameRegex to set
	 */
	public void setReservedUsernameRegex(String reservedUsernameRegex) {
		this.reservedUsernameRegex = reservedUsernameRegex;
		this.reservedUsernamePattern = null;
	}

	/**
	 * @return the reservedUsernamePattern
	 */
	public Pattern getReservedUsernamePattern() {
		if (this.reservedUsernamePattern == null
				&& this.getReservedUsernameRegex() != null) {
			this.reservedUsernamePattern = Pattern.compile(this
					.getReservedUsernameRegex(), Pattern.CASE_INSENSITIVE);
		}
		return reservedUsernamePattern;
	}

	/**
	 * Check whether the username is reserved.
	 * 
	 * @param username
	 *            the username
	 * @return true if reserved, otherwise false.
	 */
	public boolean isReservedUsername(String username) {
		return this.getReservedUsernamePattern().matcher(username).matches();
	}

	/**
	 * @return the unallowableUsernameRegex
	 */
	public String getUnallowableUsernameRegex() {
		return unallowableUsernameRegex;
	}

	/**
	 * @param unallowableUsernameRegex
	 *            the unallowableUsernameRegex to set
	 */
	public void setUnallowableUsernameRegex(String unallowableUsernameRegex) {
		this.unallowableUsernameRegex = unallowableUsernameRegex;
		this.unallowableUsernamePattern = null;
	}

	/**
	 * @return the unallowableUsernamePattern
	 */
	public Pattern getUnallowableUsernamePattern() {
		if (this.unallowableUsernamePattern == null
				&& this.getUnallowableUsernameRegex() != null) {
			this.unallowableUsernamePattern = Pattern.compile(this
					.getUnallowableUsernameRegex(), Pattern.CASE_INSENSITIVE);
		}
		return unallowableUsernamePattern;
	}

	/**
	 * Check whether the username is unallowable.
	 * 
	 * @param username
	 *            the username
	 * @return true if unallowable, otherwise false.
	 */
	public boolean isUnallowableUsername(String username) {
		return this.getUnallowableUsernamePattern().matcher(username).matches();
	}

	public static URL buildServerBaseUrl(Domain domain, URL requestUrl,
			String requestContextPath) {
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

}
