/**
 * Created on 2006-10-16 上午12:17:52
 */
package cn.net.openid.dao.hibernate;

import java.util.List;

import cn.net.openid.User;
import cn.net.openid.dao.UserDao;

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
	 * @see cn.net.openid.dao.UserDao#getUserByOpenid(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public User getUserByOpenid(String openid) {
		List<User> users = getHibernateTemplate().find(
				"from User where openid = ?", openid);
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
	public String saveUser(User user) {
		return (String) this.getHibernateTemplate().save(user);
	}

}
