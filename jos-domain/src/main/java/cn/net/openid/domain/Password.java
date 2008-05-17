/**
 * Created on 2008-3-5 下午10:28:19
 */
package cn.net.openid.domain;

import java.io.Serializable;

/**
 * @author Sutra Zhou
 * 
 */
public class Password implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7141923018810122892L;

	private String id;

	private User user;

	private String password;

	private String passwordShaHex;

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            要设置的 id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            要设置的 password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            要设置的 user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return passwordShaHex
	 */
	public String getPasswordShaHex() {
		return passwordShaHex;
	}

	/**
	 * @param passwordShaHex
	 *            要设置的 passwordShaHex
	 */
	public void setPasswordShaHex(String passwordShaHex) {
		this.passwordShaHex = passwordShaHex;
	}

}
