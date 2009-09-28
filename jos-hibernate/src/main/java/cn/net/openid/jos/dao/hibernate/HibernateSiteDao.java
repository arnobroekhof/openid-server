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
 * The {@link SiteDao} implementation using <a
 * href="https://www.hibernate.org/">Hibernate</a>.
 * 
 * @author Sutra Zhou
 */
public class HibernateSiteDao extends BaseHibernateEntityDao<Site> implements
		SiteDao {
	/**
	 * {@inheritDoc}
	 */
	public Collection<Site> getSites(final User user) {
		return find("from Site where user.id = ?", user.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Site> getTopSites(final User user,
			final int maxResults) {
		return find("from Site where user = ? order by approvals desc", 0,
				maxResults, user);
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Site> getLatestSites(final User user,
			final int maxResults) {
		return find("from Site where user = ? order by lastAttempt desc", 0,
				maxResults, user);
	}

	/**
	 * {@inheritDoc}
	 */
	public Site getSite(final User user, final String realmUrl) {
		return findUnique("from Site where user = ? and realm.url = ?", user,
				realmUrl);
	}

	/**
	 * {@inheritDoc}
	 */
	public void insertSite(final Site site) {
		getHibernateTemplate().save(site);
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateSite(final Site site) {
		getHibernateTemplate().update(site);
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateAlwaysApprove(final User user, final String realmId,
			final boolean alwaysApprove) {
		Site site = this.findUnique(
				"from Site where user = ? and realm.id = ?", user, realmId);
		if (site != null) {
			site.setAlwaysApprove(alwaysApprove);
			this.getHibernateTemplate().update(site);
		}
	}

}
