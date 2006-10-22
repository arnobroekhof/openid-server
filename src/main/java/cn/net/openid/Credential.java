/**
 * Created on 2006-10-17 上午12:22:22
 */
package cn.net.openid;

import java.io.Serializable;

/**
 * @author Shutra
 * 
 */
public class Credential implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5713456472659569041L;

	private String id;

	private CredentialHandler handler;

	private User user;

	private byte[] info;

	/**
	 * @return the handler
	 */
	public CredentialHandler getHandler() {
		return handler;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the info
	 */
	public byte[] getInfo() {
		return info;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param handler
	 *            the handler to set
	 */
	public void setHandler(CredentialHandler handler) {
		this.handler = handler;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(byte[] info) {
		this.info = info;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
