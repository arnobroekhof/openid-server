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
	private static final Log LOG = LogFactory
			.getLog(DomainConfiguratorController.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
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
	protected void onBind(HttpServletRequest request, Object command)
			throws Exception {
		super.onBind(request, command);
		Domain domain = (Domain) command;
		if (StringUtils.isBlank(domain.getId())) {
			domain.setId(null);
		}
		String[] keys = request.getParameterValues("key");
		String[] values = request.getParameterValues("value");
		int l = keys.length;
		Map<String, String> configuration = new LinkedHashMap<String, String>(l);
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
	protected void doSubmitAction(Object command) throws Exception {
		this.getJosService().saveDomain((Domain) command);
	}

	/**
	 * Get stored domain by host or create a new one if not found.
	 * 
	 * @param host
	 *            the host of the request URL.
	 * @return a stored domain or a new domain
	 */
	private Domain getOrCreateDomain(String host) {
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
		if (host.startsWith("www.")) {
			name = host.substring(4);
			domain.setServerHost("www");
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
