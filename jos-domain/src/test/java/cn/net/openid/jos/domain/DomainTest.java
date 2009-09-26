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
 * Created on 2008-8-13 12:15:55
 */
package cn.net.openid.jos.domain;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Sutra Zhou
 * 
 */
public class DomainTest {
	private Domain domain;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		domain = new Domain();
	}

	/**
	 * Test method for
	 * {@link cn.net.openid.jos.domain.Domain#getIdentifierPrefix()}.
	 * 
	 * @throws MalformedURLException
	 */
	@Test
	public void testGetIdentifierPrefix() throws MalformedURLException {
		URL url = new URL("http://example1.com/jos-webapp/");
		domain.getRuntime().setServerBaseUrl(url);
		domain.setName("example1.com");
		domain.setType(Domain.TYPE_SUBDOMAIN);
		assertEquals("http://", domain.getIdentifierPrefix());
	}

	/**
	 * Test method for
	 * {@link cn.net.openid.jos.domain.Domain#getIdentifierPrefix()}.
	 * 
	 * @throws MalformedURLException
	 */
	@Test
	public void testGetIdentifierPrefixSubdirectory()
			throws MalformedURLException {
		URL url = new URL("http://example1.com/jos-webapp/");
		domain.getRuntime().setServerBaseUrl(url);
		domain.setName("example1.com");
		domain.setType(Domain.TYPE_SUBDIRECTORY);
		assertEquals("http://example1.com/jos-webapp/", domain
				.getIdentifierPrefix());

		domain.setMemberPath("abc");
		assertEquals("http://example1.com/jos-webapp/abc/", domain
				.getIdentifierPrefix());
	}

	/**
	 * Test method for
	 * {@link cn.net.openid.jos.domain.Domain#getIdentifierPrefix()}.
	 * 
	 * @throws MalformedURLException
	 */
	@Test
	public void testGetIdentifierPrefixSubdirectoryRoot()
			throws MalformedURLException {
		URL url = new URL("http://example1.com/");
		domain.getRuntime().setServerBaseUrl(url);
		domain.setName("example1.com");
		domain.setType(Domain.TYPE_SUBDIRECTORY);
		assertEquals("http://example1.com/", domain.getIdentifierPrefix());

		domain.setMemberPath("abc");
		assertEquals("http://example1.com/abc/", domain.getIdentifierPrefix());
	}

	/**
	 * Test method for
	 * {@link cn.net.openid.jos.domain.Domain#getIdentifierSuffix()}.
	 * 
	 * @throws MalformedURLException
	 */
	@Test
	public void testGetIdentifierSuffix() throws MalformedURLException {
		URL url = new URL("http://example1.com/jos-webapp/");
		domain.getRuntime().setServerBaseUrl(url);
		domain.setName("example1.com");
		domain.setType(Domain.TYPE_SUBDOMAIN);
		assertEquals(".example1.com/jos-webapp/", domain.getIdentifierSuffix());

		url = new URL("http://example1.com:80/jos-webapp/");
		domain.getRuntime().setServerBaseUrl(url);
		domain.setName("example1.com");
		domain.setType(Domain.TYPE_SUBDOMAIN);
		assertEquals(".example1.com/jos-webapp/", domain.getIdentifierSuffix());
	}

}
