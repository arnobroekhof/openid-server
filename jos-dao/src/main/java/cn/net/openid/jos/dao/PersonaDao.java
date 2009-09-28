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
 * Created on 2008-3-5 22:35:59
 */
package cn.net.openid.jos.dao;

import java.util.Collection;

import cn.net.openid.jos.domain.Persona;
import cn.net.openid.jos.domain.User;

/**
 * {@link Persona} Data Access Object.
 * 
 * @author Sutra Zhou
 * 
 */
public interface PersonaDao {
	/**
	 * Get {@link Persona} by ID.
	 * 
	 * @param id
	 *            the {@link Persona} ID
	 * @return the {@link Persona} which ID is the specified
	 */
	Persona getPersona(String id);

	/**
	 * Get all {@link Persona} of the specified user.
	 * 
	 * @param user
	 *            the user
	 * @return all {@link Persona} of the specified user
	 */
	Collection<Persona> getPersonas(User user);

	/**
	 * Insert a new {@link Persona}.
	 * 
	 * @param persona
	 *            the {@link Persona} to insert
	 */
	void insertPersona(Persona persona);

	/**
	 * Update the {@link Persona}.
	 * 
	 * @param persona
	 *            the {@link Persona} to update
	 */
	void updatePersona(Persona persona);

	/**
	 * Delete the {@link Persona}.
	 * 
	 * @param persona
	 *            the {@link Persona} to delete
	 */
	void deletePersona(Persona persona);

	/**
	 * Return the count of the sites which is using the specified
	 * {@link Persona}.
	 * 
	 * @param persona
	 *            the {@link Persona}
	 * @return the count of the sites which is using the specified
	 *         {@link Persona}.
	 */
	long countSites(Persona persona);
}
