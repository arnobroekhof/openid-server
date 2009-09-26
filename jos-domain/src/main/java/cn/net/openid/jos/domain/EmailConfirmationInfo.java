/**
 * Created on 2008-3-10 21:39:35
 */
package cn.net.openid.jos.domain;

import java.util.Date;

/**
 * @author Sutra Zhou
 * 
 */
public class EmailConfirmationInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1421672591504003924L;

	/**
	 * The prime value for hash code calculating.
	 */
	private static final int PRIME = 31;

	/**
	 * The email of the confirmation info.
	 */
	private Email email = new Email();
	/**
	 * The confirmation code.
	 */
	private String confirmationCode;
	/**
	 * Is the confirmation info has sent to the email.
	 */
	private boolean sent;
	/**
	 * The confirmation info sending date.
	 */
	private Date sentDate;
	/**
	 * Is the confirmation info has been confirmed.
	 */
	private boolean confirmed;
	/**
	 * Confirm date.
	 */
	private Date confirmedDate;

	/**
	 * Construct a default email confirmation info.
	 */
	public EmailConfirmationInfo() {
	}

	/**
	 * @param email
	 *            the email
	 */
	public EmailConfirmationInfo(final Email email) {
		this.email = email;
	}

	/**
	 * @param email
	 *            the email
	 * @param confirmationCode
	 *            the email confirmation info
	 */
	public EmailConfirmationInfo(final Email email,
			final String confirmationCode) {
		this.email = email;
		this.confirmationCode = confirmationCode;
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
	public void setEmail(final Email email) {
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
	public void setConfirmationCode(final String confirmationCode) {
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
	public void setSent(final boolean sent) {
		this.sent = sent;
	}

	/**
	 * @return the sentDate
	 */
	public Date getSentDate() {
		return cloneDate(sentDate);
	}

	/**
	 * @param sentDate
	 *            the sentDate to set
	 */
	public void setSentDate(final Date sentDate) {
		this.sentDate = cloneDate(sentDate);
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
		return cloneDate(confirmedDate);
	}

	/**
	 * @param confirmedDate
	 *            the confirmedDate to set
	 */
	public void setConfirmedDate(final Date confirmedDate) {
		this.confirmedDate = cloneDate(confirmedDate);
	}

	/**
	 * @param confirmed
	 *            the confirmed to set
	 */
	public void setConfirmed(final boolean confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result = 1;
		result = PRIME * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof EmailConfirmationInfo)) {
			return false;
		}
		final EmailConfirmationInfo other = (EmailConfirmationInfo) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
