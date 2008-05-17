/**
 * Created on 2008-3-5 下午11:06:36
 */
package cn.net.openid.dao.hibernate;

import java.util.Map;

import cn.net.openid.dao.SiteDao;
import cn.net.openid.domain.Site;

/**
 * @author sutra
 * 
 */
public class HibernateSiteDao extends BaseHibernateEntityDao<Site> implements
		SiteDao {

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.bestid.dao.SiteDao#getSite(java.lang.String)
	 */
	public Site getSite(String id) {
		return this.get(id);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.bestid.dao.SiteDao#getSites(java.lang.String)
	 */
	public Map<String, Site> getSites(String userId) {
		return null;
	}

}
