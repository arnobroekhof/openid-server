/**
 * Created on 2008-3-5 下午10:35:59
 */
package cn.net.openid.jos.dao;

import java.util.Collection;

import cn.net.openid.jos.domain.Persona;
import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public interface PersonaDao {
	Persona getPersona(String id);

	Collection<Persona> getPersonas(User user);

	void insertPersona(Persona persona);

	void updatePersona(Persona persona);

	void deletePersona(Persona persona);

	long countSites(Persona persona);
}
