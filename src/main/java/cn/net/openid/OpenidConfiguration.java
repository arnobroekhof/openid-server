/**
 * Created on 2006-11-7 下午11:12:53
 */
package cn.net.openid;

import java.io.Serializable;

/**
 * @author Shutra
 * 
 */
public class OpenidConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5457863007358751006L;

	private String memberUsernamePattern;

	private String openidUrlPrefix;

	private String openidUrlSuffix;

	/**
	 * @return the memberUsernamePattern
	 */
	public String getMemberUsernamePattern() {
		return memberUsernamePattern;
	}

	/**
	 * @return the openidUrlPrefix
	 */
	public String getOpenidUrlPrefix() {
		return openidUrlPrefix;
	}

	/**
	 * @return the openidUrlSuffix
	 */
	public String getOpenidUrlSuffix() {
		return openidUrlSuffix;
	}

	/**
	 * @param memberUsernamePattern
	 *            the memberUsernamePattern to set
	 */
	public void setMemberUsernamePattern(String memberUsernamePattern) {
		this.memberUsernamePattern = memberUsernamePattern;
	}

	/**
	 * @param openidUrlPrefix
	 *            the openidUrlPrefix to set
	 */
	public void setOpenidUrlPrefix(String openidUrlPrefix) {
		this.openidUrlPrefix = openidUrlPrefix;
	}

	/**
	 * @param openidUrlSuffix
	 *            the openidUrlSuffix to set
	 */
	public void setOpenidUrlSuffix(String openidUrlSuffix) {
		this.openidUrlSuffix = openidUrlSuffix;
	}

}
