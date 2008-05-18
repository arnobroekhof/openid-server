/**
 * Created on 2008-3-5 下午10:36:09
 */
package cn.net.openid.jos.dao;

import java.util.List;

import cn.net.openid.jos.domain.Site;

/**
 * @author Sutra Zhou
 * 
 */
public interface SiteDao {
	Site getSite(String userId, String realmUrl);

	List<Site> getSites(String userId);

	void insertSite(Site site);

	void updateSite(Site site);

	void updateAlwaysApprove(String userId, String realmId,
			boolean alwaysApprove);
}
