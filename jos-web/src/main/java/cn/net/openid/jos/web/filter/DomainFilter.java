/**
 * Created on 2008-8-6 上午12:16:34
 */
package cn.net.openid.jos.web.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.net.openid.jos.domain.Domain;

/**
 * Domain filter. Parse domain information from request URL, and put into
 * session and request.
 * 
 * @author Sutra Zhou
 * 
 */
public class DomainFilter extends OncePerRequestServiceFilter {
	private static final Log log = LogFactory.getLog(DomainFilter.class);
	private static final String DOMAIN_ATTRIBUTE_NAME = "domain";

	private Pattern skipPattern;

	/**
	 * @param skipPattern
	 *            the skipPattern to set
	 */
	public void setSkipPattern(String skipPattern) {
		log.debug("skipPattern setted: " + skipPattern.trim());
		this.skipPattern = Pattern.compile(skipPattern.trim());
	}

	/**
	 * Get domain from <code>request.getSession(false)</code>.
	 * 
	 * @param request
	 *            the HTTP request
	 * @return the domain in the session, null if session is null or not found.
	 * @see DomainFilter#getDomain(HttpSession)
	 */
	public static Domain getDomain(HttpServletRequest request) {
		return getDomain(request.getSession(false));
	}

	/**
	 * Get domain from the session/request.
	 * 
	 * @param session
	 *            the HTTP session
	 * @return the domain in the session, null if session is null or not found.
	 */
	public static Domain getDomain(HttpSession session) {
		return session != null ? (Domain) session
				.getAttribute(DOMAIN_ATTRIBUTE_NAME) : null;
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
		if (!skip(request)) {
			this.parseDomain(request);

			// Put domain to request.
			this.setDomain(request, getDomain(request.getSession(false)));
		} else if (log.isDebugEnabled()) {
			log.debug("Skipped domain parsing.");
		}
		filterChain.doFilter(request, response);
		log.debug("End of domain filter.");
	}

	private boolean skip(HttpServletRequest request) {
		String path = request.getRequestURI().substring(
				request.getContextPath().length());
		if (this.skipPattern.matcher(path).matches()) {
			return true;
		} else {
			return false;
		}
	}

	private void parseDomain(HttpServletRequest request) {
		if (getDomain(request.getSession(false)) == null) {
			Domain domain = getService().parseDomain(request);
			this.setDomain(request.getSession(), domain);
		}
	}

	private void setDomain(HttpSession session, Domain domain) {
		log.debug("Put domain info into session.");
		session.setAttribute(DOMAIN_ATTRIBUTE_NAME, domain);
	}

	private void setDomain(HttpServletRequest request, Domain domain) {
		log.debug("Put domain info into request.");
		request.setAttribute(DOMAIN_ATTRIBUTE_NAME, domain);
	}
}
