/**
 * Created on 2006-10-18 下午11:49:06
 */
package cn.net.openid.jos.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.net.openid.jos.domain.Domain;

/**
 * @author Sutra Zhou
 * 
 */
public class MemberFilter extends OncePerRequestServiceFilter {
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
		log.debug("Begin of member filter.");
		Domain domain = null;
		domain = DomainFilter.getDomain(request);
		if (domain == null) {
			log.debug("Parse domain from the request.");
			domain = getService().parseDomain(request);
		}

		log.debug("Put the domain into the request.");
		request.setAttribute(DomainFilter.DOMAIN_SESSION_NAME, domain);
		log.debug("Parse username from the request.");
		String username = getService().parseUsername(domain, request);

		if (log.isDebugEnabled()) {
			log.debug(String.format("%1$s@%2$s", username, domain));
		}
		if (username == null) {
			log.debug("The url is not matches.");
			filterChain.doFilter(request, response);
		} else {
			this.dispatch(request, response, username);
		}
		log.debug("End of member filter.");
	}

	private void dispatch(ServletRequest request, ServletResponse response,
			String username) throws ServletException, IOException {
		String path = "/member/" + username;// ->/member/$1
		log.debug("path: " + path);
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
				path);
		rd.forward(request, response);
	}
}
