/**
 * Created on 2006-10-16 上午12:39:17
 */
package cn.net.openid.dao;

import java.util.List;

import cn.net.openid.Credential;
import cn.net.openid.User;

/**
 * @author Shutra
 * 
 */
public class DaoFacadeImpl implements DaoFacade {
	private UserDao userDao;

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
	 * @see cn.net.openid.dao.DaoFacade#saveUser(cn.net.openid.User)
	 */
	public String saveUser(User user) {
		return this.userDao.saveUser(user);
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
	 * @see cn.net.openid.dao.DaoFacade#saveCredential(cn.net.openid.Credential)
	 */
	public String saveCredential(Credential credential) {
		return this.userDao.saveCredential(credential);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getCredentiasl(java.lang.String)
	 */
	public List<Credential> getCredentials(String userId) {
		return this.userDao.getCredentials(userId);
	}

}
