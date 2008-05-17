/**
 * Created on 2006-10-16 上午12:16:09
 */
package cn.net.openid.dao;

import cn.net.openid.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public interface UserDao {
	User getUser(String id);

	User getUserByUsername(String username);

	String insertUser(User user);

	void updateUser(User user);
}
