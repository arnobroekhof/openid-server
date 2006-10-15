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

	/**
	 * @return the member
	 */
	public String getMember() {
		return member;
	}

	/**
	 * @param member
	 *            the member to set
	 */
	public void setMember(String member) {
		this.member = member;
	}

}
