/**
 * Created on 2006-10-6 下午06:29:33
 */
package cn.net.openid;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import cn.net.openid.web.login.PasswordLoginForm;

/**
 * @author Shutra
 * 
 */
public interface Provider {
	boolean checkCredential(PasswordLoginForm lf);

	/**
	 * @param lf
	 * @return
	 * @deprecated
	 */
	boolean checkPassword(PasswordLoginForm lf);

	String sign(Map<String, String> verificationStringValueMap, byte[] macKey);

	boolean checkSignature(Map<String, String> verificationStringValueMap,
			String signature, byte[] macKey);

	byte[] generateMACKey();

	void checkIdSetupResponse(String verifiedIdentifier,
			Map<String, String[]> parameterMap, HttpServletResponse response)
			throws IOException;

	String getUsername(String openid);
}
