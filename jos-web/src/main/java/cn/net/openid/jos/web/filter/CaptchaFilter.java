/**
 * Created on 2008-9-2 上午04:49:36
 */
package cn.net.openid.jos.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

/**
 * @author Sutra Zhou
 * 
 */
public class CaptchaFilter extends OncePerRequestServiceFilter {
	private static final String HUMAN_SESSION_NAME = CaptchaFilter.class
			.getName()
			+ "IS_HUMAN";
	private static final String FROM_SESSION_NAME = CaptchaFilter.class
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
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (isHuman(request)) {
			filterChain.doFilter(request, response);
		} else {
			setFrom(request, request.getRequestURI());
			this.getServletContext().getRequestDispatcher("/captcha").forward(
					request, response);
		}
	}

}
