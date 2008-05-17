/**
 * Created on 2006-10-16 上午12:31:39
 */
package cn.net.openid.dao;

import java.util.Collection;
import java.util.List;

import cn.net.openid.domain.Attribute;
import cn.net.openid.domain.AttributeValue;
import cn.net.openid.domain.Email;
import cn.net.openid.domain.Password;
import cn.net.openid.domain.User;

/**
 * @author <a href="mailto:zhoushuqun@gmail.com">Shutra</a>
 * 
 */
public interface DaoFacade {
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
