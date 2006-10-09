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

	private String openIdUrl;

	private String username;

	private String password;

	/**
	 * @return the openIdUrl
	 */
	public String getOpenIdUrl() {
		return openIdUrl;
	}

	/**
	 * @param openIdUrl
	 *            the openIdUrl to set
	 */
	public void setOpenIdUrl(String openIdUrl) {
		this.openIdUrl = openIdUrl;
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
