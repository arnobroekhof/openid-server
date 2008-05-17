/**
 * Created on 2006-8-20 21:27:57
 */
package cn.net.openid.jos.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

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
	public List<T> find(String queryString) {
		return (List<T>) getHibernateTemplate().find(queryString);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String queryString, Object value) {
		return (List<T>) getHibernateTemplate().find(queryString, value);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String queryString, Object[] values) {
		return (List<T>) getHibernateTemplate().find(queryString, values);
	}

	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		T o = (T) getHibernateTemplate().get(entityClass, id);
		return o;
	}

}
