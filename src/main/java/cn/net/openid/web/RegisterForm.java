/**
 * Created on 2006-10-16 上午12:43:06
 */
package cn.net.openid.web;

import java.io.Serializable;

/**
 * @author Shutra
 * 
 */
public class RegisterForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8100263157196112732L;

	private String member;

	private String password;

	/**
	 * @return the member
	 */
	public String getMember() {
		return member;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param member
	 *            the member to set
	 */
	public void setMember(String member) {
		this.member = member;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
