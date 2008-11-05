/**
 * Created on 2008-3-10 22:56:15
 */
package cn.net.openid.jos.dao;

import java.util.Collection;

import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public interface EmailDao {
	Email getEmail(String id);

	Email getPrimaryEmail(User user);

	Collection<Email> getEmails(User user);

	void insertEmail(Email email);

	void updateEmail(Email email);

	void deleteEmail(String id);
}
