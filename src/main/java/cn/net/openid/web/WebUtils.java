/**
 * Created on 2006-10-22 下午07:12:00
 */
package cn.net.openid.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openid4java.server.ServerManager;

/**
 * The utilities for web.
 * 
 * @author Sutra Zhou
 * 
 */
public class WebUtils {
	private static final String USER_SESSION = "userSession";

	/**
	 * 
	 * @param req
	 * @return
	 * @deprecated use HttpServletRequest.getContextPath() instead.
	 */
	public static String getContextPath(HttpServletRequest req) {
		String ret = req.getContextPath();
		if (ret.equals("/")) {
			return "";
		} else {
			return ret;
		}
	}

	/**
	 * Store the logged in user info to HTTP session.
	 * 
	 * @param request
	 *            HTTP request
	 * @param userSession
	 *            the logged in user info
	 */
	public static void setUserSession(HttpServletRequest request,
			UserSession userSession) {
		request.getSession().setAttribute(USER_SESSION, userSession);
	}

	/**
	 * Retrieve user info from HTTP session.
	 * 
	 * @param request
	 *            HTTP request
	 * @return the logged in user info. if not logged in, return null.
	 */
	public static UserSession getUserSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		UserSession userSession;
		if (session != null) {
			userSession = (UserSession) session.getAttribute(USER_SESSION);
		} else {
			userSession = null;
		}
		return userSession;
	}
}
