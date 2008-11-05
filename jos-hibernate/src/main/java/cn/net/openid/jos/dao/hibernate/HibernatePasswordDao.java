/**
 * Created on 2008-3-5 23:06:27
 */
package cn.net.openid.jos.dao.hibernate;

import java.util.Collection;

import cn.net.openid.jos.dao.PasswordDao;
import cn.net.openid.jos.domain.Password;
import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public class HibernatePasswordDao extends BaseHibernateEntityDao<Password>
		implements PasswordDao {
	private static final String GET_INFINITE_PASSWORD_COUNT_QUERY_STRING = "select count(*) from Password where user = ? and maximumServiceTimes = "
			+ Password.INFINITE_SERVICE_TIMES;

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.bestid.dao.PasswordDao#getPassword(java.lang.String)
	 */
	public Password getPassword(String id) {
		return get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.dao.PasswordDao#getPasswords(cn.net.openid.jos.domain
	 * .User)
	 */
	public Collection<Password> getPasswords(User user) {
		return find("from Password where user.id = ?", user.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.dao.PasswordDao#getPasswordCount(cn.net.openid.jos.
	 * domain.User)
	 */
	public long getInfinitePasswordCount(User user) {
		return count(GET_INFINITE_PASSWORD_COUNT_QUERY_STRING, user);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.bestid.dao.PasswordDao#deletePassword(java.lang.String)
	 */
	public void deletePassword(String id) {
		getHibernateTemplate().delete(this.get(id));
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * cn.net.openid.dao.PasswordDao#insertPassword(cn.net.openid.domain.Password
	 * )
	 */
	public void insertPassword(Password password) {
		getHibernateTemplate().save(password);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * cn.net.openid.dao.PasswordDao#updatePassword(cn.net.openid.domain.Password
	 * )
	 */
	public void updatePassword(Password password) {
		getHibernateTemplate().update(password);
	}

}
