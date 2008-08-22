/**
 * Created on 2007-3-26 23:47:40
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import cn.net.openid.jos.domain.Attribute;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;

/**
 * @author Sutra Zhou
 * 
 */
public class EditAttributeController extends AbstractJosSimpleFormController {
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
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			return this.getJosService().getAttribute(id);
		} else {
			return super.formBackingObject(request);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#doSubmitAction
	 * (java.lang.Object)
	 */
	@Override
	protected void doSubmitAction(Object command) throws Exception {
		Attribute attribute = (Attribute) command;
		if (StringUtils.isEmpty(attribute.getId())) {
			attribute.setId(null);
		}
		this.getJosService().saveAttribute(attribute);
	}

}
