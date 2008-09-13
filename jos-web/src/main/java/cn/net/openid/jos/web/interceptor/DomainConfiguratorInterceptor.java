/**
 * Created on 2008-9-13 上午07:14:37
 */
package cn.net.openid.jos.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Domain configurator login check interceptor.
 * 
 * @author Sutra Zhou
 * 
 */
public class DomainConfiguratorInterceptor extends HandlerInterceptorAdapter {
	private String loginPath;

	/**
	 * @param loginPath
	 *            the loginPath to set
	 */
	public void setLoginPath(String loginPath) {
		this.loginPath = loginPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		boolean loggedIn = false;
		HttpSession session = request.getSession(false);
		if (session != null) {
			Object o = session.getAttribute("domainConfiguratorLoggedIn");
			if (o != null) {
				loggedIn = (Boolean) o;
			}
		}
		if (loggedIn == false) {
			response.sendRedirect(response.encodeRedirectURL(request
					.getContextPath()
					+ this.loginPath));
		}
		return loggedIn;
	}
}
