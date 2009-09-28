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
 * Created on 2008-3-5 22:28:19
 */
package cn.net.openid.jos.domain;

import java.util.Date;

/**
 * Password entity.
 * 
 * @author Sutra Zhou
 */
public class Password extends BaseEntity {
	/**
	 * Indicate the password has infinite service times.
	 */
	public static final int INFINITE_SERVICE_TIMES = -1;
	/**
	 * Indicate one-time password.
	 */
	public static final int ONE_TIME = 1;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7141923018810122892L;

	/**
	 * The prime value for hash code for calculating.
	 */
	private static final int PRIME = 31;

	/**
	 * The owner of the password.
	 */
	private User user = new User();

	/**
	 * The display name of the password.
	 */
	private String name;

	/**
	 * Plain text.
	 */
	private String plaintext;

	/**
	 * The SHA HEX value of the password.
	 */
	private String shaHex;

	/**
	 * The used time of the password.
	 */
	private long usedTimes;

	/**
	 * Last used date of the password.
	 */
	private Date lastUsedDate = new Date();

	/**
	 * The maximum service times of the password.
	 */
	private int maximumServiceTimes = INFINITE_SERVICE_TIMES;

	/**
	 * Construct a default password.
	 */
	public Password() {
	}

	/**
	 * Construct a password of the specified owner.
	 * 
	 * @param user
	 *            the owner
	 */
	public Password(final User user) {
		this.user = user;
	}

	/**
	 * Get the display name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the display name.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Get the plain text.
	 * 
	 * @return plaintext
	 */
	public String getPlaintext() {
		return plaintext;
	}

	/**
	 * Set the plain text.
	 * 
	 * @param plaintext
	 *            the plaintext to set
	 */
	public void setPlaintext(final String plaintext) {
		this.plaintext = plaintext;
	}

	/**
	 * Get the owner.
	 * 
	 * @return user the owner of the password
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Set the owner.
	 * 
	 * @param user
	 *            the owner to set
	 */
	public void setUser(final User user) {
		this.user = user;
	}

	/**
	 * Get the SHA HEX value of the password.
	 * 
	 * @return shaHex the SHA HEX value
	 */
	public String getShaHex() {
		return shaHex;
	}

	/**
	 * Set the SHA HEX value.
	 * 
	 * @param shaHex
	 *            the shaHex value to set
	 */
	public void setShaHex(final String shaHex) {
		this.shaHex = shaHex;
	}

	/**
	 * Get used times.
	 * 
	 * @return the usedTimes
	 */
	public long getUsedTimes() {
		return usedTimes;
	}

	/**
	 * Set the used time.
	 * 
	 * @param usedTimes
	 *            the usedTimes to set
	 */
	public void setUsedTimes(final long usedTimes) {
		this.usedTimes = usedTimes;
	}

	/**
	 * Get the last used date.
	 * 
	 * @return the lastUsedDate
	 */
	public Date getLastUsedDate() {
		return cloneDate(lastUsedDate);
	}

	/**
	 * Set the last used date.
	 * 
	 * @param lastUsedDate
	 *            the lastUsedDate to set
	 */
	public void setLastUsedDate(final Date lastUsedDate) {
		this.lastUsedDate = cloneDate(lastUsedDate);
	}

	/**
	 * Get the maximum service times.
	 * 
	 * @return the maximumServiceTimes
	 */
	public int getMaximumServiceTimes() {
		return maximumServiceTimes;
	}

	/**
	 * Set the maximum service times.
	 * 
	 * @param maximumServiceTimes
	 *            the maximumServiceTimes to set
	 */
	public void setMaximumServiceTimes(final int maximumServiceTimes) {
		this.maximumServiceTimes = maximumServiceTimes;
	}

	/**
	 * Get the password is usable.
	 * 
	 * @return true if the password is usable, otherwise false
	 */
	public boolean isUsable() {
		return this.getMaximumServiceTimes() == Password.INFINITE_SERVICE_TIMES
				? true
				: this.getUsedTimes() < this.getMaximumServiceTimes();
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
		if (!(obj instanceof Password)) {
			return false;
		}
		final Password other = (Password) obj;
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
