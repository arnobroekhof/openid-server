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
 * Created on 2008-9-14 12:59:29
 */
package cn.net.openid.jos.web.filter;

import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;

/**
 * @author Sutra Zhou
 * 
 */
public class DomainFilterTest {
	@Test
	public void testPattern() {
		Pattern p = Pattern
				.compile("^/(robots\\.txt|.*\\.(css|js|ico|png|gif)|domain-configurator|domain-configurator-login|hl)(;jsessionid=.*)?$");

		assertTrue(p.matcher("/robots.txt").matches());
		assertTrue(p.matcher("/robots.txt;jsessionid=abc123").matches());

		assertTrue(p.matcher("/styles/domain-configurator.css").matches());
		assertTrue(p.matcher(
				"/styles/domain-configurator.css;jsessionid=abc123").matches());

		assertTrue(p.matcher("/scripts/domain-configurator.js").matches());
		assertTrue(p.matcher(
				"/scripts/domain-configurator.js;jsessionid=abc123").matches());

		assertTrue(p.matcher("/images/login-bg.gif").matches());
		assertTrue(p.matcher("/images/login-bg.gif;jsessionid=abc123")
				.matches());

		assertTrue(p.matcher("/domain-configurator").matches());
		assertTrue(p.matcher("/domain-configurator;jsessionid=abc123")
				.matches());

		assertTrue(p.matcher("/domain-configurator-login").matches());
		assertTrue(p.matcher("/domain-configurator-login;jsessionid=abc123")
				.matches());

		assertTrue(p.matcher("/hl").matches());
		assertTrue(p.matcher("/hl;jsessionid=abc123").matches());
	}
}
