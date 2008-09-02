/**
 * Created on 2008-8-11 上午12:54:32
 */
package cn.net.openid.jos.service.exception;

/**
 * Unchecked exception thrown when an attempt is made to parse a domain
 * operation upon an unresolved domain.
 * 
 * @author Sutra Zhou
 * 
 */
public class UnresolvedDomainException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8159796608529077134L;

	/**
	 * @param message
	 */
	public UnresolvedDomainException(String message) {
		super(message);
	}

}
