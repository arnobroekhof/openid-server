/**
 * Created on 2006-10-16 上午12:43:06
 */
package cn.net.openid.jos.web.form;

import java.io.Serializable;

/**
 * @author Sutra Zhou
 * 
 */
public class RegisterForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8100263157196112732L;

	private String username;
	private String password;
	private String confirmingPassword;

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

	/**
	 * @return the confirmingPassword
	 */
	public String getConfirmingPassword() {
		return confirmingPassword;
	}

	/**
	 * @param confirmingPassword
	 *            the confirmingPassword to set
	 */
	public void setConfirmingPassword(String confirmingPassword) {
		this.confirmingPassword = confirmingPassword;
	}

}
