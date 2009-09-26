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
 * @author Sutra Zhou
 * 
 */
public class HibernatePasswordDao extends BaseHibernateEntityDao<Password>
		implements PasswordDao {
	private static final String GET_INFINITE_PASSWORD_COUNT_QUERY_STRING = "select count(*) from Password where user = ? and maximumServiceTimes = "
			+ Password.INFINITE_SERVICE_TIMES;

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.bestid.dao.PasswordDao#getPassword(java.lang.String)
	 */
	public Password getPassword(String id) {
		return get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.dao.PasswordDao#getPasswords(cn.net.openid.jos.domain
	 * .User)
	 */
	public Collection<Password> getPasswords(User user) {
		return find("from Password where user.id = ?", user.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.dao.PasswordDao#getPasswordCount(cn.net.openid.jos.
	 * domain.User)
	 */
	public long getInfinitePasswordCount(User user) {
		return count(GET_INFINITE_PASSWORD_COUNT_QUERY_STRING, user);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.bestid.dao.PasswordDao#deletePassword(java.lang.String)
	 */
	public void deletePassword(String id) {
		getHibernateTemplate().delete(this.get(id));
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * cn.net.openid.dao.PasswordDao#insertPassword(cn.net.openid.domain.Password
	 * )
	 */
	public void insertPassword(Password password) {
		getHibernateTemplate().save(password);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * cn.net.openid.dao.PasswordDao#updatePassword(cn.net.openid.domain.Password
	 * )
	 */
	public void updatePassword(Password password) {
		getHibernateTemplate().update(password);
	}

}
