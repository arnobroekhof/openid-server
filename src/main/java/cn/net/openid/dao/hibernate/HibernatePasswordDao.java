/**
 * Created on 2008-3-5 下午11:06:27
 */
package cn.net.openid.dao.hibernate;

import java.util.List;

import cn.net.openid.dao.PasswordDao;
import cn.net.openid.domain.Password;

/**
 * @author sutra
 * 
 */
public class HibernatePasswordDao extends BaseHibernateEntityDao<Password>
		implements PasswordDao {

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.bestid.dao.PasswordDao#getPassword(java.lang.String)
	 */
	public Password getPassword(String id) {
		return this.get(id);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.bestid.dao.PasswordDao#getPasswordByUserId(java.lang.String)
	 */
	public Password getPasswordByUserId(String userId) {
		List<Password> passwords = this.find("from Password where user.id = ?",
				userId);
		if (!passwords.isEmpty()) {
			return passwords.get(0);
		} else {
			return null;
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.bestid.dao.PasswordDao#getPasswordByUsername(java.lang.String)
	 */
	public Password getPasswordByUsername(String username) {
		List<Password> passwords = this.find(
				"from Password where user.username = ?", username);
		if (!passwords.isEmpty()) {
			return passwords.get(0);
		} else {
			return null;
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.bestid.dao.PasswordDao#deletePassword(java.lang.String)
	 */
	public void deletePassword(String id) {
		this.getHibernateTemplate().delete(this.get(id));
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.PasswordDao#insertPassword(cn.net.openid.domain.Password)
	 */
	public void insertPassword(Password password) {
		this.getHibernateTemplate().save(password);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.PasswordDao#updatePassword(cn.net.openid.domain.Password)
	 */
	public void updatePassword(Password password) {
		this.getHibernateTemplate().update(password);
	}

}
