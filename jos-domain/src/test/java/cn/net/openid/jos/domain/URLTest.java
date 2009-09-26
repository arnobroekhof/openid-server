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
 * Created on 2008-8-13 12:40:15
 */
package cn.net.openid.jos.domain;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

/**
 * @author Sutra Zhou
 * 
 */
public class URLTest {
	@Test
	public void testURLPort() throws MalformedURLException {
		// http with default port
		URL url = new URL("http://www.example.com/");
		assertEquals(-1, url.getPort());
		assertEquals(80, url.getDefaultPort());

		// http with default port specified
		url = new URL("http://www.example.com:80/");
		assertEquals(80, url.getPort());
		assertEquals(80, url.getDefaultPort());

		// http with non-default port
		url = new URL("http://www.example.com:8080/");
		assertEquals(8080, url.getPort());
		assertEquals(80, url.getDefaultPort());

		// https with default port
		url = new URL("https://www.example.com/");
		assertEquals(-1, url.getPort());
		assertEquals(443, url.getDefaultPort());

		// https with default port specified
		url = new URL("https://www.example.com:443/");
		assertEquals(443, url.getPort());
		assertEquals(443, url.getDefaultPort());

		// https with non-default port
		url = new URL("https://www.example.com:8443/");
		assertEquals(8443, url.getPort());
		assertEquals(443, url.getDefaultPort());
	}

}
