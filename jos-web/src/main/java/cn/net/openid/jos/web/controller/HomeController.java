/**
 * Created on 2006-10-18 22:30:43
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.web.AbstractJosController;
import cn.net.openid.jos.web.UserSession;

/**
 * @author Sutra Zhou
 * 
 */
public class HomeController extends AbstractJosController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		if (username != null) {
			String redirectPath;
			if (request.getParameter("login") != null) {
				redirectPath = "/login?username=" + username;
			} else if (request.getParameter("index.login") != null) {
				redirectPath = "/index.login?username=" + username;
			} else {
				redirectPath = ("/register?username=" + username);
			}
			response.sendRedirect(response.encodeRedirectURL(request
					.getContextPath()
					+ redirectPath));
		}
		ModelAndView modelAndView = new ModelAndView("home");
		UserSession userSession = this.getUserSession(request);
		if (userSession.isLoggedIn()) {
			modelAndView.addObject("topSites", this.getJosService()
					.getTopSites(this.getUser(request), 10));
			modelAndView.addObject("latestSites", this.getJosService()
					.getLatestSites(this.getUser(request), 10));
			modelAndView.addObject("latestRealms", this.getJosService()
					.getLatestRealms(10));
		}
		return modelAndView;
	}
}
