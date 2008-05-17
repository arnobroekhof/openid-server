/**
 * Created on 2008-3-25 00:01:56
 */
package cn.net.openid.jos.dao;

import java.util.Collection;

import cn.net.openid.jos.domain.Attribute;

/**
 * @author Sutra Zhou
 * 
 */
public interface AttributeDao {
	Attribute getAttribute(String id);

	Collection<Attribute> getAttributes();

	void saveAttribute(Attribute attribute);

	void deleteAttribute(String id);
}
