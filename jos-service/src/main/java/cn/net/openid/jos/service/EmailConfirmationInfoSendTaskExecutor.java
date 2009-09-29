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
 * Created on 2008-5-26 12:38:31
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

import cn.net.openid.jos.domain.EmailConfirmationInfo;

/**
 * The email confirmation info send task executor.
 * 
 * @author Sutra Zhou
 */
public class EmailConfirmationInfoSendTaskExecutor {
	/**
	 * Email confirmation info send task.
	 * 
	 * @author Sutra Zhou
	 */
	private class EmailConfirmationInfoSendTask implements Runnable {
		/**
		 * The email confirmation info.
		 */
		private EmailConfirmationInfo emailConfirmationInfo;

		/**
		 * Constructor.
		 * 
		 * @param emailConfirmationInfo
		 *            the email confirmation info.
		 */
		public EmailConfirmationInfoSendTask(
				final EmailConfirmationInfo emailConfirmationInfo) {
			this.emailConfirmationInfo = emailConfirmationInfo;
		}

		/**
		 * {@inheritDoc}
		 */
		public void run() {
			Locale currentLocale = emailConfirmationInfo.getEmail().getLocale();
			LOG.debug("Current locale: " + currentLocale);
			LOG.debug("Sending mail to: "
					+ this.emailConfirmationInfo.getEmail().getAddress());
			ResourceBundle emailConfirmationResources = ResourceBundle
					.getBundle("email-confirmation", currentLocale);

			SimpleMailMessage simpleMessage = new SimpleMailMessage();
			simpleMessage.setTo(this.emailConfirmationInfo.getEmail()
					.getAddress());
			simpleMessage.setSubject(emailConfirmationResources
					.getString("subject"));
			String text = emailConfirmationResources.getString("text");

			text = StringUtils.replace(text, "${identifier}",
					emailConfirmationInfo.getEmail().getUser().getIdentifier());
			text = StringUtils.replace(text, "${confirmationCode}",
					emailConfirmationInfo.getConfirmationCode());
			text = StringUtils.replace(text, "${serverBaseUrl}",
					emailConfirmationInfo.getEmail().getUser().getDomain()
							.getRuntime().getServerBaseUrl().toString());

			simpleMessage.setText(text);
			mailSender.send(simpleMessage);
		}
	}

	/**
	 * Logger.
	 */
	private static final Log LOG = LogFactory
			.getLog(EmailConfirmationInfoSendTaskExecutor.class);

	/**
	 * The task executor.
	 */
	private TaskExecutor taskExecutor;

	/**
	 * The mail sender.
	 */
	private MailSender mailSender;

	/**
	 * Constructor.
	 * 
	 * @param taskExecutor
	 *            the task executor
	 */
	public EmailConfirmationInfoSendTaskExecutor(
			final TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	/**
	 * Set mail sender.
	 * 
	 * @param mailSender
	 *            the mailSender to set
	 */
	public void setMailSender(final MailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * Send mail.
	 * 
	 * @param emailConfirmationInfo
	 *            the email confirmation info to send to the mail
	 */
	public void sendEmail(final EmailConfirmationInfo emailConfirmationInfo) {
		taskExecutor.execute(new EmailConfirmationInfoSendTask(
				emailConfirmationInfo));
	}

}
