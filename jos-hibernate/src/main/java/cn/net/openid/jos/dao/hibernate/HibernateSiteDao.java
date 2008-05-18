/**
 * Created on 2008-3-5 下午11:06:36
 */
package cn.net.openid.jos.dao.hibernate;

import java.util.List;
import java.util.Map;

import cn.net.openid.jos.dao.SiteDao;
import cn.net.openid.jos.domain.Site;

/**
 * @author Sutra Zhou
 * 
 */
public class HibernateSiteDao extends BaseHibernateEntityDao<Site> implements
		SiteDao {
	/*
	 * （非 Javadoc）
	 * 
	 * @see org.bestid.dao.SiteDao#getSites(java.lang.String)
	 */
	public Map<String, Site> getSites(String userId) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.SiteDao#getSite(java.lang.String,
	 *      java.lang.String)
	 */
	public Site getSite(String userId, String realmUrl) {
		List<Site> sites = this.find(
				"from Site where user.id = ? and realm.url = ?", new String[] {
						userId, realmUrl });
		Site site;
		if (sites.isEmpty()) {
			site = null;
		} else {
			site = sites.get(0);
		}
		return site;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.SiteDao#insertSite(cn.net.openid.jos.domain.Site)
	 */
	public void insertSite(Site site) {
		this.getHibernateTemplate().save(site);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.SiteDao#updateSite(cn.net.openid.jos.domain.Site)
	 */
	public void updateSite(Site site) {
		this.getHibernateTemplate().update(site);
	}

}
