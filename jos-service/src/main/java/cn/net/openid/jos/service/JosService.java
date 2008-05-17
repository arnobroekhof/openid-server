/**
 * Created on 2006-10-16 上午12:31:39
 */
package cn.net.openid.jos.service;

import java.util.Collection;
import java.util.List;

import cn.net.openid.jos.domain.Attribute;
import cn.net.openid.jos.domain.AttributeValue;
import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.JosConfiguration;
import cn.net.openid.jos.domain.Password;
import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public interface JosService {
	JosConfiguration getJosConfiguration();

	String buildOpenidUrl(String username);

	User getUser(String id);

	User getUserByUsername(String username);

	Password getPasswordByUserId(String userId);

	/**
	 * 该方法的事务处理由Spring的事务处理保证。
	 * 
	 * @param user
	 * @param password
	 */
	void insertUser(User user, Password password);

	void updateUser(User user);

	Email getEmail(String id);

	Collection<Email> getEmailsByUserId(String userId);

	void insertEmail(Email email);

	void deleteEmail(String id);

	Attribute getAttribute(String id);

	Collection<Attribute> getAttributes();

	void saveAttribute(Attribute attribute);

	void deleteAttribute(String id);

	List<AttributeValue> getUserAttributeValues(String userId);

	void saveAttributeValues(Collection<AttributeValue> attributeValues);
}
