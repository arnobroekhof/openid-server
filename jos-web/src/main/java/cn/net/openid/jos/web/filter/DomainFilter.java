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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.service.exception.UnresolvedDomainException;

/**
 * @author Sutra Zhou
 * 
 */
public class DomainFilter extends OncePerRequestServiceFilter {
	private static final Log log = LogFactory.getLog(DomainFilter.class);
	private static final String DOMAIN_ATTRIBUTE_NAME = "domain";

	/**
	 * Get domain from the session/request.
	 * 
	 * @param request
	 *            the HTTP request
	 * @return the domain in the session or request, null if session is null or
	 *         attribute is not exists in session and request.
	 */
	public static Domain getDomain(HttpServletRequest request) {
		Domain domain = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			domain = (Domain) request.getSession().getAttribute(
					DOMAIN_ATTRIBUTE_NAME);
		}
		if (domain == null) {
			domain = (Domain) request.getAttribute(DOMAIN_ATTRIBUTE_NAME);
		}
		if (log.isDebugEnabled()) {
			if (domain != null) {
				log.debug(domain + ": " + domain.getUsernameConfiguration());
			}
		}
		return domain;
	}

	public static boolean skip(HttpServletRequest request) {
		String uri = request.getRequestURI();
		if (uri.matches("(robots\\.txt)|(.*\\.(css|ico|png|gif))")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDomainConfigurator(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		return uri.startsWith(ctx + "/domain-configurator")
				|| uri.startsWith(ctx + "/hl");
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
			try {
				this.parseDomain(request);
			} catch (UnresolvedDomainException e) {
				if (!isDomainConfigurator(request)) {
					throw e;
				}
			}
		}
		filterChain.doFilter(request, response);
		log.debug("End of domain filter.");
	}

	private void parseDomain(HttpServletRequest request) {
		if (getDomain(request) == null) {
			Domain domain = getService().parseDomain(request);
			this.setDomain(request, domain);
		}
	}

	private void setDomain(HttpServletRequest request, Domain domain) {
		log.debug("Put domain info into session.");
		HttpSession session = request.getSession(true);
		session.setAttribute(DOMAIN_ATTRIBUTE_NAME, domain);

		log.debug("Put domain info into request.");
		request.setAttribute(DOMAIN_ATTRIBUTE_NAME, domain);
		if (log.isDebugEnabled()) {
			log.debug(domain + "" + domain.getUsernameConfiguration());
		}
	}
}
