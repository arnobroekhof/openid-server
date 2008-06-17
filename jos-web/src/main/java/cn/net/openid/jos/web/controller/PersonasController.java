/**
 * Created on 2008-5-20 上午01:51:02
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
public class PersonasController extends AbstractJosController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = getUser(request).getUserId();

		// Delete checked personas.
		String[] deletePersonaIds = request
				.getParameterValues("deletePersonaId");
		if (deletePersonaIds != null) {
			try {
				josService.deletePersonas(userId, deletePersonaIds);
			} catch (LastPasswordException e) {
				// Last password exception.
			}
		}

		return new ModelAndView("personas", "personas", josService
				.getPersonas(userId));
	}

}
