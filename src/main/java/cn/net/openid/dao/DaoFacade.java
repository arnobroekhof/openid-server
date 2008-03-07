/**
 * Created on 2006-10-16 上午12:31:39
 */
package cn.net.openid.dao;

import java.util.List;

import cn.net.openid.Credential;
import cn.net.openid.CredentialException;
import cn.net.openid.CredentialHandler;
import cn.net.openid.domain.Password;
import cn.net.openid.domain.User;

/**
 * @author <a href="mailto:zhoushuqun@gmail.com">Shutra</a>
 * 
 */
public interface DaoFacade {
	String buildOpenidUrl(String username);

	void deleteCredential(Credential credential) throws CredentialException;

	Credential getCredential(String id);

	CredentialHandler getCredentialHandler(String id);

	List<CredentialHandler> getCredentialHandlers();

	List<Credential> getCredentials(String userId);

	User getUser(String id);

	User getUserByUsername(String username);

	Password getPasswordByUserId(String userId);

	String insertCredential(Credential credential);

	/**
	 * 该方法的事务处理由Spring的事务处理保证。
	 * 
	 * @param user
	 * @param password
	 */
	void insertUser(User user, Password password);

	void updateCredential(Credential credential);

	void updateUser(User user);
}
