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
			private static final String PREFIX = "user.error.";

			public static final String LOGIN_FAILED = PREFIX + "login.failed";
			public static final String USERNAME_FORMAT = PREFIX
					+ "username.format";
			public static final String USERNAME_RESERVED = PREFIX
					+ "username.reserved";
			public static final String USERNAME_UNALLOWABLE = PREFIX
					+ "username.unallowable";
			public static final String REGISTER_USER_ALREADY_EXISTS = PREFIX
					+ "register.userAlreadyExists";
			public static final String CONFIRMING_PASSWORD_NOT_EQUALS = PREFIX
					+ "confirmingPassword.notEquals";
		}
	}

	public static class Password {
		private static final String PREFIX = "password.";

		public static class Title {
			private static final String PREFIX = Password.PREFIX + "title.";

			public static final String DEFAULT_PASSWORD = PREFIX
					+ "defaultPassword";
		}

		public static class Error {
			public static final String PREFIX = "password.error.";

			public static final String USERNAME_OR_EMAIL_ADDRESS_INCORRECT = PREFIX
					+ "usernameOrEmailAddressIncorrect";
		}
	}

	public static class Email {
		private static final String PREFIX = "email.";

		public static class Error {
			private static final String PREFIX = Email.PREFIX + "error.";

			public static final String ADDRESS = PREFIX + "address";
		}
	}

	public static class Persona {
		private static final String PREFIX = "persona.";

		public static class Error {
			private static final String PREFIX = Persona.PREFIX + ".error.";

			public static final String DOB = PREFIX + ".dob";
		}
	}

	public static class Captcha {
		public static class Error {
			private static final String PREFIX = "captcha.error.";

			public static final String BAD_CAPTCHA = PREFIX + "badCaptcha";
		}
	}
}
