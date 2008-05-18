/**
 * Created on 2008-5-18 10:01:55
 */
package cn.net.openid.jos.dao.hibernate;

import cn.net.openid.jos.dao.RealmDao;
import cn.net.openid.jos.domain.Realm;

/**
 * @author Sutra Zhou
 * 
 */
public class HibernateRealmDao extends BaseHibernateEntityDao<Realm> implements
		RealmDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.RealmDao#insertRealm(cn.net.openid.jos.domain.Realm)
	 */
	public void insertRealm(Realm realm) {
		this.getHibernateTemplate().save(realm);
	}
}
