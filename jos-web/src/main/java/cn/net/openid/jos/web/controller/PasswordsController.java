/**
 * Created on 2008-6-5 下午11:42:12
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.service.LastPasswordException;
import cn.net.openid.jos.web.AbstractJosController;

/**
 * @author Sutra Zhou
 * 
 */
public class PasswordsController extends AbstractJosController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = getUser(request).getUserId();

		// Delete checked password.
		String[] deletePasswordIds = request
				.getParameterValues("deletePasswordId");
		if (deletePasswordIds != null) {
			try {
				josService.deletePasswords(userId, deletePasswordIds);
			} catch (LastPasswordException e) {
				// Last password exception.
			}
		}

		return new ModelAndView("passwords", "passwords", josService
				.getPasswords(userId));
	}

}
