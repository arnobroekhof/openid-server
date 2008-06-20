/**
 * Created on 2006-8-20 21:27:57
 */
package cn.net.openid.jos.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author Sutra Zhou
 * @see <a
 *      href="http://www.blogjava.net/calvin/archive/2006/04/28/43830.html">Java5泛型的用法，T.class的获取和为擦拭法站台</a>
 */
public abstract class BaseHibernateEntityDao<T> extends HibernateDaoSupport {
	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public BaseHibernateEntityDao() {
		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		T o = (T) getHibernateTemplate().get(entityClass, id);
		return o;
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String queryString, Object... values) {
		return (List<T>) getHibernateTemplate().find(queryString, values);
	}

	/**
	 * Find a single row. If 0 row found, return null; if more than 1 rows
	 * found, throw an IncorrectResultSizeDataAccessException.
	 * 
	 * @param queryString
	 *            a query expressed in Hibernate's query language
	 * @param values
	 *            the values of the parameters
	 * @return a single entity or null if 0 row found.
	 * @throws IncorrectResultSizeDataAccessException
	 *             in case of more than 1 rows found
	 */
	public T findUnique(String queryString, Object... values)
			throws IncorrectResultSizeDataAccessException {
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

}
