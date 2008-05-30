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
		if (token != null) {
			if (DEBUG) {
				log.debug("Add attribute `token' to request: " + token);
			}
			request.setAttribute(TOKEN_ATTRIBUTE_NAME, token);
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
