/**
 * Created on 2008-8-5 23:50:48
 */
package cn.net.openid.jos.dao;

import cn.net.openid.jos.domain.Domain;

/**
 * @author Sutra Zhou
 * 
 */
public interface DomainDao {
	Domain getDomain(String id);

	Domain getDomainByName(String name);

	void insertDomain(Domain domain);
}
