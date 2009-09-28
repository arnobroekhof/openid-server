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
 * Created on 2006-8-20 21:27:57
 */
package cn.net.openid.jos.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * The base <a href="https://www.hibernate.org/">Hibernate</a> entity DAO.
 * 
 * @author Sutra Zhou
 * @see <a href="http://www.blogjava.net/calvin/archive/2006/04/28/43830.html">
 *      Java5泛型的用法，T.class的获取和为擦拭法站台</a>
 * @param <T>
 *            the entity type
 */
public abstract class BaseHibernateEntityDao<T> extends HibernateDaoSupport {
	/**
	 * The entity class.
	 */
	private Class<T> entityClass;

	/**
	 * Construct a default {@link BaseHibernateEntityDao}.
	 */
	@SuppressWarnings("unchecked")
	public BaseHibernateEntityDao() {
		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Return the persistent instance of the given entity class with the given
	 * identifier, or null if not found.
	 * 
	 * @param id
	 *            the identifier of the persistent instance
	 * @return the persistent instance, or null if not found
	 */
	@SuppressWarnings("unchecked")
	protected T get(final Serializable id) {
		T o = (T) getHibernateTemplate().get(entityClass, id);
		return o;
	}

	/**
	 * Execute an HQL query, binding a number of values to "?" parameters in the
	 * query string.
	 * 
	 * @param queryString
	 *            a query expressed in Hibernate's query language
	 * @param values
	 *            the values of the parameters
	 * @return a List containing the results of the query execution
	 */
	@SuppressWarnings("unchecked")
	protected List<T> find(final String queryString, final Object... values) {
		return (List<T>) getHibernateTemplate().find(queryString, values);
	}

	/**
	 * Execute an HQL query, binding a number of values to "?" parameters in the
	 * query string.
	 * 
	 * @param queryString
	 *            a query expressed in Hibernate's query language
	 * @param firstResult
	 *            the first result index
	 * @param maxResults
	 *            the maximum result count
	 * @param values
	 *            the values of the parameters
	 * @return List the result list
	 */
	@SuppressWarnings("unchecked")
	protected List<T> find(final String queryString, final int firstResult,
			final int maxResults, final Object... values) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session) {
				Query query = session.createQuery(queryString);
				query.setFirstResult(firstResult);
				query.setMaxResults(maxResults);
				if (values != null) {
					for (int i = 0, l = values.length; i < l; i++) {
						query.setParameter(i, values[i]);
					}
				}
				return query.list();
			}
		});
	}

	/**
	 * Find a single row. If 0 row found, return null; if more than 1 rows
	 * found, throw an IncorrectResultSizeDataAccessException.
	 * 
	 * @param queryString
	 *            a query expressed in Hibernate's query language
	 * @param values
	 *            the values of the parameters
	 * @return a single entity or null if 0 row found. Throws
	 *         IncorrectResultSizeDataAccessException if more than 1 row found.
	 */
	protected T findUnique(final String queryString, final Object... values) {
		List<T> list = this.find(queryString, values);
		int size = list.size();
		T t;
		if (size == 0) {
			t = null;
		} else if (size != 1) {
			throw new IncorrectResultSizeDataAccessException(1, size);
		} else {
			t = list.get(0);
		}
		return t;
	}

	/**
	 * Count the rows that meet the conditions.
	 * 
	 * @param queryString
	 *            a query expressed in Hibernate's query language for counting.
	 * @param values
	 *            the values of the parameters
	 * @return the count
	 */
	protected long count(final String queryString, final Object... values) {
		return ((Number) getHibernateTemplate().find(queryString, values)
				.get(0)).longValue();
	}
}
