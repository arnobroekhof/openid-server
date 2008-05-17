/**
 * Created on 2008-3-10 下午09:39:35
 */
package cn.net.openid.jos.domain;

import java.io.Serializable;

/**
 * @author Sutra Zhou
 * 
 */
public class UnconfirmedEmail extends Email implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1421672591504003924L;

	private String confirmCode;

	/**
	 * @return confirmCode
	 */
	public String getConfirmCode() {
		return confirmCode;
	}

	/**
	 * @param confirmCode
	 *            要设置的 confirmCode
	 */
	public void setConfirmCode(String confirmCode) {
		this.confirmCode = confirmCode;
	}

}
