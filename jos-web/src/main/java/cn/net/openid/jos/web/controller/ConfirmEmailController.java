/**
 * Created on 2008-5-29 上午01:01:43
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.service.EmailConfirmationInfoNotFoundException;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.form.ConfirmEmailForm;

/**
 * @author Sutra Zhou
 * 
 */
public class ConfirmEmailController extends AbstractJosSimpleFormController {

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
		ConfirmEmailForm command = (ConfirmEmailForm) super
				.formBackingObject(request);
		String code = request.getParameter("code");
		if (!StringUtils.isBlank(code)) {
			command.setConfirmationCode(code);
		}
		return command;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java
	 * .lang.Object, org.springframework.validation.BindException)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		ConfirmEmailForm form = (ConfirmEmailForm) command;
		try {
			this.getJosService().confirmEmail(form.getConfirmationCode());
		} catch (EmailConfirmationInfoNotFoundException e) {
			errors.rejectValue("confirmationCode",
					"email.error.confirmationInfoNotFound",
					"Incorrect E-mail confirmation code.");
		}
		if (errors.hasErrors()) {
			return this.showForm(request, response, errors);
		} else {
			return super.onSubmit(request, response, command, errors);
		}
	}

}
