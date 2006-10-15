/**
 * Created on 2006-8-20 21:27:57
 */
package cn.net.openid.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author Shutra
 * @see <a
 *      href="http://www.blogjava.net/calvin/archive/2006/04/28/43830.html">Java5泛型的用法，T.class的获取和为擦拭法站台</a>
 */
public abstract class BaseHibernateEntityDao<T> extends HibernateDaoSupport {
	private Class<T> entityClass;

	public BaseHibernateEntityDao() {
		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		T o = (T) getHibernateTemplate().get(entityClass, id);
		return o;
	}

}
