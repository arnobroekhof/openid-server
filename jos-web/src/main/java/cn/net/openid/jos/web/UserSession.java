/**
 * Created on 2006-10-28 下午09:01:01
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

	private String userId;
	private String username;
	private String identifier;
	private boolean loggedIn;
	private Map<String, CheckIdRequest> approvingRequests;

	public UserSession() {
		this.approvingRequests = new HashMap<String, CheckIdRequest>();
		this.loggedIn = false;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.userId = user.getId();
		this.username = user.getUsername();
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
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

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/* Authentiation Requests */

	public String addRequest(CheckIdRequest request) {
		if (request.getToken() == null) {
			String id = WebUtils.generateToken();
			request.setToken(id);
		}
		if (!this.approvingRequests.containsKey(request.getToken())) {
			this.approvingRequests.put(request.getToken(), request);
		}
		if (log.isDebugEnabled()) {
			log.debug("Add request: " + request.getToken());
		}
		return request.getToken();
	}

	public CheckIdRequest getRequest(String token) {
		if (log.isDebugEnabled()) {
			log.debug("get request: " + token);
		}
		return this.approvingRequests.get(token);
	}

	public CheckIdRequest removeRequest(String token) {
		if (log.isDebugEnabled()) {
			log.debug("Remove request: " + token);
		}
		return this.approvingRequests.remove(token);
	}

	public Collection<CheckIdRequest> getApprovingRequests() {
		return this.approvingRequests.values();
	}
}
