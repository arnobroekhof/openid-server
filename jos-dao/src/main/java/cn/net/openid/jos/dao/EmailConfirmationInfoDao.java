/**
 * Created on 2008-5-26 上午01:06:16
 */
package cn.net.openid.jos.dao;

import cn.net.openid.jos.domain.EmailConfirmationInfo;

/**
 * @author Sutra Zhou
 * 
 */
public interface EmailConfirmationInfoDao {
	EmailConfirmationInfo getEmailConfirmationInfo(String confirmationCode);

	void insertEmailConfirmationInfo(EmailConfirmationInfo emailConfirmationInfo);

	void updateEmailConfirmationInfo(EmailConfirmationInfo emailConfirmationInfo);
}
