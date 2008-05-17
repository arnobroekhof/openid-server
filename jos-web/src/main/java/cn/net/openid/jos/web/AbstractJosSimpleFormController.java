/**
 * Created on 2008-5-12 01:19:46
 */
package cn.net.openid.jos.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openid4java.server.ServerManager;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cn.net.openid.jos.dao.DaoFacade;

/**
 * @author Sutra Zhou
 * 
 */
public abstract class AbstractJosSimpleFormController extends SimpleFormController {
	protected final Log log = LogFactory.getLog(getClass());

	protected DaoFacade daoFacade;
	protected ServerManager serverManager;

	/**
	 * @param daoFacade
	 *            the daoFacade to set
	 */
	public void setDaoFacade(DaoFacade daoFacade) {
		this.daoFacade = daoFacade;
	}

	/**
	 * @param serverManager
	 *            the serverManager to set
	 */
	public void setServerManager(ServerManager serverManager) {
		this.serverManager = serverManager;
	}
}
