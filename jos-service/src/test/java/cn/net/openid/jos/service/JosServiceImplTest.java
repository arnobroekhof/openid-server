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
 * Created on 2011-4-10 22:21:21
 */
package cn.net.openid.jos.service;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

import cn.net.openid.jos.dao.DomainDao;
import cn.net.openid.jos.domain.Domain;

/**
 * @author Sutra Zhou
 */
public class JosServiceImplTest {
	private Domain domain;

	@Before
	public void setUp() {
		domain = new Domain();
		domain.setName("openid.example.com");
		domain.setType(Domain.TYPE_SUBDOMAIN);
	}

	@Test
	public void testParseDomain_openid_example_com() {
		DomainDao domainDao = createMock(DomainDao.class);
		expect(domainDao.getDomainByName("openid.example.com")).andReturn(
				domain).once();

		JosServiceImpl service = new JosServiceImpl();
		service.setDomainDao(domainDao);

		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getRequestURL()).andReturn(
				new StringBuffer("http://openid.example.com")).once();
		expect(request.getContextPath()).andReturn("").once();

		replay(domainDao, request);
		Domain parsedDomain = service.parseDomain(request);
		verify(domainDao, request);

		assertEquals("openid.example.com", parsedDomain.getName());
	}

	@Test
	public void testParseDomain_www_openid_example_com() {
		DomainDao domainDao = createMock(DomainDao.class);
		expect(domainDao.getDomainByName("www.openid.example.com")).andReturn(
				null).once();
		expect(domainDao.getDomainByName("openid.example.com")).andReturn(
				domain).once();

		JosServiceImpl service = new JosServiceImpl();
		service.setDomainDao(domainDao);

		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getRequestURL()).andReturn(
				new StringBuffer("http://www.openid.example.com")).once();
		expect(request.getContextPath()).andReturn("").once();

		replay(domainDao, request);
		Domain parsedDomain = service.parseDomain(request);
		verify(domainDao, request);

		assertEquals("openid.example.com", parsedDomain.getName());
	}

	@Test
	public void testParseDomain_page1_username_openid_example_com() {
		DomainDao domainDao = createMock(DomainDao.class);
		expect(domainDao.getDomainByName("page1.username.openid.example.com"))
				.andReturn(null).once();
		expect(domainDao.getDomainByName("username.openid.example.com"))
				.andReturn(null).once();
		expect(domainDao.getDomainByName("openid.example.com")).andReturn(
				domain).once();

		JosServiceImpl service = new JosServiceImpl();
		service.setDomainDao(domainDao);

		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getRequestURL()).andReturn(
				new StringBuffer("http://page1.username.openid.example.com"))
				.once();
		expect(request.getContextPath()).andReturn("").once();

		replay(domainDao, request);
		Domain parsedDomain = service.parseDomain(request);
		verify(domainDao, request);
		assertEquals("openid.example.com", parsedDomain.getName());
	}

}
