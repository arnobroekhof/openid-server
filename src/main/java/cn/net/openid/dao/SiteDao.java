/**
 * Created on 2008-3-5 下午10:36:09
 */
package cn.net.openid.dao;

import java.util.Map;

import cn.net.openid.domain.Site;

/**
 * @author sutra
 * 
 */
public interface SiteDao {
	Site getSite(String id);

	Map<String, Site> getSites(String userId);
}
