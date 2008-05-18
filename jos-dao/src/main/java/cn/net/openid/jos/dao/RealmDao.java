/**
 * Created on 2008-05-18 10:00:45
 */
package cn.net.openid.jos.dao;

import cn.net.openid.jos.domain.Realm;

/**
 * @author Sutra Zhou
 * 
 */
public interface RealmDao {
	Realm getRealmByUrl(String url);

	void insertRealm(Realm realm);
}
