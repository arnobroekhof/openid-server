/**
 * Created on 2008-3-5 下午10:28:19
 */
package cn.net.openid.jos.domain;

import java.io.Serializable;
import java.util.Date;

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
	private User user = new User();
	private String name;
	private String plaintext;
	private String shaHex;
	private Date creationDate = new Date();

	/**
	 * 
	 */
	public Password() {
	}

	/**
	 * @param user
	 */
	public Password(User user) {
		this.user = user;
	}

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return plaintext
	 */
	public String getPlaintext() {
		return plaintext;
	}

	/**
	 * @param plaintext
	 *            要设置的 plaintext
	 */
	public void setPlaintext(String plaintext) {
		this.plaintext = plaintext;
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
	 * @return shaHex
	 */
	public String getShaHex() {
		return shaHex;
	}

	/**
	 * @param shaHex
	 *            要设置的 shaHex
	 */
	public void setShaHex(String shaHex) {
		this.shaHex = shaHex;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
		if (!(obj instanceof Password))
			return false;
		final Password other = (Password) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
