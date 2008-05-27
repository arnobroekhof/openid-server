/**
 * Created on 2008-3-5 下午10:28:19
 */
package cn.net.openid.jos.domain;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Password other = (Password) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
