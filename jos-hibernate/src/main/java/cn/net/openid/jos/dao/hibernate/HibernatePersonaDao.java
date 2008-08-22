/**
 * Created on 2008-3-5 下午11:06:51
 */
package cn.net.openid.jos.dao.hibernate;

import java.util.Collection;

import cn.net.openid.jos.dao.PersonaDao;
import cn.net.openid.jos.domain.Persona;
import cn.net.openid.jos.domain.User;

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
		return get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.PersonaDao#getPersonas(cn.net.openid.jos.domain.User)
	 */
	public Collection<Persona> getPersonas(User user) {
		return find("from Persona where user.id = ?", user.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.PersonaDao#insertPersona(cn.net.openid.jos.domain.Persona)
	 */
	public void insertPersona(Persona persona) {
		getHibernateTemplate().save(persona);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.PersonaDao#updatePersona(cn.net.openid.jos.domain.Persona)
	 */
	public void updatePersona(Persona persona) {
		getHibernateTemplate().update(persona);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.PersonaDao#deletePersona(cn.net.openid.jos.domain.Persona)
	 */
	public void deletePersona(Persona persona) {
		getHibernateTemplate().delete(persona);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.net.openid.jos.dao.PersonaDao#countSites(cn.net.openid.jos.domain.Persona)
	 */
	public long countSites(Persona persona) {
		return count("select count(*) from Site where persona = ?", persona);
	}
}
