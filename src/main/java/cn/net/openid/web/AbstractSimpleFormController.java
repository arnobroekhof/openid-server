/**
 * Created on 2008-5-12 01:19:46
 */
package cn.net.openid.web;

import org.springframework.web.servlet.mvc.SimpleFormController;

import cn.net.openid.dao.DaoFacade;

/**
 * @author sutra
 * 
 */
public abstract class AbstractSimpleFormController extends SimpleFormController {
	protected DaoFacade daoFacade;

	/**
	 * @param daoFacade
	 *            the daoFacade to set
	 */
	public void setDaoFacade(DaoFacade daoFacade) {
		this.daoFacade = daoFacade;
	}
}
