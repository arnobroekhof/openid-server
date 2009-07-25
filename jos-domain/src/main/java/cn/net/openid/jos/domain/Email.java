/**
 * Created on 2008-3-10 21:31:39
 */
package cn.net.openid.jos.domain;

import java.util.Locale;

/**
 * @author Sutra Zhou
 * 
 */
public class Email extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6706127085088356027L;

	/**
	 * The prime value for {@code #hashCode()}.
	 */
	private static final int PRIME = 31;

	/**
	 * The owner of the email.
	 */
	private User user = new User();
	/**
	 * Email address.
	 */
	private String address;
	/**
	 * The locale of messages that the email owner prefer to received via this
	 * email address.
	 */
	private Locale locale = Locale.getDefault();
	/**
	 * Is this the primary email address of the owner?
	 */
	private boolean primary;
	/**
	 * Is this email address confirmed by owner via email.
	 */
	private boolean confirmed;
	/**
	 * Email address confirmation information.
	 */
	private EmailConfirmationInfo emailConfirmationInfo;

	/**
	 * @return user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(final User user) {
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
	 *            the address to set
	 */
	public void setAddress(final String address) {
		this.address = address;
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
	public void setLocale(final Locale locale) {
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
	 *            the confirmed to set
	 */
	public void setConfirmed(final boolean confirmed) {
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
	 *            the primary to set
	 */
	public void setPrimary(final boolean primary) {
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
			final EmailConfirmationInfo emailConfirmationInfo) {
		this.emailConfirmationInfo = emailConfirmationInfo;
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
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Email)) {
			return false;
		}
		final Email other = (Email) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

}
