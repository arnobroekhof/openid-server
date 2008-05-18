/**
 * Created on 2008-3-10 下午11:04:50
 */
package cn.net.openid.jos.web.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.UserSession;
import cn.net.openid.jos.web.WebUtils;

/**
 * @author Sutra Zhou
 * 
 */
public class EmailController extends AbstractJosSimpleFormController {
	/*
	 * （非 Javadoc）
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		UserSession userSession = WebUtils.getOrCreateUserSession(request
				.getSession());
		String userId = userSession.getUserId();
		User user = this.josService.getUser(userId);

		Collection<Email> emails = this.josService.getEmailsByUserId(userId);
		Collection<Email> confirmedEmails = new ArrayList<Email>(emails.size());
		Collection<Email> unconfirmedEmails = new ArrayList<Email>(emails
				.size());
		for (Email email : emails) {
			if (email.isConfirmed()) {
				confirmedEmails.add(email);
			} else {
				unconfirmedEmails.add(email);
			}
		}
		request.setAttribute("confirmedEmails", confirmedEmails);
		request.setAttribute("unconfirmedEmails", unconfirmedEmails);

		Email email = (Email) super.formBackingObject(request);
		email.setUser(user);
		return email;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java.lang.Object)
	 */
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		Email email = (Email) command;
		this.josService.insertEmail(email);
		return super.onSubmit(command);
	}

}
