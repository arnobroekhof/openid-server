/**
 * Created on 2008-3-5 下午10:35:59
 */
package cn.net.openid.dao;

import java.util.Collection;

import cn.net.openid.domain.Persona;

/**
 * @author sutra
 * 
 */
public interface PersonaDao {
	Persona getPersona(String id);

	Collection<Persona> getPersonas(String userId);
}
