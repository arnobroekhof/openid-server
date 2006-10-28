/**
 * Created on 2006-10-6 下午06:29:33
 */
package cn.net.openid;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import cn.net.openid.web.LoginForm;

/**
 * @author Shutra
 * 
 */
public interface Provider {
	/**
	 * 
	 * @param lf
	 * @return If check failed return null.
	 */
	User checkCredential(LoginForm lf);

	/**
	 * @param lf
	 * @return
	 * @deprecated
	 */
	boolean checkPassword(LoginForm lf);

	String sign(Map<String, String> verificationStringValueMap, byte[] macKey);

	boolean checkSignature(Map<String, String> verificationStringValueMap,
			String signature, byte[] macKey);

	byte[] generateMACKey();

	void checkIdSetupResponse(String verifiedIdentifier,
			Map<String, String[]> parameterMap, HttpServletResponse response)
			throws IOException;

	String getUsername(String openid);
}
