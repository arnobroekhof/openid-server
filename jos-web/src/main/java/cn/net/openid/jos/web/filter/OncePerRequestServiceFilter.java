/**
 * Created on 2008-8-7 下午11:49:20
 */
package cn.net.openid.jos.web.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.net.openid.jos.service.JosService;

/**
 * @author Sutra Zhou
 * 
 */
public abstract class OncePerRequestServiceFilter extends OncePerRequestFilter {
	protected final Log log = LogFactory.getLog(this.getClass());

	private JosService service;

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(JosService service) {
		this.service = service;
	}

	protected JosService getService() {
		return this.service;
	}

}
