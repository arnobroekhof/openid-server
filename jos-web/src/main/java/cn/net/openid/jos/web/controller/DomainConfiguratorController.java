/**
 * Created on 2008-9-12 下午11:39:01
 */
package cn.net.openid.jos.web.controller;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.domain.UsernameConfiguration;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;

/**
 * Domain configurator controller.
 * 
 * @author Sutra Zhou
 * 
 */
public class DomainConfiguratorController extends
		AbstractJosSimpleFormController {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject
	 * (javax.servlet.http.HttpServletRequest)
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

		Domain domain = new Domain();
		URL url = new URL(request.getRequestURL().toString());
		String host = url.getHost();
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
		String s = "w+|home|server|approve.*|approving|register|login|logout|email.*|password.*|persona.*|site.*|attribute.*|hl|member|news|jos|mail|smtp|pop3|pop|.*fuck.*";
		uc.setUnallowableRegex(s);
		return domain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#doSubmitAction
	 * (java.lang.Object)
	 */
	@Override
	protected void doSubmitAction(Object command) throws Exception {
		this.getJosService().insertDomain((Domain) command);
	}

}
