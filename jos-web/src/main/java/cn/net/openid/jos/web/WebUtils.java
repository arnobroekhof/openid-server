/**
 * Created on 2006-10-22 下午07:12:00
 */
package cn.net.openid.jos.web;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;

/**
 * The utilities for web.
 * 
 * @author Sutra Zhou
 * 
 */
public class WebUtils {
	private static final String USER_SESSION = "userSession";

	public static String generateToken() {
		return RandomStringUtils.randomAlphanumeric(32);
	}

	/**
	 * Retrieve user session from http session.
	 * 
	 * @param session
	 * @return the user session, if not found in http session, create a new one.
	 */
	public static UserSession getOrCreateUserSession(HttpSession session) {
		return (UserSession) org.springframework.web.util.WebUtils
				.getOrCreateSessionAttribute(session, USER_SESSION,
						UserSession.class);
	}

	/**
	 * Writer string to HTTP output stream.
	 * 
	 * @param httpResp
	 *            the HTTP response
	 * @param response
	 *            the text which to write, if null, do nothing.
	 * @throws IOException
	 */
	public static void writeResponse(HttpServletResponse httpResp,
			String response) throws IOException {
		if (response == null) {
			return;
		}

		ServletOutputStream os = httpResp.getOutputStream();
		try {
			os.write(response.getBytes());
		} finally {
			os.close();
		}
	}
}
