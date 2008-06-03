/**
 * Created on 2008-3-10 下午09:39:35
 */
package cn.net.openid.jos.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sutra Zhou
 * 
 */
public class EmailConfirmationInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1421672591504003924L;

	private String id;
	private Email email = new Email();
	private String confirmationCode;
	private boolean sent;
	private Date sentDate;
	private boolean confirmed;
	private Date confirmedDate;
	private Date creationDate = new Date();

	/**
	 * 
	 */
	public EmailConfirmationInfo() {
	}

	/**
	 * @param email
	 */
	public EmailConfirmationInfo(Email email) {
		this.email = email;
	}

	/**
	 * @param email
	 * @param confirmationCode
	 */
	public EmailConfirmationInfo(Email email, String confirmationCode) {
		this.email = email;
		this.confirmationCode = confirmationCode;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public Email getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(Email email) {
		this.email = email;
	}

	/**
	 * @return the confirmationCode
	 */
	public String getConfirmationCode() {
		return confirmationCode;
	}

	/**
	 * @param confirmationCode
	 *            the confirmationCode to set
	 */
	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	/**
	 * @return the sent
	 */
	public boolean isSent() {
		return sent;
	}

	/**
	 * @param sent
	 *            the sent to set
	 */
	public void setSent(boolean sent) {
		this.sent = sent;
	}

	/**
	 * @return the sentDate
	 */
	public Date getSentDate() {
		return sentDate;
	}

	/**
	 * @param sentDate
	 *            the sentDate to set
	 */
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	/**
	 * @return the confirmed
	 */
	public boolean isConfirmed() {
		return confirmed;
	}

	/**
	 * @return the confirmedDate
	 */
	public Date getConfirmedDate() {
		return confirmedDate;
	}

	/**
	 * @param confirmedDate
	 *            the confirmedDate to set
	 */
	public void setConfirmedDate(Date confirmedDate) {
		this.confirmedDate = confirmedDate;
	}

	/**
	 * @param confirmed
	 *            the confirmed to set
	 */
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
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
		final EmailConfirmationInfo other = (EmailConfirmationInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
