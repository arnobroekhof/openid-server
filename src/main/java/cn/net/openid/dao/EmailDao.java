/**
 * Created on 2008-3-10 下午10:56:15
 */
package cn.net.openid.dao;

import java.util.Collection;

import cn.net.openid.domain.Email;

/**
 * @author sutra
 * 
 */
public interface EmailDao {
	Email getEmail(String id);

	Collection<Email> getEmailsByUserId(String userId);

	void insertEmail(Email email);

	void deleteEmail(String id);
}
