/**
 * Created on 2008-3-25 00:10:28
 */
package cn.net.openid.jos.dao;

import java.util.Collection;

import cn.net.openid.jos.domain.AttributeValue;
import cn.net.openid.jos.domain.User;

/**
 * Data access object for {@link AttributeValue}.
 * 
 * @author Sutra Zhou
 * 
 */
public interface AttributeValueDao {
	/**
	 * Get attribute value.
	 * 
	 * @param user
	 * @param attributeId
	 * @return return the value of the user with the specified
	 *         <code>attributeId</code>, if this attribute has multiple
	 *         values, return the first one(which index is 0).
	 */
	String getAttributeValue(User user, String attributeId);

	/**
	 * Get all attribute values.
	 * 
	 * @param user
	 * @param attributeId
	 * @return return the values of the user with the specified
	 *         <code>attributeId</code>, sorted by the index({@link AttributeValue#getIndex()}).
	 */
	Collection<String> getAttributeValues(User user, String attributeId);

	/**
	 * Get all attributes of the user.
	 * 
	 * @param user
	 * @return return the attribute value of the user.
	 */
	Collection<AttributeValue> getUserAttributeValues(User user);

	void saveAttributeValue(AttributeValue attributeValue);
}
