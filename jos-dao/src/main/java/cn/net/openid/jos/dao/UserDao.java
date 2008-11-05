/**
 * Created on 2006-10-16 12:16:09
 */
package cn.net.openid.jos.dao;

import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public interface UserDao {
	User getUser(String id);

	User getUser(Domain domain, String username);

	void insertUser(User user);

	void updateUser(User user);
}
