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
		Object member = request.getParameter("username");
		if (member != null) {
			if (request.getParameter("login") != null) {
				response.sendRedirect("login?username=" + member);
			} else {
				response.sendRedirect("register?username=" + member);
			}
		}
		return new ModelAndView("home");
	}

}
