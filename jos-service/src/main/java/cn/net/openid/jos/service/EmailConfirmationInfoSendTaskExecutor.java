/**
 * Created on 2008-5-26 上午12:38:31
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
 * @author Sutra Zhou
 * 
 */
public class EmailConfirmationInfoSendTaskExecutor {
	/**
	 * Email confirmation info send task.
	 * 
	 * @author Sutra Zhou
	 * 
	 */
	private class EmailConfirmationInfoSendTask implements Runnable {
		private EmailConfirmationInfo emailConfirmationInfo;

		public EmailConfirmationInfoSendTask(
				EmailConfirmationInfo emailConfirmationInfo) {
			this.emailConfirmationInfo = emailConfirmationInfo;
		}

		public void run() {
			Locale currentLocale = emailConfirmationInfo.getEmail().getLocale();
			log.debug("Current locale: " + currentLocale);
			log.debug("Sending mail to: "
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

	private static final Log log = LogFactory
			.getLog(EmailConfirmationInfoSendTaskExecutor.class);

	private TaskExecutor taskExecutor;
	private MailSender mailSender;

	public EmailConfirmationInfoSendTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	/**
	 * @param mailSender
	 *            the mailSender to set
	 */
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendEmail(EmailConfirmationInfo emailConfirmationInfo) {
		taskExecutor.execute(new EmailConfirmationInfoSendTask(
				emailConfirmationInfo));
	}

}