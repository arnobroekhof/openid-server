/**
 * Created on 2006-10-16 上午12:17:52
 */
package cn.net.openid.jos.dao.hibernate;

import cn.net.openid.jos.dao.UserDao;
import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public class HibernateUserDao extends BaseHibernateEntityDao<User> implements
		UserDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.UserDao#getUser(java.lang.String)
	 */
	public User getUser(String id) {
		return get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.UserDao#getUserByUsername(java.lang.String)
	 */
	public User getUserByUsername(String username) {
		return findUnique("from User where username = ?", username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.UserDao#insertUser(cn.net.openid.jos.domain.User)
	 */
	public void insertUser(User user) {
		getHibernateTemplate().save(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.UserDao#updateUser(cn.net.openid.User)
	 */
	public void updateUser(User user) {
		getHibernateTemplate().update(user);
	}

}
