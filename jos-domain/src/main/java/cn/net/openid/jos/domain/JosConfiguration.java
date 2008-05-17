/**
 * Created on 2008-05-18 06:09:04
 */
package cn.net.openid.jos.domain;

import java.io.Serializable;

/**
 * @author Sutra Zhou
 * 
 */
public class JosConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -422179045234771173L;

	private String openidServerUrl;
	private String identifierPrefix;
	private String identifierSuffix;
	private String usernamePattern;
	private String memberFilterFromPattern;

	/**
	 * @return the openidServerUrl
	 */
	public String getOpenidServerUrl() {
		return openidServerUrl;
	}

	/**
	 * @param openidServerUrl
	 *            the openidServerUrl to set
	 */
	public void setOpenidServerUrl(String openidServerUrl) {
		this.openidServerUrl = openidServerUrl;
	}

	/**
	 * @return the identifierPrefix
	 */
	public String getIdentifierPrefix() {
		return identifierPrefix;
	}

	/**
	 * @param identifierPrefix
	 *            the identifierPrefix to set
	 */
	public void setIdentifierPrefix(String identifierPrefix) {
		this.identifierPrefix = identifierPrefix;
	}

	/**
	 * @return the identifierSuffix
	 */
	public String getIdentifierSuffix() {
		return identifierSuffix;
	}

	/**
	 * @param identifierSuffix
	 *            the identifierSuffix to set
	 */
	public void setIdentifierSuffix(String identifierSuffix) {
		this.identifierSuffix = identifierSuffix;
	}

	/**
	 * @return the usernamePattern
	 */
	public String getUsernamePattern() {
		return usernamePattern;
	}

	/**
	 * @param usernamePattern
	 *            the usernamePattern to set
	 */
	public void setUsernamePattern(String usernamePattern) {
		this.usernamePattern = usernamePattern;
	}

	/**
	 * @return the memberFilterFromPattern
	 */
	public String getMemberFilterFromPattern() {
		return memberFilterFromPattern;
	}

	/**
	 * @param memberFilterFromPattern
	 *            the memberFilterFromPattern to set
	 */
	public void setMemberFilterFromPattern(String memberFilterFromPattern) {
		this.memberFilterFromPattern = memberFilterFromPattern;
	}

}
