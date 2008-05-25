/**
 * Created on 2008-5-26 上午12:38:31
 */
package cn.net.openid.jos.service;

import org.springframework.core.task.TaskExecutor;

import cn.net.openid.jos.domain.EmailConfirmationInfo;

/**
 * @author Sutra Zhou
 * 
 */
public class TaskExecutorExample {

	private class MessagePrinterTask implements Runnable {

		private EmailConfirmationInfo emailConfirmationInfo;

		public MessagePrinterTask(EmailConfirmationInfo emailConfirmationInfo) {
			this.emailConfirmationInfo = emailConfirmationInfo;
		}

		public void run() {
			System.out.println(emailConfirmationInfo.getEmail().getAddress());
		}

	}

	private TaskExecutor taskExecutor;

	public TaskExecutorExample(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void sendEmail(EmailConfirmationInfo emailConfirmationInfo) {
		taskExecutor.execute(new MessagePrinterTask(emailConfirmationInfo));
	}

}