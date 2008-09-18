/**
 * Created on 2006-10-29 下午06:45:15
 */
package cn.net.openid.jos.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.web.UserSession;
import cn.net.openid.jos.web.WebUtils;

/**
 * @author Sutra Zhou
 * 
 */
public class UserLoggedInInterceptor implements HandlerInterceptor {
	private String loginPath = "login";

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
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      java.lang.Exception)
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      org.springframework.web.servlet.ModelAndView)
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		boolean ret = false;
		HttpSession session = request.getSession(false);
		if (session != null) {
			UserSession userSession = WebUtils.getOrCreateUserSession(request
					.getSession());
			if (userSession.isLoggedIn()) {
				ret = true;
			}
		}
		if (ret == false) {
			response.sendRedirect(response.encodeRedirectURL(request
					.getContextPath()
					+ this.loginPath));
		}
		return ret;
	}

}
