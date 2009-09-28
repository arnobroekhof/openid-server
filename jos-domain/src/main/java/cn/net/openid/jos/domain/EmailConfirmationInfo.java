/**
 * Copyright (c) 2006-2009, Redv.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Redv.com nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * Created on 2008-3-10 21:39:35
 */
package cn.net.openid.jos.domain;

import java.util.Date;

/**
 * Email confirmation info entity.
 * 
 * @author Sutra Zhou
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
	public boolean equals(final Object obj) {
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
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

}
