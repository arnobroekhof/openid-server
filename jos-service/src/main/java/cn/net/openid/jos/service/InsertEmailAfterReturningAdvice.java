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
 * Created on 2008-5-26 23:51:45
 */
package cn.net.openid.jos.service;

import java.lang.reflect.Method;
import java.util.Date;

import org.springframework.aop.AfterReturningAdvice;

import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.EmailConfirmationInfo;
import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public class InsertEmailAfterReturningAdvice implements AfterReturningAdvice {
	/**
	 * The email confirmation info send task executor.
	 */
	private EmailConfirmationInfoSendTaskExecutor
		emailConfirmationInfoSendTaskExecutor;

	/**
	 * Constructor.
	 * 
	 * @param emailConfirmationInfoSendTaskExecutor
	 *            the emailConfirmationInfoSendTaskExecutor
	 */
	public InsertEmailAfterReturningAdvice(
			final EmailConfirmationInfoSendTaskExecutor
			emailConfirmationInfoSendTaskExecutor) {
		this.emailConfirmationInfoSendTaskExecutor =
			emailConfirmationInfoSendTaskExecutor;
	}

	/**
	 * {@inheritDoc}
	 */
	public void afterReturning(final Object returnValue, final Method method,
			final Object[] args, final Object target) throws Throwable {
		JosService josService = (JosService) target;

		User user = (User) args[0];
		Email email = (Email) args[1];

		String confirmationCode = josService.generateConfirmationCode(email);
		EmailConfirmationInfo emailConfirmationInfo = new EmailConfirmationInfo(
				email, confirmationCode);
		emailConfirmationInfo.setSent(true);
		emailConfirmationInfo.setSentDate(new Date());

		josService.insertEmailConfirmationInfo(user, emailConfirmationInfo);

		this.emailConfirmationInfoSendTaskExecutor
				.sendEmail(emailConfirmationInfo);
	}

}
