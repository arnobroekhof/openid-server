/**
 * Created on 2008-3-10 下午10:58:02
 */
package cn.net.openid.dao.hibernate;

import java.util.Collection;
import java.util.List;

import cn.net.openid.dao.EmailDao;
import cn.net.openid.domain.Email;

/**
 * @author sutra
 * 
 */
public class HibernateEmailDao extends BaseHibernateEntityDao<Email> implements
		EmailDao {

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.EmailDao#deleteEmail(java.lang.String)
	 */
	public void deleteEmail(String id) {
		this.getSession().delete(this.get(id));
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.EmailDao#getEmail(java.lang.String)
	 */
	public Email getEmail(String id) {
		return this.get(id);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.EmailDao#insertEmail(cn.net.openid.domain.Email)
	 */
	public void insertEmail(Email email) {
		this.getHibernateTemplate().save(email);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.EmailDao#getEmailsByUserId(java.lang.String)
	 */
	public Collection<Email> getEmailsByUserId(String userId) {
		List<Email> emails = this.find("from Email where user.id = ?", userId);
		return emails;
	}

}
