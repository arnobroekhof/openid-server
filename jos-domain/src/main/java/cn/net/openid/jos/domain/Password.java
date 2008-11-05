/**
 * Created on 2008-3-5 22:28:19
 */
package cn.net.openid.jos.domain;

import java.util.Date;

/**
 * @author Sutra Zhou
 * 
 */
public class Password extends BaseEntity {
	public static final int INFINITE_SERVICE_TIMES = -1;
	public static final int SINGLE_USE = 1;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7141923018810122892L;

	private User user = new User();
	private String name;
	private String plaintext;
	private String shaHex;
	private long usedTimes;
	private Date lastUsedDate = new Date();
	private int maximumServiceTimes = INFINITE_SERVICE_TIMES;

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
	 * @return the usedTimes
	 */
	public long getUsedTimes() {
		return usedTimes;
	}

	/**
	 * @param usedTimes
	 *            the usedTimes to set
	 */
	public void setUsedTimes(long usedTimes) {
		this.usedTimes = usedTimes;
	}

	/**
	 * @return the lastUsedDate
	 */
	public Date getLastUsedDate() {
		return lastUsedDate;
	}

	/**
	 * @param lastUsedDate
	 *            the lastUsedDate to set
	 */
	public void setLastUsedDate(Date lastUsedDate) {
		this.lastUsedDate = lastUsedDate;
	}

	/**
	 * @return the maximumServiceTimes
	 */
	public int getMaximumServiceTimes() {
		return maximumServiceTimes;
	}

	/**
	 * @param maximumServiceTimes
	 *            the maximumServiceTimes to set
	 */
	public void setMaximumServiceTimes(int maximumServiceTimes) {
		this.maximumServiceTimes = maximumServiceTimes;
	}

	public boolean isUseful() {
		return this.getMaximumServiceTimes() == Password.INFINITE_SERVICE_TIMES ? true
				: this.getUsedTimes() < this.getMaximumServiceTimes();
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
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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
