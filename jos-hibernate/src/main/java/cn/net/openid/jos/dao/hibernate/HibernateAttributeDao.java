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
 * Created on 2008-3-25 00:04:32
 */
package cn.net.openid.jos.dao.hibernate;

import java.util.Collection;

import cn.net.openid.jos.dao.AttributeDao;
import cn.net.openid.jos.domain.Attribute;

/**
 * The {@link AttributeDao} implementation using <a
 * href="https://www.hibernate.org/">Hibernate</a>.
 * 
 * @author Sutra Zhou
 */
public class HibernateAttributeDao extends BaseHibernateEntityDao<Attribute>
		implements AttributeDao {

	/**
	 * {@inheritDoc}
	 */
	public Attribute getAttribute(final String id) {
		return get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Attribute> getAttributes() {
		return this.find("from Attribute");
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveAttribute(final Attribute attribute) {
		this.getHibernateTemplate().saveOrUpdate(attribute);
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteAttribute(final String id) {
		this.getHibernateTemplate().bulkUpdate(
				"delete from AttributeValue where attribute.id = ?", id);
		this.getHibernateTemplate().delete(this.get(id));
	}
}
