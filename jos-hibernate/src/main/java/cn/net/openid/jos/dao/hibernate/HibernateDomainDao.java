/**
 * Created on 2008-8-5 23:53:25
 */
package cn.net.openid.jos.dao.hibernate;

import cn.net.openid.jos.dao.DomainDao;
import cn.net.openid.jos.domain.Domain;

/**
 * @author Sutra Zhou
 * 
 */
public class HibernateDomainDao extends BaseHibernateEntityDao<Domain>
		implements DomainDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.DomainDao#getDomain(java.lang.String)
	 */
	public Domain getDomain(String id) {
		return this.get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.DomainDao#getDomainByName(java.lang.String)
	 */
	public Domain getDomainByName(String name) {
		return this.findUnique("from Domain where name = ?", name);
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveDomain(Domain domain) {
		this.getHibernateTemplate().saveOrUpdate(domain);
	}
}
