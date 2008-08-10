/**
 * Created on 2008-8-6 上午12:16:34
 */
package cn.net.openid.jos.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.net.openid.jos.domain.Domain;

/**
 * @author Sutra Zhou
 * 
 */
public class DomainFilter extends OncePerRequestServiceFilter {
	public static final String DOMAIN_SESSION_NAME = "domain";

	/**
	 * Get domain from the session.
	 * 
	 * @param request
	 *            the HTTP request
	 * @return the domain in the session, null if session is null or attribute
	 *         is not exists
	 */
	public static Domain getDomain(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return (Domain) request.getSession().getAttribute(
					DOMAIN_SESSION_NAME);
		} else {
			return null;
		}
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
		log.debug("Begin of domain filter.");
		if (getDomain(request) == null) {
			this.parseAndSetDomain(request, response);
		}
		filterChain.doFilter(request, response);
		log.debug("End of domain filter.");
	}

	private void parseAndSetDomain(HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("Parse domain from the request, and put into session.");
		HttpSession session = request.getSession();
		Domain domain = getService().parseDomain(request);
		session.setAttribute(DOMAIN_SESSION_NAME, domain);
	}
}
