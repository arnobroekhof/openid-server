/**
 * Created on 2008-5-20 01:51:02
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.service.exception.PersonaInUseException;
import cn.net.openid.jos.web.AbstractJosController;

/**
 * @author Sutra Zhou
 * 
 */
public class PersonasController extends AbstractJosController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) {
		User user = getUser(request);

		// Delete checked personas.
		String[] deletePersonaIds = request
				.getParameterValues("deletePersonaId");
		if (deletePersonaIds != null) {
			try {
				getJosService().deletePersonas(user, deletePersonaIds);
			} catch (PersonaInUseException e) {
				request.setAttribute("error", "persona.error.inUse");
			}
		}

		return new ModelAndView("personas", "personas", getJosService()
				.getPersonas(user));
	}

}
