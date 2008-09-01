/**
 * Created on 2008-9-2 上午06:20:56
 */
package cn.net.openid.jos.web.form;

import java.io.Serializable;

/**
 * @author Sutra Zhou
 * 
 */
public class CaptchaForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7661908793403102686L;

	private String captcha;

	/**
	 * @return the captcha
	 */
	public String getCaptcha() {
		return captcha;
	}

	/**
	 * @param captcha
	 *            the captcha to set
	 */
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

}
