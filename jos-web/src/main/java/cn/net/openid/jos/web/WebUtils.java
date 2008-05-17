/**
 * Created on 2006-10-22 下午07:12:00
 */
package cn.net.openid.jos.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.net.openid.jos.service.JosService;

/**
 * The utilities for web.
 * 
 * @author Sutra Zhou
 * 
 */
public class WebUtils {
	private static final String USER_SESSION = "userSession";
	private static final String JOS_SERVICE_BEAN_NAME_CONTEXT_PARAM_NAME = "josServiceBeanName";

	public static JosService getJosService(ServletContext servletContext) {
		String beanName = servletContext
				.getInitParameter(JOS_SERVICE_BEAN_NAME_CONTEXT_PARAM_NAME);
		return (JosService) WebApplicationContextUtils
				.getWebApplicationContext(servletContext).getBean(beanName);
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
