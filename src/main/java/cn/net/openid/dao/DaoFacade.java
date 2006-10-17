/**
 * Created on 2006-10-16 上午12:31:39
 */
package cn.net.openid.dao;

import cn.net.openid.Credential;
import cn.net.openid.User;

/**
 * @author Shutra
 * 
 */
public interface DaoFacade {
	User getUser(String id);

	User getUserByOpenid(String openid);

	String saveUser(User user);

	String saveCredential(Credential credential);
}
