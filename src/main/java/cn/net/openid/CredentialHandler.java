/**
 * Created on 2006-10-22 下午07:23:42
 */
package cn.net.openid;

import java.io.Serializable;

import cn.net.openid.web.authentication.AuthenticationHandler;

/**
 * @author Shutra
 * @deprecated Support password only now.
 */
public class CredentialHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3816729341231244669L;

	private AuthenticationHandler authenticationHandler;

	private String id;

	// private String name;

	// private String description;

	private String className;

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	// /**
	// * @return the description
	// */
	// public String getDescription() {
	// return description;
	// }

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	// /**
	// * @return the name
	// */
	// public String getName() {
	// return name;
	// }

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	// /**
	// * @param description
	// * the description to set
	// */
	// public void setDescription(String description) {
	// this.description = description;
	// }

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	// /**
	// * @param name
	// * the name to set
	// */
	// public void setName(String name) {
	// this.name = name;
	// }

	public AuthenticationHandler getAuthenticationHandler() {
		if (authenticationHandler == null) {
			try {
				this.authenticationHandler = (AuthenticationHandler) Class
						.forName(this.className).newInstance();
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		return this.authenticationHandler;
	}

}
