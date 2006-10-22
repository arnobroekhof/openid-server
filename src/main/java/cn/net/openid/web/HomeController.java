/**
 * Created on 2006-10-18 下午10:30:43
 */
package cn.net.openid.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @author Shutra
 * 
 */
public class HomeController implements Controller {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
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
			response.sendRedirect(response.encodeRedirectURL(WebUtils
					.getContextPath(request)
					+ redirectPath));
		}
		return new ModelAndView("home");
	}
}
