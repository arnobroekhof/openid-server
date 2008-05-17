/**
 * Created on 2006-11-7 下午11:12:53
 */
package cn.net.openid;

import java.io.Serializable;

/**
 * @author Shutra
 * 
 */
public class JosConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5457863007358751006L;

	public static final String CONFIGURATION_BEAN_NAME = "openId.configurationBeanName";

	public static final String CONFIGURATION_ATTRIBUTE_NAME = "openId.configurationAttributeName";

	private String openIdServer;

	private String memberUsernamePattern;

	private String openIdUrlPrefix;

	private String openIdUrlSuffix;

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
	 * @return the openIdServer
	 */
	public String getOpenIdServer() {
		return openIdServer;
	}

	/**
	 * @return the openIdUrlPrefix
	 */
	public String getOpenIdUrlPrefix() {
		return openIdUrlPrefix;
	}

	/**
	 * @return the openIdUrlSuffix
	 */
	public String getOpenIdUrlSuffix() {
		return openIdUrlSuffix;
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
	 * @param openIdServer
	 *            the openIdServer to set
	 */
	public void setOpenIdServer(String openidServer) {
		this.openIdServer = openidServer;
	}

	/**
	 * @param openIdUrlPrefix
	 *            the openIdUrlPrefix to set
	 */
	public void setOpenIdUrlPrefix(String openidUrlPrefix) {
		this.openIdUrlPrefix = openidUrlPrefix;
	}

	/**
	 * @param openIdUrlSuffix
	 *            the openIdUrlSuffix to set
	 */
	public void setOpenIdUrlSuffix(String openidUrlSuffix) {
		this.openIdUrlSuffix = openidUrlSuffix;
	}

}
