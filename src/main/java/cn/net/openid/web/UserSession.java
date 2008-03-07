/**
 * Created on 2006-10-28 下午09:01:01
 */
package cn.net.openid.web;

import java.io.Serializable;

import cn.net.openid.domain.User;

/**
 * The user session, stored the data of user who are logging or logged in.
 * 
 * @author Shutra
 * 
 */
public class UserSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8227402637586478669L;

	private String userId;

	private String username;

	private String openIdUrl;

	private boolean loggedIn;

	public UserSession() {

	}

	public UserSession(User user) {
		this.userId = user.getId();
		this.username = user.getUsername();
	}

	/**
	 * @return the openIdUrl
	 */
	public String getOpenIdUrl() {
		return openIdUrl;
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
	 * @param openIdUrl
	 *            the openIdUrl to set
	 */
	public void setOpenIdUrl(String openidUrl) {
		this.openIdUrl = openidUrl;
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

}
