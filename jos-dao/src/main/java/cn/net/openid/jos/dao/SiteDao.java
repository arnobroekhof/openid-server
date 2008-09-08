/**
 * Created on 2008-3-5 下午10:36:09
 */
package cn.net.openid.jos.dao;

import java.util.Collection;

import cn.net.openid.jos.domain.Site;
import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public interface SiteDao {
	Site getSite(User user, String realmUrl);

	Collection<Site> getSites(User user);

	Collection<Site> getTopSites(User user, int maxResults);

	void insertSite(Site site);

	void updateSite(Site site);

	void updateAlwaysApprove(User user, String realmId, boolean alwaysApprove);
}
