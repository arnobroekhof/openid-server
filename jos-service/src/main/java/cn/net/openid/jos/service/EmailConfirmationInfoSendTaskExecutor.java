/**
 * Created on 2008-5-26 上午12:38:31
 */
package cn.net.openid.jos.service;

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
	private class EmailConfirmationInfoSendTask implements Runnable {
		private EmailConfirmationInfo emailConfirmationInfo;

		public EmailConfirmationInfoSendTask(
				EmailConfirmationInfo emailConfirmationInfo) {
			this.emailConfirmationInfo = emailConfirmationInfo;
		}

		public void run() {
			log.debug("Sending mail to: "
					+ this.emailConfirmationInfo.getEmail().getAddress());
			SimpleMailMessage simpleMessage = new SimpleMailMessage();
			simpleMessage.setTo(this.emailConfirmationInfo.getEmail()
					.getAddress());
			simpleMessage.setSubject("Confirmation message.");
			simpleMessage.setText(this.emailConfirmationInfo
					.getConfirmationCode());
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