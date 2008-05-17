/**
 * Created on 2006-10-22 下午07:47:12
 */
package cn.net.openid.dao.hibernate;

import java.util.List;

import cn.net.openid.CredentialHandler;
import cn.net.openid.dao.CredentialHandlerDao;

/**
 * @author <a href="mailto:zhoushuqun@gmail.com">Shutra</a>
 * 
 */
public class HibernateCredentialHandlerDao extends
		BaseHibernateEntityDao<CredentialHandler> implements
		CredentialHandlerDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.CredentialHandlerDao#getCredentialHandlers()
	 */
	public List<CredentialHandler> getCredentialHandlers() {
		return this.find("from CredentialHandler");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.CredentialHandlerDao#getCredentialHandler(java.lang.String)
	 */
	public CredentialHandler getCredentialHandler(String id) {
		return this.get(id);
	}

}
