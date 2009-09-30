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
 * Created on 2008-3-5 22:36:09
 */
package cn.net.openid.jos.dao;

import java.util.Collection;

import cn.net.openid.jos.domain.Site;
import cn.net.openid.jos.domain.User;

/**
 * {@link Site} Data Access Object.
 * 
 * @author Sutra Zhou
 */
public interface SiteDao {
	/**
	 * Get {@link Site} by {@link User} and realm URL.
	 * 
	 * @param user
	 *            the {@link User}
	 * @param realmUrl
	 *            the {@link RealmDao} URL
	 * @return the {@link Site}
	 */
	Site getSite(User user, String realmUrl);

	/**
	 * Get all sites of the specified {@link User}.
	 * 
	 * @param user
	 *            the {@link User}
	 * @return all sites of the specified {@link User}
	 */
	Collection<Site> getSites(User user);

	/**
	 * Get top visited sites of the specified user.
	 * 
	 * @param user
	 *            the user
	 * @param maxResults
	 *            max results
	 * @return top visited sites of the specified user
	 */
	Collection<Site> getTopSites(User user, int maxResults);

	/**
	 * Get latest sites that the specified user logged on.
	 * 
	 * @param user
	 *            the user
	 * @param maxResults
	 *            max results
	 * @return latest sites that the user logged on
	 */
	Collection<Site> getLatestSites(User user, int maxResults);

	/**
	 * Insert a new site.
	 * 
	 * @param site
	 *            the site to insert
	 */
	void insertSite(Site site);

	/**
	 * Update the site.
	 * 
	 * @param site
	 *            the site to update
	 */
	void updateSite(Site site);

	/**
	 * Update if the the {@link User} always approve the authentication requests
	 * from the realm.
	 * 
	 * @param user
	 *            the {@link User}
	 * @param realmId
	 *            the realm ID
	 * @param alwaysApprove
	 *            indicate if always approve
	 */
	void updateAlwaysApprove(User user, String realmId, boolean alwaysApprove);
}
