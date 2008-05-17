/**
 * Created on 2008-3-25 00:20:38
 */
package cn.net.openid.jos.dao.hibernate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.net.openid.jos.dao.AttributeValueDao;
import cn.net.openid.jos.domain.AttributeValue;

/**
 * @author Sutra Zhou
 * 
 */
public class HibernateAttributeValueDao extends
		BaseHibernateEntityDao<AttributeValue> implements AttributeValueDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.AttributeValueDao#getAttributeValue(java.lang.String,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public String getAttributeValue(String userId, String attributeId) {
		String q = "from AttributeValue where user.id = ? and attribute.id = ? order by index";
		List<AttributeValue> attributeValues = this.find(q, new String[] {
				userId, attributeId });
		if (!attributeValues.isEmpty()) {
			return attributeValues.get(0).getValue();
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.AttributeValueDao#getAttributeValues(java.lang.String,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAttributeValues(String userId, String attributeId) {
		String q = "from AttributeValue where user.id = ? and attribute.id = ? order by index";
		List<AttributeValue> attributeValues = this.find(q, new String[] {
				userId, attributeId });
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
	 * @see cn.net.openid.dao.AttributeValueDao#getUserAttributeValues(java.lang.String)
	 */
	public List<AttributeValue> getUserAttributeValues(String userId) {
		String q = "from AttributeValue where user.id = ? order by index";
		return this.find(q, userId);
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
