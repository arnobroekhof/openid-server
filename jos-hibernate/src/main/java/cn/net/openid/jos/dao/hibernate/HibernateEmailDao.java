/**
 * Created on 2008-3-10 下午10:58:02
 */
package cn.net.openid.jos.dao.hibernate;

import java.util.Collection;

import cn.net.openid.jos.dao.EmailDao;
import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public class HibernateEmailDao extends BaseHibernateEntityDao<Email> implements
		EmailDao {

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.EmailDao#getEmail(java.lang.String)
	 */
	public Email getEmail(String id) {
		return get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.EmailDao#getEmails(cn.net.openid.jos.domain.User)
	 */
	public Collection<Email> getEmails(User user) {
		return find("from Email where user.id = ?", user.getId());
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.EmailDao#insertEmail(cn.net.openid.domain.Email)
	 */
	public void insertEmail(Email email) {
		getHibernateTemplate().save(email);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.EmailDao#deleteEmail(java.lang.String)
	 */
	public void deleteEmail(String id) {
		getSession().delete(this.get(id));
	}

}
