/**
 * Created on 2008-5-31 上午05:52:07
 */
package cn.net.openid.jos.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Sutra Zhou
 * 
 */
public class ApprovingRequestFilter implements Filter {
	private static final Log log = LogFactory
			.getLog(ApprovingRequestFilter.class);
	private static final boolean DEBUG = log.isDebugEnabled();

	private static final String TOKEN_ATTRIBUTE_NAME = "token";
	private static final String APPROVING_REQUEST_ATTRIBUTE_NAME = "approvingRequest";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
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
			log.debug("Add attribute `token' to request: " + token);
		}
		chain.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}
}
