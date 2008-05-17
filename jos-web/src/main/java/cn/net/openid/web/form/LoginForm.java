/**
 * Created on 2006-10-7 上午12:29:03
 */
package cn.net.openid.web.form;

import java.io.Serializable;

/**
 * @author Sutra Zhou
 * 
 */
public class LoginForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5037879147649703902L;

	private String username;

	private String password;

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
