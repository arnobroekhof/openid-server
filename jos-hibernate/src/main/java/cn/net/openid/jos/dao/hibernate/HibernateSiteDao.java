/**
 * Copyright (c) 2006-2009, Redv.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Redv.com nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * Created on 2008-3-5 23:06:36
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
		return find("from Site where user = ? order by approvals desc", 0,
				maxResults, user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.dao.SiteDao#getLatestSites(cn.net.openid.jos.domain
	 * .User, int)
	 */
	public Collection<Site> getLatestSites(User user, int maxResults) {
		return find("from Site where user = ? order by lastAttempt desc", 0,
				maxResults, user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.SiteDao#getSite(cn.net.openid.jos.domain.User,
	 * java.lang.String)
	 */
	public Site getSite(User user, String realmUrl) {
		return findUnique("from Site where user = ? and realm.url = ?", user,
				realmUrl);
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
				"from Site where user = ? and realm.id = ?", user, realmId);
		if (site != null) {
			site.setAlwaysApprove(alwaysApprove);
			this.getHibernateTemplate().update(site);
		}
	}

}
