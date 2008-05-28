/**
 * Created on 2008-5-29 上午01:03:31
 */
package cn.net.openid.jos.web.form;

import java.io.Serializable;

/**
 * @author Sutra Zhou
 * 
 */
public class ConfirmEmailForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7981706918543691401L;

	private String confirmationCode;

	/**
	 * @return the confirmationCode
	 */
	public String getConfirmationCode() {
		return confirmationCode;
	}

	/**
	 * @param confirmationCode
	 *            the confirmationCode to set
	 */
	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

}
