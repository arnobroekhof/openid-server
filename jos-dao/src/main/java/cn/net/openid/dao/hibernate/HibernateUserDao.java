/**
 * Created on 2006-10-16 上午12:17:52
 */
package cn.net.openid.dao.hibernate;

import java.util.List;

import cn.net.openid.dao.UserDao;
import cn.net.openid.domain.User;

/**
 * @author Shutra
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
		return this.get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.UserDao#getUserByUsername(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public User getUserByUsername(String username) {
		List<User> users = getHibernateTemplate().find(
				"from User where username = ?", username);
		if (users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.UserDao#saveUser(cn.net.openid.User)
	 */
	public String insertUser(User user) {
		return (String) this.getHibernateTemplate().save(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.UserDao#updateUser(cn.net.openid.User)
	 */
	public void updateUser(User user) {
		this.getHibernateTemplate().update(user);
	}

}
