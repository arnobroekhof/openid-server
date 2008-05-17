/**
 * Created on 2008-3-25 00:04:32
 */
package cn.net.openid.dao.hibernate;

import java.util.Collection;

import cn.net.openid.dao.AttributeDao;
import cn.net.openid.domain.Attribute;

/**
 * @author sutra
 * 
 */
public class HibernateAttributeDao extends BaseHibernateEntityDao<Attribute>
		implements AttributeDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.AttributeDao#getAttribute(java.lang.String)
	 */
	public Attribute getAttribute(String id) {
		return get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.AttributeDao#getAttributes()
	 */
	public Collection<Attribute> getAttributes() {
		return this.find("from Attribute");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.AttributeDao#saveAttribute(cn.net.openid.domain.Attribute)
	 */
	public void saveAttribute(Attribute attribute) {
		this.getHibernateTemplate().saveOrUpdate(attribute);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.AttributeDao#deleteAttribute(java.lang.String)
	 */
	public void deleteAttribute(String id) {
		this.getHibernateTemplate().bulkUpdate(
				"delete from AttributeValue where attribute.id = ?", id);
		this.getHibernateTemplate().delete(this.get(id));
	}
}
