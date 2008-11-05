/**
 * Created on 2008-3-5 22:36:17
 */
package cn.net.openid.jos.dao;

import java.util.Collection;

import cn.net.openid.jos.domain.Password;
import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public interface PasswordDao {
	Password getPassword(String id);

	Collection<Password> getPasswords(User user);

	long getInfinitePasswordCount(User user);

	void insertPassword(Password password);

	void updatePassword(Password password);

	void deletePassword(String id);
}
