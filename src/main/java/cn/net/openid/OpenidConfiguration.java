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

	public static final String CONFIGURATION_BEAN_NAME = "openid.configurationBeanName";

	public static final String CONFIGURATION_ATTRIBUTE_NAME = "openid.configurationAttributeName";

	private String openidServer;

	private String memberUsernamePattern;

	private String openidUrlPrefix;

	private String openidUrlSuffix;

	private String memberFilterFromPattern;

	/**
	 * @return the memberFilterFromPattern
	 */
	public String getMemberFilterFromPattern() {
		return memberFilterFromPattern;
	}

	/**
	 * @return the memberUsernamePattern
	 */
	public String getMemberUsernamePattern() {
		return memberUsernamePattern;
	}

	/**
	 * @return the openidServer
	 */
	public String getOpenidServer() {
		return openidServer;
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
	 * @param memberFilterFromPattern
	 *            the memberFilterFromPattern to set
	 */
	public void setMemberFilterFromPattern(String memberFilterFromPattern) {
		this.memberFilterFromPattern = memberFilterFromPattern;
	}

	/**
	 * @param memberUsernamePattern
	 *            the memberUsernamePattern to set
	 */
	public void setMemberUsernamePattern(String memberUsernamePattern) {
		this.memberUsernamePattern = memberUsernamePattern;
	}

	/**
	 * @param openidServer
	 *            the openidServer to set
	 */
	public void setOpenidServer(String openidServer) {
		this.openidServer = openidServer;
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
