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
 * Created on 2008-5-29 22:14:33
 */
package cn.net.openid.jos.web;

import java.io.Serializable;

import org.openid4java.message.AuthRequest;

/**
 * @author Sutra Zhou
 */
public class ApprovingRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6688267787986929480L;

	/**
	 * The token of the approving request.
	 */
	private String token;

	/**
	 * The authentication request.
	 */
	private AuthRequest authRequest;

	/**
	 * Constructs an approving request.
	 */
	public ApprovingRequest() {
	}

	/**
	 * Constructs an approving request with the authentication request.
	 * 
	 * @param authRequest
	 *            the authentication request
	 */
	public ApprovingRequest(final AuthRequest authRequest) {
		this.authRequest = authRequest;
	}

	/**
	 * Constructs an approving request with the token and authentication
	 * request.
	 * 
	 * @param token
	 *            the token
	 * @param authRequest
	 *            the authentication request
	 */
	public ApprovingRequest(final String token, final AuthRequest authRequest) {
		this.token = token;
		this.authRequest = authRequest;
	}

	/**
	 * Gets the token.
	 * 
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 * 
	 * @param token
	 *            the token to set
	 */
	public void setToken(final String token) {
		this.token = token;
	}

	/**
	 * Gets the authentication request.
	 * 
	 * @return the authRequest
	 */
	public AuthRequest getAuthRequest() {
		return authRequest;
	}

	/**
	 * Sets the authentication request.
	 * 
	 * @param authRequest
	 *            the authRequest to set
	 */
	public void setAuthRequest(final AuthRequest authRequest) {
		this.authRequest = authRequest;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ApprovingRequest other = (ApprovingRequest) obj;
		if (token == null) {
			if (other.token != null) {
				return false;
			}
		} else if (!token.equals(other.token)) {
			return false;
		}
		return true;
	}
}
