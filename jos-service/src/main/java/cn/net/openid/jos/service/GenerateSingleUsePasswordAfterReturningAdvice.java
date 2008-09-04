/**
 * Created on 2008-9-5 上午01:57:49
 */
package cn.net.openid.jos.service;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.Password;

/**
 * @author Sutra Zhou
 * 
 */
public class GenerateSingleUsePasswordAfterReturningAdvice implements
		AfterReturningAdvice {
	private PasswordSendTaskExecutor passwordSendTaskExecutor;

	/**
	 * @param passwordSendTaskExecutor
	 *            the passwordSendTaskExecutor to set
	 */
	public void setPasswordSendTaskExecutor(
			PasswordSendTaskExecutor passwordSendTaskExecutor) {
		this.passwordSendTaskExecutor = passwordSendTaskExecutor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.aop.AfterReturningAdvice#afterReturning(java.lang
	 * .Object, java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		Password password = (Password) returnValue;
		// User user = (User) args[0];
		Email email = (Email) args[1];

		this.passwordSendTaskExecutor.sendPassword(email, password);
	}

}
