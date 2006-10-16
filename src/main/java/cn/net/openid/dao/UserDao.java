/**
 * Created on 2006-10-16 上午12:16:09
 */
package cn.net.openid.dao;

import cn.net.openid.User;

/**
 * @author Shutra
 * 
 */
public interface UserDao {
	User getUser(String id);

	User getUserByOpenid(String openid);

	String saveUser(User user);
}
