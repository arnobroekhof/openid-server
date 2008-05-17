/**
 * Created on 2007-3-26 23:47:30
 */
package cn.net.openid.web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.domain.Attribute;
import cn.net.openid.web.AbstractController;

/**
 * @author Sutra Zhou
 * 
 */
public class AttributesController extends AbstractController {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Collection<Attribute>> model = new HashMap<String, Collection<Attribute>>();
		model.put("attributes", this.daoFacade.getAttributes());
		return new ModelAndView("attributes", model);
	}
}
