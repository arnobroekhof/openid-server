/**
 * 
 */
package cn.net.openid.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.web.AbstractController;

/**
 * @author Sutra Zhou
 * 
 */
public class RemoveAttributeController extends AbstractController {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (id != null) {
			this.daoFacade.deleteAttribute(id);
		}
		return new ModelAndView("remove-attribute-success");
	}
}
