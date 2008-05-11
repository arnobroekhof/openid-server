/**
 * Created on 2008-5-12 00:55:46
 */
package cn.net.openid.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author sutra
 * 
 */
public class ApprovingController extends AbstractSimpleFormController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		if (request.getParameter("allow_once") != null) {
			request.getSession().setAttribute("approved", true);
			response.sendRedirect("server?_action=complete");
		} else if (request.getParameter("allow_forever") != null) {
			request.getSession().setAttribute("approved", true);
			response.sendRedirect("server?_action=complete");
		} else {
			request.getSession().setAttribute("approved", false);
			response.sendRedirect("server?_action=complete");
		}
		return null;
	}

}
