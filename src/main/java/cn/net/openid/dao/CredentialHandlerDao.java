/**
 * Created on 2006-10-22 下午07:46:26
 */
package cn.net.openid.dao;

import java.util.List;

import cn.net.openid.CredentialHandler;

/**
 * @author Shutra
 * 
 */
public interface CredentialHandlerDao {
	CredentialHandler getCredentialHandler(String id);

	List<CredentialHandler> getCredentialHandlers();
}
