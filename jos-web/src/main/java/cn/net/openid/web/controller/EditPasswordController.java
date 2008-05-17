/**
 * Created on 2006-10-29 上午02:58:32
 */
package cn.net.openid.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.web.AbstractJosSimpleFormController;
import cn.net.openid.web.form.EditPasswordForm;

/**
 * @author Sutra Zhou
 * 
 */
public class EditPasswordController extends AbstractJosSimpleFormController {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		EditPasswordForm editPasswordForm = (EditPasswordForm) super
				.formBackingObject(request);
		editPasswordForm.setCredentialId(request.getParameter("id"));
		return editPasswordForm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#onBindAndValidate(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		super.onBindAndValidate(request, command, errors);
		EditPasswordForm editPasswordForm = (EditPasswordForm) command;
		if (StringUtils.isEmpty(editPasswordForm.getPassword())) {
			errors.rejectValue("password", "error.password.empty");
		}
		if (!StringUtils.equals(editPasswordForm.getPassword(),
				editPasswordForm.getRetypedPassword())) {
			errors.rejectValue("retypedPassword", "error.password.notEquals");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		EditPasswordForm editPasswordForm = (EditPasswordForm) command;
		return super.onSubmit(request, response, command, errors);
	}
}
