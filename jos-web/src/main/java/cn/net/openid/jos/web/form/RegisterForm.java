/**
 * Created on 2006-10-16 上午12:43:06
 */
package cn.net.openid.jos.web.form;

import java.io.Serializable;

import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public class RegisterForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8100263157196112732L;

	private User user = new User();
	private String password;
	private String confirmingPassword;

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
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
