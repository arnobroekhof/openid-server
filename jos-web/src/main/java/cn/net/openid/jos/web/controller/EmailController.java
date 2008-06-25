/**
 * Created on 2008-3-10 下午11:04:50
 */
package cn.net.openid.jos.web.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;

/**
 * @author Sutra Zhou
 * 
 */
public class EmailController extends AbstractJosSimpleFormController {
	private LocaleResolver localeResolver;

	/**
	 * @param localeResolver
	 *            the localeResolver to set
	 */
	public void setLocaleResolver(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		User user = getUser(request);

		Collection<Email> emails = josService.getEmails(user);
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
		Email email = (Email) command;
		email.setLocale(localeResolver.resolveLocale(request));
		josService.insertEmail(getUser(request), email);
		return super.onSubmit(request, response, command, errors);
	}

}
