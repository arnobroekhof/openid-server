/**
 * Copyright (c) 2006-2009, Redv.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Redv.com nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * Created on 2008-8-9 19:21:23
 */
package cn.net.openid.jos.web;

/**
 * @author Sutra Zhou
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
