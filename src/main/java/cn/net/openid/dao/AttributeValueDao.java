/**
 * Created on 2008-3-25 00:10:28
 */
package cn.net.openid.dao;

import java.util.List;

import cn.net.openid.domain.AttributeValue;

/**
 * Data access object for {@link AttributeValue}.
 * 
 * @author sutra
 * 
 */
public interface AttributeValueDao {
	/**
	 * Get attribute value.
	 * 
	 * @param userId
	 * @param attributeId
	 * @return return the value of the user with the specified
	 *         <code>attributeId</code>, if this attribute has multiple
	 *         values, return the first one(which index is 0).
	 */
	String getAttributeValue(String userId, String attributeId);

	/**
	 * Get all attribute values.
	 * 
	 * @param userId
	 * @param attributeId
	 * @return return the values of the user with the specified
	 *         <code>attributeId</code>, sorted by the index({@link AttributeValue#getIndex()}).
	 */
	List<String> getAttributeValues(String userId, String attributeId);

	/**
	 * Get all attributes of the user.
	 * 
	 * @param userId
	 * @return return the attribute value of the user.
	 */
	List<AttributeValue> getUserAttributeValues(String userId);
}
