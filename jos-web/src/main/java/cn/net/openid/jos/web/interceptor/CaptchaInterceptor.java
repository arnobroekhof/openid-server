/**
 * Created on 2008-9-2 04:49:36
 */
package cn.net.openid.jos.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.web.filter.DomainFilter;

/**
 * @author Sutra Zhou
 * 
 */
public class CaptchaInterceptor extends HandlerInterceptorAdapter {
	private static final String HUMAN_SESSION_NAME = CaptchaInterceptor.class
			.getName()
			+ "IS_HUMAN";
	private static final String FROM_SESSION_NAME = CaptchaInterceptor.class
			.getName()
			+ "FROM";

	public static boolean isHuman(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return session != null
				&& session.getAttribute(HUMAN_SESSION_NAME) != null;
	}

	public static void setHuman(HttpServletRequest request, boolean value) {
		if (value) {
			request.getSession().setAttribute(HUMAN_SESSION_NAME, value);
		} else {
			request.getSession().removeAttribute(HUMAN_SESSION_NAME);
		}
	}

	/**
	 * Get the captcha previous page, if null return contextPath.
	 * 
	 * @param request
	 * @return
	 */
	public static String getFrom(HttpServletRequest request) {
		String ret = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			ret = (String) session.getAttribute(FROM_SESSION_NAME);
			session.removeAttribute(FROM_SESSION_NAME);
		}
		return StringUtils.defaultString(ret, request.getContextPath());
	}

	private static void setFrom(HttpServletRequest request, String value) {
		request.getSession().setAttribute(FROM_SESSION_NAME, value);
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
		boolean ret;

		Domain domain = DomainFilter.getDomain(request);
		boolean captcha = domain.getBooleanAttribute("captcha");
		if (!captcha || isHuman(request)) {
			ret = true;
		} else {
			setFrom(request, request.getRequestURI());
			response.sendRedirect(response.encodeRedirectURL(request
					.getContextPath()
					+ "/captcha"));
			ret = false;
		}

		return ret;
	}
}
