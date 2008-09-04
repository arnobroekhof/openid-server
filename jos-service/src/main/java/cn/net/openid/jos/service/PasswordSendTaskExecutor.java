/**
 * Created on 2008-9-5 上午01:58:57
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
 * Send single-use password to email.
 * 
 * @author Sutra Zhou
 * 
 */
public class PasswordSendTaskExecutor {
	private static final Log log = LogFactory
			.getLog(PasswordSendTaskExecutor.class);
	private TaskExecutor taskExecutor;
	private MailSender mailSender;

	public PasswordSendTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	/**
	 * @param mailSender
	 *            the mailSender to set
	 */
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendPassword(Email email, Password password) {
		taskExecutor.execute(new PasswordSendTask(email, password));
	}

	private class PasswordSendTask implements Runnable {
		private Email email;
		private Password password;

		/**
		 * @param email
		 * @param password
		 */
		public PasswordSendTask(Email email, Password password) {
			this.email = email;
			this.password = password;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			Locale currentLocale = email.getLocale();
			log.debug("Current locale: " + currentLocale);
			log.debug("Sending mail to: " + this.email.getAddress());
			ResourceBundle emailConfirmationResources = ResourceBundle
					.getBundle("single-use-password", currentLocale);

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
