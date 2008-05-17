/**
 * Created on 2006-10-16 上午12:17:52
 */
package cn.net.openid.dao.hibernate;

import java.util.List;

import cn.net.openid.Credential;
import cn.net.openid.dao.CredentialDao;

/**
 * @author Shutra
 * 
 */
public class HibernateCredentialDao extends BaseHibernateEntityDao<Credential>
		implements CredentialDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.CredentialDao#countCredentials(java.lang.String)
	 */
	public long countCredentials(String userId) {
		List l = this.getHibernateTemplate().find(
				"select count(*) from Credential where user.id = ?", userId);
		return (Long) l.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.CredentialDao#deleteCredential(cn.net.openid.Credential)
	 */
	public void deleteCredential(Credential credential) {
		this.getHibernateTemplate().delete(credential);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.CredentialDao#getCredential(java.lang.String)
	 */
	public Credential getCredential(String id) {
		return this.get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.CredentialDao#getCredentials(java.lang.String)
	 */
	public List<Credential> getCredentials(String userId) {
		List<Credential> credentials = find(
				"from Credential where user.id = ?", userId);
		return credentials;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.CredentialDao#insertCredential(cn.net.openid.Credential)
	 */
	public String insertCredential(Credential credential) {
		return (String) this.getHibernateTemplate().save(credential);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.CredentialDao#updateCredential(cn.net.openid.Credential)
	 */
	public void updateCredential(Credential credential) {
		this.getHibernateTemplate().update(credential);
	}
}
