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
 * 
 */
public class UserSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8227402637586478669L;

	private static final Log log = LogFactory.getLog(UserSession.class);

	private User user = new User();
	private boolean loggedIn = false;
	private Map<String, ApprovingRequest> approvingRequests = new HashMap<String, ApprovingRequest>();

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * @param loggedIn
	 *            the loggedIn to set
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/* Authentication Requests */

	public String addApprovingRequest(ApprovingRequest approvingRequest) {
		if (approvingRequest.getToken() == null) {
			String id = WebUtils.generateToken();
			approvingRequest.setToken(id);
		}
		if (!this.approvingRequests.containsKey(approvingRequest.getToken())) {
			this.approvingRequests.put(approvingRequest.getToken(),
					approvingRequest);
		}
		if (log.isDebugEnabled()) {
			log.debug("Add approving request: " + approvingRequest.getToken());
		}
		return approvingRequest.getToken();
	}

	public ApprovingRequest getApprovingRequest(String token) {
		if (log.isDebugEnabled()) {
			log.debug("get approving request: " + token);
		}
		return this.approvingRequests.get(token);
	}

	public ApprovingRequest removeApprovingRequest(String token) {
		if (log.isDebugEnabled()) {
			log.debug("Remove approving request: " + token);
		}
		return this.approvingRequests.remove(token);
	}

	public Collection<ApprovingRequest> getApprovingRequests() {
		return this.approvingRequests.values();
	}
}
