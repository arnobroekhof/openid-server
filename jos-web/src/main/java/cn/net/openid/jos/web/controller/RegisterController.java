/**
 * Created on 2006-10-15 21:20:29
 */
package cn.net.openid.jos.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Password;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.MessageCodes;
import cn.net.openid.jos.web.form.RegisterForm;
import cn.net.openid.jos.web.interceptor.CaptchaInterceptor;

/**
 * @author Sutra Zhou
 * 
 */
public class RegisterController extends AbstractJosSimpleFormController {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#referenceData
	 * (javax.servlet.http.HttpServletRequest, java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	protected Map<Object, Object> referenceData(HttpServletRequest request,
			Object command, Errors errors) throws Exception {
		RegisterForm form = (RegisterForm) command;

		// Set current domain to the user.
		form.getUser().setDomain(this.getDomain(request));

		form.getUser().setUsername(request.getParameter("username"));
		return null;
	}

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
		RegisterForm form = (RegisterForm) super.formBackingObject(request);

		// Set current domain to the user.
		form.getUser().setDomain(this.getDomain(request));

		return form;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.BaseCommandController#onBindAndValidate
	 * (javax.servlet.http.HttpServletRequest, java.lang.Object,
	 * org.springframework.validation.BindException)
	 */
	@Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		RegisterForm form = (RegisterForm) command;

		String username = form.getUser().getUsername();

		if (!errors.hasErrors()) {
			User user = this.getJosService().getUser(getDomain(request),
					username);
			if (user != null) {
				errors.rejectValue("user.username",
						MessageCodes.User.Error.REGISTER_USER_ALREADY_EXISTS);
			}
		}

		super.onBindAndValidate(request, command, errors);

		// Register success, remove the human flag.
		if (!errors.hasErrors()) {
			CaptchaInterceptor.setHuman(request, false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java
	 * .lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(Object command, BindException errors)
			throws Exception {
		RegisterForm form = (RegisterForm) command;
		User user = form.getUser();
		String passwordShaHex = DigestUtils.shaHex(form.getPassword());
		Password password = new Password(user);
		String defaultPasswordMessage = this.getMessageSourceAccessor()
				.getMessage(MessageCodes.Password.Title.DEFAULT_PASSWORD);
		password.setName(defaultPasswordMessage);
		password.setPlaintext(form.getPassword());
		password.setShaHex(passwordShaHex);
		this.getJosService().insertUser(user, password);
		return super.onSubmit(command, errors);
	}

}
