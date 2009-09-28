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
 * Created on 2006-10-15 17:11:24
 */
package cn.net.openid.jos.domain;


/**
 * End user entity.
 * 
 * @author Sutra Zhou
 */
public class User extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6219139356897428716L;

	/**
	 * The prime value for hash code calculating.
	 */
	private static final int PRIME = 31;

	/**
	 * The username.
	 */
	private String username;

	/**
	 * The domain.
	 */
	private Domain domain = new Domain();

	/**
	 * Construct a default user.
	 */
	public User() {
	}

	/**
	 * Construct a user of the specified domain and usename.
	 * 
	 * @param domain
	 *            the domain
	 * @param username
	 *            the username
	 */
	public User(final Domain domain, final String username) {
		this.domain = domain;
		this.username = username;
	}

	/**
	 * Get the username.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the username.
	 * 
	 * @param username
	 *            the username to set
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * Get the domain.
	 * 
	 * @return the domain
	 */
	public Domain getDomain() {
		return domain;
	}

	/**
	 * Set the domain.
	 * 
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(final Domain domain) {
		this.domain = domain;
	}

	/**
	 * Get the identifier.
	 * 
	 * @return the identifier
	 */
	public String getIdentifier() {
		return String.format("%1$s%2$s%3$s", getDomain().getIdentifierPrefix(),
				getUsername(), getDomain().getIdentifierSuffix());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result = 1;
		result = PRIME * result
				+ ((getDomain() == null) ? 0 : getDomain().hashCode());
		result = PRIME * result
				+ ((getUsername() == null) ? 0 : getUsername().hashCode());
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
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (getDomain() == null) {
			if (other.getDomain() != null) {
				return false;
			}
		} else if (!getDomain().equals(other.getDomain())) {
			return false;
		}
		if (getUsername() == null) {
			if (other.getUsername() != null) {
				return false;
			}
		} else if (!getUsername().equals(other.getUsername())) {
			return false;
		}
		return true;
	}

}
