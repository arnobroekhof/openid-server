/**
 * Created on 2006-10-15 下午09:20:29
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
import cn.net.openid.jos.web.form.RegisterForm;

/**
 * @author Sutra Zhou
 * 
 */
public class RegisterController extends AbstractJosSimpleFormController {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#onBindAndValidate(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		RegisterForm form = (RegisterForm) command;
		String username = form.getUsername();

		if (!errors.hasErrors()) {
			User user = this.josService.getUserByUsername(username);
			if (user != null) {
				errors.rejectValue("username",
						"error.register.usernameAlreadyExists");
			}
		}

		super.onBindAndValidate(request, command, errors);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java.lang.Object,
	 *      org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(Object command, BindException errors)
			throws Exception {
		RegisterForm form = (RegisterForm) command;
		User user = new User();
		user.setUsername(form.getUsername());
		String passwordShaHex = DigestUtils.shaHex(form.getPassword());
		Password password = new Password(user);
		String defaultPasswordMessage = this.getMessageSourceAccessor()
				.getMessage("password.title.defaultPassword");
		password.setName(defaultPasswordMessage);
		password.setPlaintext(form.getPassword());
		password.setShaHex(passwordShaHex);
		this.josService.insertUser(user, password);
		return super.onSubmit(command, errors);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	protected Map<Object, Object> referenceData(HttpServletRequest request,
			Object command, Errors errors) throws Exception {
		RegisterForm form = (RegisterForm) command;
		form.setUsername(request.getParameter("username"));
		return null;
	}
}
