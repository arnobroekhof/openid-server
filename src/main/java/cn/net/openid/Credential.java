/**
 * Created on 2006-10-17 上午12:22:22
 */
package cn.net.openid;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.net.openid.domain.User;
import cn.net.openid.web.authentication.AuthenticationHandler;

/**
 * @author Shutra
 * @deprecated Support password only now.
 */
public class Credential implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5713456472659569041L;

	private static final Log log = LogFactory.getLog(Credential.class);

	private AuthenticationHandler authenticationHandler;

	private String id;

	private CredentialHandler handler;

	private User user;

	private byte[] info;

	public AuthenticationHandler getAuthenticationHandler() {
		if (authenticationHandler == null) {
			try {
				authenticationHandler = (AuthenticationHandler) Class.forName(
						this.handler.getClassName()).newInstance();
			} catch (InstantiationException e) {
				log.error(e);
			} catch (IllegalAccessException e) {
				log.error(e);
			} catch (ClassNotFoundException e) {
				log.error(e);
			}
		}
		return authenticationHandler;
	}

	public String getDescription() {
		return this.getAuthenticationHandler().describe(this);
	}

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
