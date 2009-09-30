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
 * Created on 2008-9-12 23:39:01
 */
package cn.net.openid.jos.web.controller;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.domain.UsernameConfiguration;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;

/**
 * Domain configurator controller.
 * 
 * @author Sutra Zhou
 */
public class DomainConfiguratorController extends
		AbstractJosSimpleFormController {
	/**
	 * The logger.
	 */
	private static final Log LOG = LogFactory
			.getLog(DomainConfiguratorController.class);

	/**
	 * The default host name.
	 */
	private static final String DEFAULT_HOST = "www";

	/**
	 * The default host prefix.
	 */
	private static final String DEFAULT_HOST_PREFIX = DEFAULT_HOST + ".";

	/**
	 * The length of default host prefix.
	 */
	private static final int DEFAULT_HOST_PREFIX_LENGTH = DEFAULT_HOST_PREFIX
			.length();

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {
		Map<Integer, String> types = new LinkedHashMap<Integer, String>();
		types.put(Domain.TYPE_SUBDOMAIN, getMessageSourceAccessor().getMessage(
				"domain.title.type.subdomain"));
		types.put(Domain.TYPE_SUBDIRECTORY, getMessageSourceAccessor()
				.getMessage("domain.title.type.subdirectory"));
		request.setAttribute("types", types);

		URL url = new URL(request.getRequestURL().toString());
		String host = url.getHost();
		return this.getOrCreateDomain(host);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onBind(final HttpServletRequest request,
			final Object command)
			throws Exception {
		super.onBind(request, command);
		Domain domain = (Domain) command;
		if (StringUtils.isBlank(domain.getId())) {
			domain.setId(null);
		}
		String[] keys = request.getParameterValues("key");
		String[] values = request.getParameterValues("value");
		int l = keys.length;
		Map<String, String> configuration =
			new LinkedHashMap<String, String>(l);
		for (int i = 0; i < l; i++) {
			if (StringUtils.isNotEmpty(keys[i])
					&& StringUtils.isNotEmpty(values[i])) {
				configuration.put(keys[i], values[i]);
			}
		}
		domain.setConfiguration(configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doSubmitAction(final Object command) throws Exception {
		this.getJosService().saveDomain((Domain) command);
	}

	/**
	 * Get stored domain by host or create a new one if not found.
	 * 
	 * @param host
	 *            the host of the request URL.
	 * @return a stored domain or a new domain
	 */
	private Domain getOrCreateDomain(final String host) {
		String name = host;
		Domain domain = this.getJosService().getDomainByName(name);
		LOG.debug("Stored domain: " + domain);
		if (domain == null) {
			domain = createDomain(host);
		}
		return domain;
	}

	/**
	 * Create a new domain according to the host.
	 * 
	 * @param host
	 *            the host of the request URL
	 * @return a new domain
	 */
	private Domain createDomain(final String host) {
		final Domain domain = new Domain();
		String name = host;
		if (host.startsWith(DEFAULT_HOST_PREFIX)) {
			name = host.substring(DEFAULT_HOST_PREFIX_LENGTH);
			domain.setServerHost(DEFAULT_HOST);
		}
		domain.setName(name);
		if (!host.equals("localhost")) {
			domain.setType(Domain.TYPE_SUBDOMAIN);
		} else {
			domain.setType(Domain.TYPE_SUBDIRECTORY);
		}
		UsernameConfiguration uc = new UsernameConfiguration();
		domain.setUsernameConfiguration(uc);
		uc.setRegex("[a-z]{1,16}");
		uc.setReservedRegex("root|admin|administrator");
		String s = "member|news|jos|mail|smtp|pop3|pop|.*fuck.*";
		uc.setUnallowableRegex(s);
		return domain;
	}
}
