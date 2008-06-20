/**
 * Created on 2008-3-25 00:20:38
 */
package cn.net.openid.jos.dao.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cn.net.openid.jos.dao.AttributeValueDao;
import cn.net.openid.jos.domain.AttributeValue;
import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public class HibernateAttributeValueDao extends
		BaseHibernateEntityDao<AttributeValue> implements AttributeValueDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.AttributeValueDao#getAttributeValue(cn.net.openid.jos.domain.User,
	 *      java.lang.String)
	 */@SuppressWarnings("unchecked")
	public String getAttributeValue(User user, String attributeId) {
		String q = "from AttributeValue where user.id = ? and attribute.id = ? order by index";
		List<AttributeValue> attributeValues = this.find(q, user.getId(),
				attributeId);
		if (!attributeValues.isEmpty()) {
			return attributeValues.get(0).getValue();
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.AttributeValueDao#getAttributeValues(cn.net.openid.jos.domain.User,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Collection<String> getAttributeValues(User user, String attributeId) {
		String q = "from AttributeValue where user.id = ? and attribute.id = ? order by index";
		List<AttributeValue> attributeValues = this.find(q, user.getId(),
				attributeId);
		if (!attributeValues.isEmpty()) {
			List<String> ret = new ArrayList<String>(attributeValues.size());
			for (AttributeValue av : attributeValues) {
				ret.add(av.getValue());
			}
			return ret;
		} else {
			return Collections.emptyList();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.AttributeValueDao#getUserAttributeValues(cn.net.openid.jos.domain.User)
	 */
	public List<AttributeValue> getUserAttributeValues(User user) {
		return find("from AttributeValue where user.id = ? order by index",
				user.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.AttributeValueDao#saveAttributeValue(cn.net.openid.domain.AttributeValue)
	 */
	public void saveAttributeValue(AttributeValue attributeValue) {
		this.getHibernateTemplate().saveOrUpdate(attributeValue);
	}
}
