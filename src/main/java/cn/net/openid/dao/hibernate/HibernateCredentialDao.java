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
}
