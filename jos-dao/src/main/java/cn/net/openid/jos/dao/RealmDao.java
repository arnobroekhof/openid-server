/**
 * Created on 2008-05-18 10:00:45
 */
package cn.net.openid.jos.dao;

import java.util.Collection;

import cn.net.openid.jos.domain.Realm;

/**
 * @author Sutra Zhou
 * 
 */
public interface RealmDao {
	Realm getRealmByUrl(String url);

	Collection<Realm> getLatestRealms(int maxResults);

	void insertRealm(Realm realm);
}
