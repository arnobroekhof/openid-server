/**
 * 
 */
package cn.net.openid.jos.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openid4java.server.ServerManager;
import org.springframework.web.servlet.mvc.Controller;

import cn.net.openid.jos.service.JosService;

/**
 * @author Sutra Zhou
 * 
 */
public abstract class AbstractJosController implements Controller {
	protected final Log log = LogFactory.getLog(getClass());

	protected JosService josService;
	protected ServerManager serverManager;

	/**
	 * @param josService
	 *            the josService to set
	 */
	public void setJosService(JosService josService) {
		this.josService = josService;
	}

	/**
	 * @param serverManager
	 *            the serverManager to set
	 */
	public void setServerManager(ServerManager serverManager) {
		this.serverManager = serverManager;
	}
}
