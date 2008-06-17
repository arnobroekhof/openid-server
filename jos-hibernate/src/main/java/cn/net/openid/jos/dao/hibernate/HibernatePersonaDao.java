/**
 * Created on 2008-3-5 下午11:06:51
 */
package cn.net.openid.jos.dao.hibernate;

import java.util.Collection;
import java.util.List;

import cn.net.openid.jos.dao.PersonaDao;
import cn.net.openid.jos.domain.Persona;

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
		List<Persona> personas = this.find("from Persona where user.id = ?",
				userId);
		return personas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.PersonaDao#insertPersona(cn.net.openid.jos.domain.Persona)
	 */
	public void insertPersona(Persona persona) {
		this.getHibernateTemplate().save(persona);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.PersonaDao#updatePersona(cn.net.openid.jos.domain.Persona)
	 */
	public void updatePersona(Persona persona) {
		this.getHibernateTemplate().update(persona);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.dao.PersonaDao#deletePersona(cn.net.openid.jos.domain.Persona)
	 */
	public void deletePersona(Persona persona) {
		this.getHibernateTemplate().delete(persona);
	}
}
