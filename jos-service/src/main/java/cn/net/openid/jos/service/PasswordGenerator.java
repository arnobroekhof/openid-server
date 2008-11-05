/**
 * Created on 2008-9-4 03:08:14
 */
package cn.net.openid.jos.service;

/**
 * @author Sutra Zhou
 * 
 */
public interface PasswordGenerator {
	char[] generate(int minimumLength, int maximumLength);
}
