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
 * Created on 2008-9-5 01:58:57
 */
package cn.net.openid.jos.service;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.Password;

/**
 * Send one-time password to email.
 * 
 * @author Sutra Zhou
 */
public class PasswordSendTaskExecutor {
	/**
	 * The logger.
	 */
	private static final Log LOG = LogFactory
			.getLog(PasswordSendTaskExecutor.class);

	/**
	 * The task executor.
	 */
	private TaskExecutor taskExecutor;

	/**
	 * The mail sender.
	 */
	private MailSender mailSender;

	/**
	 * Constructor a new password send task executor with the task executor.
	 * 
	 * @param taskExecutor
	 *            the task executor
	 */
	public PasswordSendTaskExecutor(final TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	/**
	 * Set the mail sender.
	 * 
	 * @param mailSender
	 *            the mailSender to set
	 */
	public void setMailSender(final MailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * Send the password to the email.
	 * 
	 * @param email
	 *            the email to send to
	 * @param password
	 *            the password to send
	 */
	public void sendPassword(final Email email, final Password password) {
		taskExecutor.execute(new PasswordSendTask(email, password));
	}

	/**
	 * The password send task.
	 * 
	 * @author Sutra Zhou
	 */
	private class PasswordSendTask implements Runnable {
		/**
		 * The email to send to.
		 */
		private Email email;

		/**
		 * The password to send.
		 */
		private Password password;

		/**
		 * Constructor.
		 * 
		 * @param email
		 *            the email
		 * @param password
		 *            the password
		 */
		public PasswordSendTask(final Email email, final Password password) {
			this.email = email;
			this.password = password;
		}

		/**
		 * {@inheritDoc}
		 */
		public void run() {
			Locale currentLocale = email.getLocale();
			LOG.debug("Current locale: " + currentLocale);
			LOG.debug("Sending mail to: " + this.email.getAddress());
			ResourceBundle emailConfirmationResources = ResourceBundle
					.getBundle("one-time-password", currentLocale);

			SimpleMailMessage simpleMessage = new SimpleMailMessage();
			simpleMessage.setTo(this.email.getAddress());
			simpleMessage.setSubject(emailConfirmationResources
					.getString("subject"));
			String text = emailConfirmationResources.getString("text");

			text = StringUtils.replace(text, "${identifier}", email.getUser()
					.getIdentifier());
			text = StringUtils.replace(text, "${password.plaintext}", password
					.getPlaintext());
			text = StringUtils.replace(text, "${serverBaseUrl}", email
					.getUser().getDomain().getRuntime().getServerBaseUrl()
					.toString());

			simpleMessage.setText(text);
			mailSender.send(simpleMessage);
		}
	}
}
