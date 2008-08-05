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

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.service.JosService;

/**
 * @author Sutra Zhou
 * 
 */
public class DomainFilter extends OncePerRequestFilter {
	private JosService josService;

	private void parseAndSetDomain(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserSession userSession = WebUtils.getOrCreateUserSession(session);
		if (userSession.getUser() == null) {
			User user = josService.parseUser(request);
			userSession.setUser(user);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.filter.GenericFilterBean#initFilterBean()
	 */
	@Override
	protected void initFilterBean() throws ServletException {
		super.initFilterBean();
		this.josService = (JosService) WebApplicationContextUtils
				.getWebApplicationContext(this.getServletContext()).getBean(
						this.getFilterConfig().getInitParameter(
								"josServiceBeanName"));

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
		this.parseAndSetDomain(request, response);
		filterChain.doFilter(request, response);
	}
}
