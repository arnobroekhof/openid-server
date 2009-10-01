/**
 * Copyright (c) 2006-2009, Redv.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Redv.com nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * Created on 2008-3-10 23:04:50
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
 */
public class EmailController extends AbstractJosSimpleFormController {
	/**
	 * The locale resolver.
	 */
	private LocaleResolver localeResolver;

	/**
	 * Constructs an email controller.
	 * 
	 * @param localeResolver
	 *            the locale resolver
	 */
	public EmailController(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {
		User user = getUser(request);

		Collection<Email> emails = getJosService().getEmails(user);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {
		Email email = (Email) command;
		email.setLocale(localeResolver.resolveLocale(request));
		getJosService().insertEmail(getUser(request), email);
		return super.onSubmit(request, response, command, errors);
	}
}
