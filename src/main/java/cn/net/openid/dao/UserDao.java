/**
 * Created on 2006-10-16 上午12:16:09
 */
package cn.net.openid.dao;

import java.util.List;

import cn.net.openid.Credential;
import cn.net.openid.User;

/**
 * @author Shutra
 * 
 */
public interface UserDao {
	User getUser(String id);

	User getUserByOpenid(String openid);

	List<Credential> getCredentials(String userId);

	String saveUser(User user);

	String saveCredential(Credential credential);
}
