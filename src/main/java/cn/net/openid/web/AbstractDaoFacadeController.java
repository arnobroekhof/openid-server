/**
 * 
 */
package cn.net.openid.web;

import org.springframework.web.servlet.mvc.Controller;

import cn.net.openid.dao.DaoFacade;

/**
 * @author sutra
 * 
 */
public abstract class AbstractDaoFacadeController implements Controller {
	protected DaoFacade daoFacade;

	/**
	 * @param daoFacade
	 *            the daoFacade to set
	 */
	public void setDaoFacade(DaoFacade daoFacade) {
		this.daoFacade = daoFacade;
	}
}
