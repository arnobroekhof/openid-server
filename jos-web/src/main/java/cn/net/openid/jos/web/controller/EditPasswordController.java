/**
 * Created on 2006-10-29 上午02:58:32
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Password;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.form.EditPasswordForm;

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

		String passwordId = request.getParameter("password.id");
		if (!StringUtils.isEmpty(passwordId)) {
			editPasswordForm.setPassword(josService.getPassword(
					getUser(request), passwordId));
		}

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
		if (StringUtils.isEmpty(editPasswordForm.getPassword().getName())) {
			errors.rejectValue("password.name", "password.error.nameRequired");
		}
		if (StringUtils.isEmpty(editPasswordForm.getPassword().getPlaintext())) {
			errors.rejectValue("password.plaintext",
					"password.error.plaintextRequired");
		}
		if (!StringUtils.equals(editPasswordForm.getPassword().getPlaintext(),
				editPasswordForm.getRetypedPassword())) {
			errors.rejectValue("retypedPassword", "password.error.notEquals");
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
		Password password = editPasswordForm.getPassword();
		josService.updatePassword(getUser(request), password.getId(), password
				.getName(), password.getPlaintext());
		return super.onSubmit(request, response, command, errors);
	}
}
