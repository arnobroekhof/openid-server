/**
 * Created on 2006-10-16 上午12:39:17
 */
package cn.net.openid.dao;

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
	 * @see cn.net.openid.dao.DaoFacade#getUserByOpenid(java.lang.String)
	 */
	public User getUserByOpenid(String openid) {
		return this.userDao.getUserByOpenid(openid);
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

}
