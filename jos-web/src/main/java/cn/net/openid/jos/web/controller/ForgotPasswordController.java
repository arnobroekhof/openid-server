/**
 * Created on 2008-9-3 上午05:41:51
 */
package cn.net.openid.jos.web.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.MessageCodes;
import cn.net.openid.jos.web.interceptor.CaptchaInterceptor;

/**
 * @author Sutra Zhou
 * 
 */
public class ForgotPasswordController extends AbstractJosSimpleFormController {

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
		super.onBindAndValidate(request, command, errors);

		Email email = (Email) command;
		if (StringUtils.isEmpty(email.getUser().getUsername())) {
			errors.rejectValue("user.username", "required");
			return;
		}

		User user = this.getJosService().getUser(this.getDomain(request),
				email.getUser().getUsername());
		String errorCode = MessageCodes.Password.Error.USERNAME_OR_EMAIL_ADDRESS_INCORRECT;
		if (user == null) {
			errors.rejectValue("address", errorCode);
		} else {
			Collection<Email> emails = this.getJosService().getEmails(user);
			boolean ok = false;
			for (Email e : emails) {
				if (e.isConfirmed()
						&& e.getAddress().equals(email.getAddress())) {
					ok = true;
					e.setUser(user);
					this.getJosService().generateSingleUsePassword(user, e);
					break;
				}
			}
			if (!ok) {
				errors.rejectValue("address", errorCode);
			}
		}

		if (!errors.hasErrors()) {
			CaptchaInterceptor.setHuman(request, false);
		}
	}
}
