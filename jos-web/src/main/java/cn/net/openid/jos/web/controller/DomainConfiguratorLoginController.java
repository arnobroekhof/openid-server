/**
 * Created on 2008-9-12 下午11:39:01
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.web.AbstractJosSimpleFormController;

/**
 * Domain configurator login controller.
 * 
 * @author Sutra Zhou
 * 
 */
public class DomainConfiguratorLoginController extends
		AbstractJosSimpleFormController {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject
	 * (javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		return new Object();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String input = request.getParameter("password");
		ModelAndView ret = null;
		if (this.getJosService().checkConfiguratorPassword(input)) {
			log.debug("Password check OK.");
			request.getSession().setAttribute("domainConfiguratorLoggedIn",
					true);
			response.sendRedirect(response.encodeRedirectURL(request
					.getContextPath()
					+ "/domain-configurator"));
		} else {
			log.debug("Password check failed.");
			ret = new ModelAndView(this.getFormView());
		}
		return ret;
	}

}
