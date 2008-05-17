/**
 * Created on 2008-3-5 下午10:36:17
 */
package cn.net.openid.dao;

import cn.net.openid.domain.Password;

/**
 * @author Sutra Zhou
 * 
 */
public interface PasswordDao {
	Password getPassword(String id);

	Password getPasswordByUserId(String userId);

	Password getPasswordByUsername(String username);

	void insertPassword(Password password);

	void updatePassword(Password password);

	void deletePassword(String id);
}
