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
 * Created on 2006-10-28 21:01:01
 */
package cn.net.openid.jos.web;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.net.openid.jos.domain.User;

/**
 * The user session, stored the data of user who are logging or logged in.
 * 
 * @author Sutra Zhou
 */
public class UserSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8227402637586478669L;

	/**
	 * The logger.
	 */
	private static final Log LOG = LogFactory.getLog(UserSession.class);

	/**
	 * The user.
	 */
	private User user = new User();

	/**
	 * Indicate if the user logged in.
	 */
	private boolean loggedIn = false;

	/**
	 * The approving requests.
	 */
	private Map<String, ApprovingRequest> approvingRequests =
		new HashMap<String, ApprovingRequest>();

	/**
	 * Sets the user.
	 * 
	 * @param user
	 *            the user to set
	 */
	public void setUser(final User user) {
		this.user = user;
	}

	/**
	 * Gets the user.
	 * 
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Returns if the user is logged in.
	 * 
	 * @return the loggedIn true if the user is logged in, otherwise false
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * Modify the logged in status of the user.
	 * 
	 * @param loggedIn
	 *            the loggedIn to set
	 */
	public void setLoggedIn(final boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/* Authentication Requests */

	/**
	 * Adds approving request.
	 * 
	 * @param approvingRequest
	 *            the approving request to add
	 * @return the token for retrieving the added approving request
	 */
	public String addApprovingRequest(final ApprovingRequest approvingRequest) {
		if (approvingRequest.getToken() == null) {
			String id = WebUtils.generateToken();
			approvingRequest.setToken(id);
		}
		if (!this.approvingRequests.containsKey(approvingRequest.getToken())) {
			this.approvingRequests.put(approvingRequest.getToken(),
					approvingRequest);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("Add approving request: " + approvingRequest.getToken());
		}
		return approvingRequest.getToken();
	}

	/**
	 * Gets the approving request by the token.
	 * 
	 * @param token
	 *            the token
	 * @return the approving request associated to the token
	 */
	public ApprovingRequest getApprovingRequest(final String token) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("get approving request: " + token);
		}
		return this.approvingRequests.get(token);
	}

	/**
	 * Removes the approving request by the token.
	 * 
	 * @param token
	 *            the token
	 * @return the removed approving request
	 */
	public ApprovingRequest removeApprovingRequest(final String token) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Remove approving request: " + token);
		}
		return this.approvingRequests.remove(token);
	}

	/**
	 * Gets all approving requests in this user session.
	 * 
	 * @return all approving requests
	 */
	public Collection<ApprovingRequest> getApprovingRequests() {
		return this.approvingRequests.values();
	}
}
