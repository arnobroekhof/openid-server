/**
 * Created on 2008-5-12 01:19:46
 */
package cn.net.openid.jos.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.service.JosService;
import cn.net.openid.jos.web.filter.DomainFilter;

/**
 * @author Sutra Zhou
 * 
 */
public abstract class AbstractJosSimpleFormController extends
		SimpleFormController {
	protected final Log log = LogFactory.getLog(getClass());

	private JosService josService;

	/**
	 * @return the josService
	 */
	public JosService getJosService() {
		return josService;
	}

	/**
	 * @param josService
	 *            the josService to set
	 */
	public void setJosService(JosService josService) {
		this.josService = josService;
	}

	/**
	 * Get the current domain.
	 * 
	 * @param request
	 *            the HTTP request
	 * @return current domain which parsed from the request url by
	 *         {@link DomainFilter}.
	 */
	public Domain getDomain(HttpServletRequest request) {
		return DomainFilter.getDomain(request);
	}

	/* User Session */

	public UserSession getUserSession(HttpSession session) {
		return WebUtils.getOrCreateUserSession(session);
	}

	public UserSession getUserSession(HttpServletRequest request) {
		return getUserSession(request.getSession());
	}

	public User getUser(HttpSession session) {
		return this.getUserSession(session).getUser();
	}

	public User getUser(HttpServletRequest request) {
		return this.getUserSession(request).getUser();
	}

}
