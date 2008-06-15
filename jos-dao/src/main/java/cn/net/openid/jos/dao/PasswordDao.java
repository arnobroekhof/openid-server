/**
 * Created on 2008-3-5 下午10:36:17
 */
package cn.net.openid.jos.dao;

import java.util.Collection;

import cn.net.openid.jos.domain.Password;

/**
 * @author Sutra Zhou
 * 
 */
public interface PasswordDao {
	Password getPassword(String id);

	Collection<Password> getPasswords(String userId);

	void insertPassword(Password password);

	void updatePassword(Password password);

	void deletePassword(String id);
}
