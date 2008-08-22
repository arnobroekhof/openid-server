/**
 * Created on 2008-8-9 下午07:21:23
 */
package cn.net.openid.jos.web;

/**
 * @author Sutra Zhou
 * 
 */
public class MessageCodes {
	public static class Error {
		public static final String REQUIRED = "required";
	}

	public static class User {
		public static class Error {
			public static final String LOGIN_FAILED = "user.error.login.failed";
			public static final String USERNAME_FORMAT = "user.error.username.format";
			public static final String USERNAME_RESERVED = "user.error.username.reserved";
			public static final String USERNAME_UNALLOWABLE = "user.error.username.unallowable";
			public static final String REGISTER_USER_ALREADY_EXISTS = "user.error.register.userAlreadyExists";
			public static final String CONFIRMING_PASSWORD_NOT_EQUALS = "user.error.confirmingPassword.notEquals";
		}
	}

	public static class Password {
		public static class Title {
			public static final String DEFAULT_PASSWORD = "password.title.defaultPassword";
		}
	}
}
