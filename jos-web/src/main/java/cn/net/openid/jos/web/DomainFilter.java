/**
 * Created on 2008-8-6 上午12:16:34
 */
package cn.net.openid.jos.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.web.filter.OncePerRequestServiceFilter;

/**
 * @author Sutra Zhou
 * 
 */
public class DomainFilter extends OncePerRequestServiceFilter {
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
		this.parseAndSetDomain(request, response);
		filterChain.doFilter(request, response);
	}

	private void parseAndSetDomain(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserSession userSession = WebUtils.getOrCreateUserSession(session);

		if (userSession.getUser().getDomain().getName() == null) {
			Domain domain = getService().parseDomain(request);
			userSession.getUser().setDomain(domain);
		}
	}
}
