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
 * Created on 2008-3-25 00:20:38
 */
package cn.net.openid.jos.dao.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cn.net.openid.jos.dao.AttributeValueDao;
import cn.net.openid.jos.domain.AttributeValue;
import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public class HibernateAttributeValueDao extends
		BaseHibernateEntityDao<AttributeValue> implements AttributeValueDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.dao.AttributeValueDao#getAttributeValue(cn.net.openid
	 * .jos.domain.User, java.lang.String)
	 */public String getAttributeValue(User user, String attributeId) {
		String q = "from AttributeValue where user.id = ? and attribute.id = ? order by index";
		List<AttributeValue> attributeValues = this.find(q, user.getId(),
				attributeId);
		if (!attributeValues.isEmpty()) {
			return attributeValues.get(0).getValue();
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.dao.AttributeValueDao#getAttributeValues(cn.net.openid
	 * .jos.domain.User, java.lang.String)
	 */
	public Collection<String> getAttributeValues(User user, String attributeId) {
		String q = "from AttributeValue where user.id = ? and attribute.id = ? order by index";
		List<AttributeValue> attributeValues = this.find(q, user.getId(),
				attributeId);
		if (!attributeValues.isEmpty()) {
			List<String> ret = new ArrayList<String>(attributeValues.size());
			for (AttributeValue av : attributeValues) {
				ret.add(av.getValue());
			}
			return ret;
		} else {
			return Collections.emptyList();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.dao.AttributeValueDao#getUserAttributeValues(cn.net
	 * .openid.jos.domain.User)
	 */
	public List<AttributeValue> getUserAttributeValues(User user) {
		return find("from AttributeValue where user.id = ? order by index",
				user.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.dao.AttributeValueDao#saveAttributeValue(cn.net.openid.
	 * domain.AttributeValue)
	 */
	public void saveAttributeValue(AttributeValue attributeValue) {
		this.getHibernateTemplate().saveOrUpdate(attributeValue);
	}
}
