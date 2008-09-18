/**
 * Created on 2008-5-29 上午01:43:28
 */
package cn.net.openid.jos.service.exception;

/**
 * @author Sutra Zhou
 * 
 */
public class EmailConfirmationInfoNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6189975564426887558L;

	/**
	 * 
	 */
	public EmailConfirmationInfoNotFoundException() {
	}

	/**
	 * @param arg0
	 */
	public EmailConfirmationInfoNotFoundException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public EmailConfirmationInfoNotFoundException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public EmailConfirmationInfoNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
