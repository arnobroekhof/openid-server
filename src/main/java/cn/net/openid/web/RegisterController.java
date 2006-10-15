/**
 * Created on 2006-10-15 下午09:20:29
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
public class RegisterController implements Controller {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String openidUrl = request.getParameter("openidUrl") + ".openid.org.cn";
		return new ModelAndView("register");
	}

}
