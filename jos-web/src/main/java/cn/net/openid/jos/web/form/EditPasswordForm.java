/**
 * Created on 2006-10-29 02:58:18
 */
package cn.net.openid.jos.web.form;

import java.io.Serializable;

import cn.net.openid.jos.domain.Password;

/**
 * @author Sutra Zhou
 * 
 */
public class EditPasswordForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5154439238450014108L;

	private Password password = new Password();
	private String retypedPassword;

	/**
	 * @return the password
	 */
	public Password getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(Password password) {
		this.password = password;
	}

	/**
	 * @return the retypedPassword
	 */
	public String getRetypedPassword() {
		return retypedPassword;
	}

	/**
	 * @param retypedPassword
	 *            the retypedPassword to set
	 */
	public void setRetypedPassword(String retypedPassword) {
		this.retypedPassword = retypedPassword;
	}

}
