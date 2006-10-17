/**
 * Created on 2006-10-17 上午12:27:43
 */
package cn.net.openid;

import java.io.Serializable;

/**
 * @author Shutra
 * 
 */
public class UsernamePasswordCredential implements Credential, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5713456472659569041L;

	private String id;

	private User user;

	private String username;

	private String password;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
