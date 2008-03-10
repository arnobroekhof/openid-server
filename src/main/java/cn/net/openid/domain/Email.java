/**
 * Created on 2008-3-10 下午09:31:39
 */
package cn.net.openid.domain;

import java.io.Serializable;

/**
 * @author sutra
 * 
 */
public class Email implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6706127085088356027L;

	private String id;

	private User user;

	private String address;

	/**
	 * @return user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            要设置的 user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            要设置的 address
	 */
	public void setAddress(String emailAddress) {
		this.address = emailAddress;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            要设置的 id
	 */
	public void setId(String id) {
		this.id = id;
	}

}
