/**
 * Created on 2006-10-16 上午12:39:17
 */
package cn.net.openid.dao;

import java.util.List;

import cn.net.openid.Credential;
import cn.net.openid.CredentialHandler;
import cn.net.openid.User;

/**
 * @author Shutra
 * 
 */
public class DaoFacadeImpl implements DaoFacade {
	private UserDao userDao;

	private CredentialDao credentialDao;

	private CredentialHandlerDao credentialHandlerDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getCredentialHandler(java.lang.String)
	 */
	public CredentialHandler getCredentialHandler(String id) {
		return this.credentialHandlerDao.getCredentialHandler(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getCredentialHandlers()
	 */
	public List<CredentialHandler> getCredentialHandlers() {
		return this.credentialHandlerDao.getCredentialHandlers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getCredentiasl(java.lang.String)
	 */
	public List<Credential> getCredentials(String userId) {
		return this.credentialDao.getCredentials(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getUser(java.lang.String)
	 */
	public User getUser(String id) {
		return this.userDao.getUser(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getUserByUsername(java.lang.String)
	 */
	public User getUserByUsername(String username) {
		return this.userDao.getUserByUsername(username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#saveCredential(cn.net.openid.Credential)
	 */
	public String insertCredential(Credential credential) {
		return this.credentialDao.insertCredential(credential);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#insertUser(cn.net.openid.User,
	 *      cn.net.openid.Credential)
	 */
	public void insertUser(User user, Credential credential) {
		this.userDao.insertUser(user);
		this.credentialDao.insertCredential(credential);
	}

	/**
	 * @param credentialDao
	 *            the credentialDao to set
	 */
	public void setCredentialDao(CredentialDao credentialDao) {
		this.credentialDao = credentialDao;
	}

	public void setCredentialHandlerDao(
			CredentialHandlerDao credentialHandlerDao) {
		this.credentialHandlerDao = credentialHandlerDao;
	}

	/**
	 * @param userDao
	 *            the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
