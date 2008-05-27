/**
 * Created on 2008-5-26 下午11:51:45
 */
package cn.net.openid.jos.service;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.aop.AfterReturningAdvice;

import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.EmailConfirmationInfo;

/**
 * @author Sutra Zhou
 * 
 */
public class InsertEmailAfterReturningAdvice implements AfterReturningAdvice {
	private EmailConfirmationInfoSendTaskExecutor emailConfirmationInfoSendTaskExecutor;

	/**
	 * @param emailConfirmationInfoSendTaskExecutor
	 *            the emailConfirmationInfoSendTaskExecutor to set
	 */
	public void setEmailConfirmationInfoSendTaskExecutor(
			EmailConfirmationInfoSendTaskExecutor emailConfirmationInfoSendTaskExecutor) {
		this.emailConfirmationInfoSendTaskExecutor = emailConfirmationInfoSendTaskExecutor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.aop.AfterReturningAdvice#afterReturning(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		JosService josService = (JosService) target;

		Email email = (Email) args[0];

		StringBuilder seed = new StringBuilder();
		seed.append(email.getUser().getId());
		seed.append(email.getUser().getUsername());
		seed.append(email.getUser().getRegisterTime());
		seed.append(email.getAddress());
		seed.append(RandomStringUtils.randomAlphanumeric(32));
		seed.append(System.currentTimeMillis());
		seed.append(System.nanoTime());
		String confirmationCode = DigestUtils.shaHex(seed.toString());
		EmailConfirmationInfo emailConfirmationInfo = new EmailConfirmationInfo(
				email, confirmationCode);
		emailConfirmationInfo.setSent(true);
		emailConfirmationInfo.setSentDate(new Date());

		josService.insertEmailConfirmationInfo(emailConfirmationInfo);

		this.emailConfirmationInfoSendTaskExecutor
				.sendEmail(emailConfirmationInfo);
	}

}
