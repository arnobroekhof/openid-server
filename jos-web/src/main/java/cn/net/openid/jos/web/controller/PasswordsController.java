/**
 * Created on 2008-6-5 下午11:42:12
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.service.exception.LastPasswordException;
import cn.net.openid.jos.web.AbstractJosController;

/**
 * @author Sutra Zhou
 * 
 */
public class PasswordsController extends AbstractJosController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getUser(request);

		// Delete checked password.
		String[] deletePasswordIds = request
				.getParameterValues("deletePasswordId");
		if (deletePasswordIds != null) {
			try {
				getJosService().deletePasswords(user, deletePasswordIds);
			} catch (LastPasswordException e) {
				// Last password exception.
			}
		}

		return new ModelAndView("passwords", "passwords", getJosService()
				.getPasswords(user));
	}

}
