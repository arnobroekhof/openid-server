/**
 * Created on 2008-3-5 下午11:06:51
 */
package cn.net.openid.dao.hibernate;

import java.util.Collection;
import java.util.List;

import cn.net.openid.dao.PersonaDao;
import cn.net.openid.domain.Persona;

/**
 * @author Sutra Zhou
 * 
 */
public class HibernatePersonaDao extends BaseHibernateEntityDao<Persona>
		implements PersonaDao {

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.bestid.dao.PersonaDao#getPersona(java.lang.String)
	 */
	public Persona getPersona(String id) {
		return this.get(id);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.bestid.dao.PersonaDao#getPersonas(java.lang.String)
	 */
	public Collection<Persona> getPersonas(String userId) {
		List<Persona> personas = this.find("from Persona where User.id = ?",
				userId);
		return personas;
	}

}
