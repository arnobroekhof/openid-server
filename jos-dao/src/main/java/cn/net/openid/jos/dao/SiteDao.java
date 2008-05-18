/**
 * Created on 2008-3-5 下午10:36:09
 */
package cn.net.openid.jos.dao;

import java.util.Map;

import cn.net.openid.jos.domain.Site;

/**
 * @author Sutra Zhou
 * 
 */
public interface SiteDao {
	Site getSite(String userId, String realmUrl);

	Map<String, Site> getSites(String userId);

	void insertSite(Site site);

	void updateSite(Site site);
}
