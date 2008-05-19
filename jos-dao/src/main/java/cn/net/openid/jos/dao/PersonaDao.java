/**
 * Created on 2008-3-5 下午10:35:59
 */
package cn.net.openid.jos.dao;

import java.util.Collection;

import cn.net.openid.jos.domain.Persona;

/**
 * @author Sutra Zhou
 * 
 */
public interface PersonaDao {
	Persona getPersona(String id);

	Collection<Persona> getPersonas(String userId);

	void insertPersona(Persona persona);

	void updatePersona(Persona persona);

	void deletePersona(String id);
}
