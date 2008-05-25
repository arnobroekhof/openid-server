/**
 * Created on 2008-5-26 上午01:11:11
 */
package cn.net.openid.jos.dao.hibernate;

import cn.net.openid.jos.dao.EmailConfirmationInfoDao;
import cn.net.openid.jos.domain.EmailConfirmationInfo;

/**
 * @author Sutra Zhou
 * 
 */
public class HibernateEmailConfirmationInfoDao extends
		BaseHibernateEntityDao<EmailConfirmationInfo> implements
		EmailConfirmationInfoDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.EmailConfirmationInfoDao#insertEmailConfirmationInfo(cn.net.openid.jos.domain.EmailConfirmationInfo)
	 */
	public void insertEmailConfirmationInfo(
			EmailConfirmationInfo emailConfirmationInfo) {
		this.getHibernateTemplate().save(emailConfirmationInfo);
	}
}
