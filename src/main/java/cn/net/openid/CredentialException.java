/**
 * Created on 2006-10-29 下午04:47:31
 */
package cn.net.openid;

/**
 * @author Shutra
 * @deprecated Support password only now.
 */
public class CredentialException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4710332939217786550L;

	/**
	 * 
	 */
	public CredentialException() {
	}

	/**
	 * @param message
	 */
	public CredentialException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public CredentialException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CredentialException(String message, Throwable cause) {
		super(message, cause);
	}

}
