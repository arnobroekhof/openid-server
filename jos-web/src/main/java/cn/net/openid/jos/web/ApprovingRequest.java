/**
 * Created on 2008-5-29 下午10:14:33
 */
package cn.net.openid.jos.web;

import java.io.Serializable;

import org.openid4java.message.AuthRequest;

/**
 * @author Sutra Zhou
 * 
 */
public class ApprovingRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6688267787986929480L;

	private String token;
	private AuthRequest authRequest;

	/**
	 * 
	 */
	public ApprovingRequest() {
	}

	/**
	 * @param authRequest
	 */
	public ApprovingRequest(AuthRequest authRequest) {
		this.authRequest = authRequest;
	}

	/**
	 * @param token
	 * @param authRequest
	 */
	public ApprovingRequest(String token, AuthRequest authRequest) {
		this.token = token;
		this.authRequest = authRequest;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the authRequest
	 */
	public AuthRequest getAuthRequest() {
		return authRequest;
	}

	/**
	 * @param authRequest
	 *            the authRequest to set
	 */
	public void setAuthRequest(AuthRequest authRequest) {
		this.authRequest = authRequest;
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
		result = prime * result + ((token == null) ? 0 : token.hashCode());
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
		final ApprovingRequest other = (ApprovingRequest) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

}
