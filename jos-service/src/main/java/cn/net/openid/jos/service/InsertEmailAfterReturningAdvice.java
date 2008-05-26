/**
 * Created on 2008-5-26 下午11:51:45
 */
package cn.net.openid.jos.service;

import java.lang.reflect.Method;
import java.util.Date;

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

		EmailConfirmationInfo emailConfirmationInfo = new EmailConfirmationInfo(
				email, RandomStringUtils.random(32, true, true));
		emailConfirmationInfo.setSent(true);
		emailConfirmationInfo.setSentDate(new Date());

		josService.insertEmailConfirmationInfo(emailConfirmationInfo);

		this.emailConfirmationInfoSendTaskExecutor
				.sendEmail(emailConfirmationInfo);
	}

}
