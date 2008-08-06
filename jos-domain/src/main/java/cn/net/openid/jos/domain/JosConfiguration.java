/**
 * Created on 2008-05-18 06:09:04
 */
package cn.net.openid.jos.domain;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * @author Sutra Zhou
 * 
 */
public class JosConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -422179045234771173L;

	private String baseUrl;
	private String openidServerUrl;
	private String identifierPrefix;
	private String identifierSuffix;
	private String usernameRegex = "[a-z]{1,16}";
	private Pattern usernamePattern = Pattern.compile(usernameRegex);
	private String reservedUsernameRegex = "root|toor|wheel|staff|admin|administrator";
	private Pattern reservedUsernamePattern = Pattern.compile(
			reservedUsernameRegex, Pattern.CASE_INSENSITIVE);
	private String unallowableUsernameRegex = "w+|home|server|approve.*|approving|register|login|logout|email.*|password.*|persona.*|site.*|attribute.*|hl|member|news|jos|mail|smtp|pop3|pop|.*fuck.*";
	private Pattern unallowableUsernamePattern = Pattern.compile(
			unallowableUsernameRegex, Pattern.CASE_INSENSITIVE);
	private String memberFilterFromRegex;

	/**
	 * @return the baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * @param baseUrl
	 *            the baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

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
	 * @return the memberFilterFromRegex
	 */
	public String getMemberFilterFromRegex() {
		return memberFilterFromRegex;
	}

	/**
	 * @param memberFilterFromRegex
	 *            the memberFilterFromRegex to set
	 */
	public void setMemberFilterFromRegex(String memberFilterFromRegex) {
		this.memberFilterFromRegex = memberFilterFromRegex;
	}

}
