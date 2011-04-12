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
 * Created on 2011-4-12 15:08:48
 */
package cn.net.openid.jos.domain;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class DomainRuntimeTest {

	@Test
	public void testHttp() throws MalformedURLException {
		Domain domain = new Domain("example.com", Domain.TYPE_SUBDOMAIN, "www");
		URL requestUrl = new URL("http://username.example.com/login");
		String requestContextPath = "/joids";
		URL serverBaseUrl = DomainRuntime.buildServerBaseUrl(domain,
				requestUrl, requestContextPath);
		assertEquals(new URL("http://www.example.com/joids/"), serverBaseUrl);
	}

	@Test
	public void testHttpsEndpointEnabled() throws MalformedURLException {
		Map<String, String> configuration = new HashMap<String, String>();
		configuration.put("https.endpoint.enabled", "true");
		Domain domain = new Domain("example.com", Domain.TYPE_SUBDOMAIN, "www");
		domain.setConfiguration(configuration);
		URL requestUrl = new URL("http://username.example.com/login");
		String requestContextPath = "/joids";

		URL serverBaseUrl = DomainRuntime.buildServerBaseUrl(domain,
				requestUrl, requestContextPath);
		assertEquals(new URL("http://www.example.com/joids/"), serverBaseUrl);

		URL endpointUrl = DomainRuntime.buildEndpointUrl(domain, serverBaseUrl);
		assertEquals(new URL("https://www.example.com/joids/server"),
				endpointUrl);
	}

	@Test
	public void testHttpsEndpointDisabled() throws MalformedURLException {
		Map<String, String> configuration = new HashMap<String, String>();
		configuration.put("https.endpoint.enabled", "false");
		Domain domain = new Domain("example.com", Domain.TYPE_SUBDOMAIN, "www");
		domain.setConfiguration(configuration);
		URL requestUrl = new URL("https://username.example.com/login");
		String requestContextPath = "/joids";

		URL serverBaseUrl = DomainRuntime.buildServerBaseUrl(domain,
				requestUrl, requestContextPath);
		assertEquals(new URL("https://www.example.com/joids/"), serverBaseUrl);

		URL endpointUrl = DomainRuntime.buildEndpointUrl(domain, serverBaseUrl);
		assertEquals(new URL("http://www.example.com/joids/server"),
				endpointUrl);
	}

}
