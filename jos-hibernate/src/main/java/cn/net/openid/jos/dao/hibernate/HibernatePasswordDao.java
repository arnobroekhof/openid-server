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
 * Created on 2008-3-5 23:06:27
 */
package cn.net.openid.jos.dao.hibernate;

import java.util.Collection;

import cn.net.openid.jos.dao.PasswordDao;
import cn.net.openid.jos.domain.Password;
import cn.net.openid.jos.domain.User;

/**
 * The {@link PasswordDao} implementation using <a
 * href="https://www.hibernate.org/">Hibernate</a>.
 * 
 * @author Sutra Zhou
 */
public class HibernatePasswordDao extends BaseHibernateEntityDao<Password>
		implements PasswordDao {
	/**
	 * The HQL query string for getting infinite passwor count.
	 */
	private static final String GET_INFINITE_PASSWORD_COUNT_QUERY_STRING
		= "select count(*) from Password"
			+ " where user = ? and maximumServiceTimes = "
			+ Password.INFINITE_SERVICE_TIMES;

	/**
	 * {@inheritDoc}
	 */
	public Password getPassword(final String id) {
		return get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Password> getPasswords(final User user) {
		return find("from Password where user.id = ?", user.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	public long getInfinitePasswordCount(final User user) {
		return count(GET_INFINITE_PASSWORD_COUNT_QUERY_STRING, user);
	}

	/**
	 * {@inheritDoc}
	 */
	public void deletePassword(final String id) {
		getHibernateTemplate().delete(this.get(id));
	}

	/**
	 * {@inheritDoc}
	 */
	public void insertPassword(final Password password) {
		getHibernateTemplate().save(password);
	}

	/**
	 * {@inheritDoc}
	 */
	public void updatePassword(final Password password) {
		getHibernateTemplate().update(password);
	}

}
