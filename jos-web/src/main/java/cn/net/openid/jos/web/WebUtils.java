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
 * Created on 2006-10-22 19:12:00
 */
package cn.net.openid.jos.web;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;

/**
 * The utilities for web.
 * 
 * @author Sutra Zhou
 */
public final class WebUtils {
	/**
	 * The HTTP session key of user session object.
	 */
	private static final String USER_SESSION = "userSession";

	/**
	 * The length of token.
	 */
	private static final int TOKEN_LENGTH = 32;

	/**
	 * Utility classes should not have a public or default constructor.
	 */
	private WebUtils() {
	}

	/**
	 * Generate a approving request token.
	 * 
	 * @return a token string
	 */
	public static String generateToken() {
		return RandomStringUtils.randomAlphanumeric(TOKEN_LENGTH);
	}

	/**
	 * Retrieve user session from HTTP session.
	 * 
	 * @param session
	 *            the HTTP session
	 * @return the user session, if not found in HTTP session, create a new one.
	 */
	public static UserSession getOrCreateUserSession(
			final HttpSession session) {
		return (UserSession) org.springframework.web.util.WebUtils
				.getOrCreateSessionAttribute(session, USER_SESSION,
						UserSession.class);
	}

	/**
	 * Writer string to HTTP output stream.
	 * 
	 * @param httpResp
	 *            the HTTP response
	 * @param response
	 *            the text which to write, if null, do nothing.
	 * @throws IOException
	 *             indicate IO error
	 */
	public static void writeResponse(final HttpServletResponse httpResp,
			final String response) throws IOException {
		if (response == null) {
			return;
		}

		ServletOutputStream os = httpResp.getOutputStream();
		try {
			os.write(response.getBytes());
		} finally {
			os.close();
		}
	}
}
