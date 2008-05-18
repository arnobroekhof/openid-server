/**
 * Created on 2008-05-18 13:36:49
 */
package cn.net.openid.jos.web.form;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sutra Zhou
 * 
 */
public class ApprovingForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2752745018691700211L;

	private String token;
	private List<String> required;
	private List<String> optional;

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the required
	 */
	public List<String> getRequired() {
		return required;
	}

	/**
	 * @param required
	 *            the required to set
	 */
	public void setRequired(List<String> required) {
		this.required = required;
	}

	/**
	 * @return the optional
	 */
	public List<String> getOptional() {
		return optional;
	}

	/**
	 * @param optional
	 *            the optional to set
	 */
	public void setOptional(List<String> optional) {
		this.optional = optional;
	}
}
