/**
 * Created on 2008-3-25 00:01:56
 */
package cn.net.openid.dao;

import cn.net.openid.domain.Attribute;

/**
 * @author sutra
 * 
 */
public interface AttributeDao {
	Attribute getAttribute(String id);

	void saveAttribute(Attribute attribute);
}
