/**
 * Created on 2008-3-5 下午10:36:09
 */
package cn.net.openid.jos.dao;

import java.util.List;

import cn.net.openid.jos.domain.Site;
import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public interface SiteDao {
	Site getSite(User user, String realmUrl);

	List<Site> getSites(User user);

	void insertSite(Site site);

	void updateSite(Site site);

	void updateAlwaysApprove(User user, String realmId, boolean alwaysApprove);
}
