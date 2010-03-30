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
 * Created on 2010-3-30 22:15:25
 */
package cn.net.openid.jos.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.domain.UsernameConfiguration;

/**
 * @author sutra
 * 
 */
public class ParseUsernameSubDomainTest {
	private JosServiceImpl jsi;
	private Domain domain;
	private MockHttpServletRequest request;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		jsi = new JosServiceImpl();
		jsi.setSystemReservedWordPattern("home|login");

		domain = new Domain();
		domain.setType(Domain.TYPE_SUBDOMAIN);
		domain.setId("1");
		domain.setName("example.org");
		UsernameConfiguration uc = new UsernameConfiguration();
		uc.setRegex("[a-z]{1,16}");
		uc.setReservedRegex("root|admin|administrator");
		uc.setUnallowableRegex("member|news|jos|mail|smtp|pop3|pop|.*fuck.*");
		domain.setUsernameConfiguration(uc);

		request = new MockHttpServletRequest();
	}

	/**
	 * Test method for
	 * {@link cn.net.openid.jos.service.JosServiceImpl#parseUsername(cn.net.openid.jos.domain.Domain, javax.servlet.http.HttpServletRequest)}
	 * .
	 */
	@Test
	public void testParseUsernameWithoutServerHost() {
		domain.setServerHost(null);
		domain.setMemberPath(null);

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("myusername.example.org");
		String username = jsi.parseUsername(domain, request);
		assertEquals("myusername", username);

		request.setServerName("myusername.example.org");
		request.setContextPath("/mycontext");
		request.setRequestURI("/mycontext/myusername");
		username = jsi.parseUsername(domain, request);
		assertEquals("myusername", username);

		domain.setMemberPath("");
		request.setServerName("myusername.example.org");
		username = jsi.parseUsername(domain, request);
		assertEquals("myusername", username);

		domain.setMemberPath("member");
		request.setServerName("myusername.example.org");
		request.setContextPath("/mycontext");
		request.setRequestURI("/mycontext/member/myusername");
		username = jsi.parseUsername(domain, request);
		assertEquals("myusername", username);
	}

	/**
	 * Test method for
	 * {@link cn.net.openid.jos.service.JosServiceImpl#parseUsername(cn.net.openid.jos.domain.Domain, javax.servlet.http.HttpServletRequest)}
	 * .
	 */
	@Test
	public void testParseUsernameWithServerHost() {
		domain.setServerHost(null);
		domain.setMemberPath(null);
		domain.setServerHost("www");

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerName("myusername.example.org");
		String username = jsi.parseUsername(domain, request);
		assertEquals("myusername", username);

		request.setServerName("myusername.example.org");
		request.setContextPath("/mycontext");
		request.setRequestURI("/mycontext/myusername");
		username = jsi.parseUsername(domain, request);
		assertEquals("myusername", username);

		domain.setMemberPath("");
		request.setServerName("myusername.example.org");
		username = jsi.parseUsername(domain, request);
		assertEquals("myusername", username);

		domain.setMemberPath("member");
		request.setServerName("myusername.example.org");
		request.setContextPath("/mycontext");
		request.setRequestURI("/mycontext/member/myusername");
		username = jsi.parseUsername(domain, request);
		assertEquals("myusername", username);
	}

	@Test
	public void testSystemReservedWord() {
		jsi.setSystemReservedWordPattern("home|myhome");
		request.setServerName("example.org");
		request.setRequestURI("/home");
		String username = jsi.parseUsername(domain, request);
		assertNull(username);
	}

	@Test
	public void testDomainReservedWord() {
		request.setServerName("example.org");
		request.setRequestURI("/root");
		String username = jsi.parseUsername(domain, request);
		assertNull(username);
	}

	@Test
	public void testServerHost() {
		domain.setName("test.example.org");
		domain.setServerHost("test");
		request.setServerName("hello.test.example.org");
		String username = jsi.parseUsername(domain, request);
		assertEquals("hello", username);

		domain.setName("test.example.org");
		domain.setServerHost("test");
		request.setServerName("test.test.example.org");
		username = jsi.parseUsername(domain, request);
		assertNull(username);
	}

	@Test
	public void testNotMatchUsernameRegex() {
		request.setServerName("example.org");
		request.setRequestURI("/test1");
		String username = jsi.parseUsername(domain, request);
		assertNull(username);
	}

	@Test
	public void testUnallowable() {
		request.setServerName("example.org");
		request.setRequestURI("/news");
		String username = jsi.parseUsername(domain, request);
		assertNull(username);
	}
}
