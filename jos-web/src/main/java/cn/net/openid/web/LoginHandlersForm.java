/**
 * Created on 2006-10-7 上午12:29:03
 */
package cn.net.openid.web;

import java.io.Serializable;
import java.util.List;

import cn.net.openid.CredentialHandler;

/**
 * @author Shutra
 * 
 */
public class LoginHandlersForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5037879147649703902L;

	private String username;

	private CredentialHandler credentialHandler;

	private List<CredentialHandler> credentialHandlers;

	public LoginHandlersForm() {
		this.credentialHandler = new CredentialHandler();
	}

	/**
	 * @return the credentialHandler
	 */
	public CredentialHandler getCredentialHandler() {
		return credentialHandler;
	}

	/**
	 * @return the credentialHandlers
	 */
	public List<CredentialHandler> getCredentialHandlers() {
		return credentialHandlers;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param credentialHandler
	 *            the credentialHandler to set
	 */
	public void setCredentialHandler(CredentialHandler credentialHandler) {
		this.credentialHandler = credentialHandler;
	}

	/**
	 * @param credentialHandlers
	 *            the credentialHandlers to set
	 */
	public void setCredentialHandlers(List<CredentialHandler> credentialHandlers) {
		this.credentialHandlers = credentialHandlers;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
