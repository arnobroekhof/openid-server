/**
 * Created on 2008-3-5 下午11:06:36
 */
package cn.net.openid.jos.dao.hibernate;

import java.util.Collection;

import cn.net.openid.jos.dao.SiteDao;
import cn.net.openid.jos.domain.Site;
import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public class HibernateSiteDao extends BaseHibernateEntityDao<Site> implements
		SiteDao {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.dao.SiteDao#getSites(cn.net.openid.jos.domain.User)
	 */
	public Collection<Site> getSites(User user) {
		return find("from Site where user.id = ?", user.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.dao.SiteDao#getTopSites(cn.net.openid.jos.domain.User,
	 * int)
	 */
	public Collection<Site> getTopSites(User user, int maxResults) {
		this.getHibernateTemplate().setMaxResults(maxResults);
		try {
			return find("from Site where user = ? order by approvals desc",
					user);
		} finally {
			this.getHibernateTemplate().setMaxResults(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.dao.SiteDao#getLatestSites(cn.net.openid.jos.domain
	 * .User, int)
	 */
	public Collection<Site> getLatestSites(User user, int maxResults) {
		this.getHibernateTemplate().setMaxResults(maxResults);
		try {
			return find("from Site where user = ? order by lastAttempt desc",
					user);
		} finally {
			this.getHibernateTemplate().setMaxResults(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.SiteDao#getSite(cn.net.openid.jos.domain.User,
	 * java.lang.String)
	 */
	public Site getSite(User user, String realmUrl) {
		return findUnique("from Site where user.id = ? and realm.url = ?", user
				.getId(), realmUrl);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.dao.SiteDao#insertSite(cn.net.openid.jos.domain.Site)
	 */
	public void insertSite(Site site) {
		getHibernateTemplate().save(site);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.dao.SiteDao#updateSite(cn.net.openid.jos.domain.Site)
	 */
	public void updateSite(Site site) {
		getHibernateTemplate().update(site);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.dao.SiteDao#updateAlwaysApprove(cn.net.openid.jos.domain
	 * .User, java.lang.String, boolean)
	 */
	public void updateAlwaysApprove(User user, String realmId,
			boolean alwaysApprove) {
		Site site = this.findUnique(
				"from Site where user.id = ? and realm.id = ?", user.getId(),
				realmId);
		if (site != null) {
			site.setAlwaysApprove(alwaysApprove);
			this.getHibernateTemplate().update(site);
		}
	}

}
