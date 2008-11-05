/**
 * Created on 2008-9-2 06:20:56
 */
package cn.net.openid.jos.web.form;

import java.io.Serializable;
import java.util.Locale;

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
	private Locale locale;

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

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
