/**
 * Created on 2008-5-26 上午12:38:31
 */
package cn.net.openid.jos.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.task.TaskExecutor;

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
		}

	}

	private static final Log log = LogFactory
			.getLog(EmailConfirmationInfoSendTaskExecutor.class);
	private TaskExecutor taskExecutor;

	public EmailConfirmationInfoSendTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void sendEmail(EmailConfirmationInfo emailConfirmationInfo) {
		taskExecutor.execute(new EmailConfirmationInfoSendTask(
				emailConfirmationInfo));
	}

}