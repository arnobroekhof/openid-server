/**
 * Created on 2008-5-31 05:52:07
 */
package cn.net.openid.jos.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.net.openid.jos.web.UserSession;
import cn.net.openid.jos.web.WebUtils;

/**
 * @author Sutra Zhou
 * 
 */
public class ApprovingRequestFilter extends OncePerRequestFilter {
	private static final String TOKEN_ATTRIBUTE_NAME = "token";
	private static final String APPROVING_REQUEST_ATTRIBUTE_NAME = "approvingRequest";

	private final boolean DEBUG = logger.isDebugEnabled();

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
		String token = request.getParameter("token");
		request.setAttribute(TOKEN_ATTRIBUTE_NAME, StringUtils.defaultString(
				token, StringUtils.EMPTY));
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpReq = (HttpServletRequest) request;
			HttpSession session = httpReq.getSession();
			UserSession userSession = WebUtils.getOrCreateUserSession(session);
			request.setAttribute(APPROVING_REQUEST_ATTRIBUTE_NAME, userSession
					.getApprovingRequest(token));
		}
		if (DEBUG) {
			logger.debug("Add attribute `token' to request: " + token);
		}
		filterChain.doFilter(request, response);
	}
}
