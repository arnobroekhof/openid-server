/**
 * Created on 2008-3-10 下午09:31:39
 */
package cn.net.openid.jos.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

/**
 * @author Sutra Zhou
 * 
 */
public class Email implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6706127085088356027L;

	private String id;
	private User user = new User();
	private String address;
	private Locale locale = Locale.getDefault();
	private boolean primary;
	private boolean confirmed;
	private EmailConfirmationInfo emailConfirmationInfo;
	private Date creationDate = new Date();

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
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            要设置的 address
	 */
	public void setAddress(String emailAddress) {
		this.address = emailAddress;
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
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * @return confirmed
	 */
	public boolean isConfirmed() {
		return confirmed;
	}

	/**
	 * @param confirmed
	 *            要设置的 confirmed
	 */
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * @return primary
	 */
	public boolean isPrimary() {
		return primary;
	}

	/**
	 * @param primary
	 *            要设置的 primary
	 */
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	/**
	 * @return the emailConfirmationInfo
	 */
	public EmailConfirmationInfo getEmailConfirmationInfo() {
		return emailConfirmationInfo;
	}

	/**
	 * @param emailConfirmationInfo
	 *            the emailConfirmationInfo to set
	 */
	public void setEmailConfirmationInfo(
			EmailConfirmationInfo emailConfirmationInfo) {
		this.emailConfirmationInfo = emailConfirmationInfo;
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
		if (getClass() != obj.getClass())
			return false;
		final Email other = (Email) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
