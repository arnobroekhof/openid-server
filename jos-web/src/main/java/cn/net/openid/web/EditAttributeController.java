/**
 * Created on 2007-3-26 23:47:40
 */
package cn.net.openid.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cn.net.openid.dao.DaoFacade;
import cn.net.openid.domain.Attribute;

/**
 * @author sutra
 * 
 */
public class EditAttributeController extends SimpleFormController {
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
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			return this.daoFacade.getAttribute(id);
		} else {
			return super.formBackingObject(request);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#doSubmitAction(java.lang.Object)
	 */
	@Override
	protected void doSubmitAction(Object command) throws Exception {
		Attribute attribute = (Attribute) command;
		if (StringUtils.isEmpty(attribute.getId())) {
			attribute.setId(null);
		}
		this.daoFacade.saveAttribute(attribute);
	}

}
