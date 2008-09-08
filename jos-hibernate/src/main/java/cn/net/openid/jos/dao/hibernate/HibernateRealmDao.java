/**
 * Created on 2008-5-18 10:01:55
 */
package cn.net.openid.jos.dao.hibernate;

import java.util.Collection;

import cn.net.openid.jos.dao.RealmDao;
import cn.net.openid.jos.domain.Realm;

/**
 * @author Sutra Zhou
 * 
 */
public class HibernateRealmDao extends BaseHibernateEntityDao<Realm> implements
		RealmDao {
	public Realm getRealmByUrl(String url) {
		return findUnique("from Realm where url = ?", url);
	}

	public Collection<Realm> getRecentRealms(int maxResults) {
		this.getHibernateTemplate().setMaxResults(maxResults);
		return find("from Realm order by creationDate desc");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.dao.RealmDao#insertRealm(cn.net.openid.jos.domain.Realm
	 * )
	 */
	public void insertRealm(Realm realm) {
		getHibernateTemplate().save(realm);
	}
}
