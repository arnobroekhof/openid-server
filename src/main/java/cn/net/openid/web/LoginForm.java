/**
 * Created on 2006-10-7 上午12:29:03
 */
package cn.net.openid.web;

import java.io.Serializable;

/**
 * @author Shutra
 * 
 */
public class LoginForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5037879147649703902L;

	private String openidUrl;

	private String username;

	private String password;

	/**
	 * @return the openidUrl
	 */
	public String getOpenidUrl() {
		return openidUrl;
	}

	/**
	 * @param openidUrl
	 *            the openidUrl to set
	 */
	public void setOpenidUrl(String openidUrl) {
		this.openidUrl = openidUrl;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
