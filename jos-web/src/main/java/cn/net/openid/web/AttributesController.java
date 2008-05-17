/**
 * Created on 2007-3-26 23:47:30
 */
package cn.net.openid.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cn.net.openid.dao.DaoFacade;
import cn.net.openid.domain.Attribute;

/**
 * @author sutra
 * 
 */
public class AttributesController implements Controller {
	private DaoFacade daoFacade;

	/**
	 * @param daoFacade
	 *            the daoFacade to set
	 */
	public void setDaoFacade(DaoFacade daoFacade) {
		this.daoFacade = daoFacade;
	}

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
