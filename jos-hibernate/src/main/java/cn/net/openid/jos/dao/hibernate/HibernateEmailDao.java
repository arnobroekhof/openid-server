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
 * Created on 2008-3-10 22:58:02
 */
package cn.net.openid.jos.dao.hibernate;

import java.util.Collection;

import cn.net.openid.jos.dao.EmailDao;
import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public class HibernateEmailDao extends BaseHibernateEntityDao<Email> implements
		EmailDao {

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.EmailDao#getEmail(java.lang.String)
	 */
	public Email getEmail(String id) {
		return get(id);
	}

	public Email getPrimaryEmail(User user) {
		return findUnique("from Email where user.id = ? and primary = true",
				user.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.EmailDao#getEmails(cn.net.openid.jos.domain.User)
	 */
	public Collection<Email> getEmails(User user) {
		return find("from Email where user.id = ?", user.getId());
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.EmailDao#insertEmail(cn.net.openid.domain.Email)
	 */
	public void insertEmail(Email email) {
		getHibernateTemplate().save(email);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.EmailDao#updateEmail(cn.net.openid.jos.domain.Email)
	 */
	public void updateEmail(Email email) {
		getHibernateTemplate().update(email);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.EmailDao#deleteEmail(java.lang.String)
	 */
	public void deleteEmail(String id) {
		getSession().delete(this.get(id));
	}

}
