/**
 * Created on 2006-10-16 上午12:39:17
 */
package cn.net.openid.dao;

import java.util.List;

import cn.net.openid.Credential;
import cn.net.openid.CredentialException;
import cn.net.openid.CredentialHandler;
import cn.net.openid.OpenidConfiguration;
import cn.net.openid.domain.User;

/**
 * @author Shutra
 * 
 */
public class DaoFacadeImpl implements DaoFacade {
	private OpenidConfiguration openidConfiguration;

	private UserDao userDao;

	private CredentialDao credentialDao;

	private CredentialHandlerDao credentialHandlerDao;

	/**
	 * @param openidConfiguration
	 *            the openidConfiguration to set
	 */
	public void setOpenidConfiguration(OpenidConfiguration openidConfiguration) {
		this.openidConfiguration = openidConfiguration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#buildOpenidUrl(java.lang.String)
	 */
	public String buildOpenidUrl(String username) {
		return String.format("%1$s%2$s%3$s", this.openidConfiguration
				.getOpenidUrlPrefix(), username, this.openidConfiguration
				.getOpenidUrlSuffix());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#deleteCredential(cn.net.openid.Credential)
	 */
	public void deleteCredential(Credential credential)
			throws CredentialException {
		if (this.credentialDao.countCredentials(credential.getUser().getId()) == 1) {
			throw new CredentialException();
		}
		this.credentialDao.deleteCredential(credential);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getCredential(java.lang.String)
	 */
	public Credential getCredential(String id) {
		return this.credentialDao.getCredential(id);
	}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#updateCredential(cn.net.openid.Credential)
	 */
	public void updateCredential(Credential credential) {
		this.credentialDao.updateCredential(credential);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#updateUser(cn.net.openid.User)
	 */
	public void updateUser(User user) {
		this.userDao.updateUser(user);
	}

}
