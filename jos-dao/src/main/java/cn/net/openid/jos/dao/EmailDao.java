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
 * Created on 2008-3-10 22:56:15
 */
package cn.net.openid.jos.dao;

import java.util.Collection;

import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.User;

/**
 * {@link Email} Data Access Object.
 * 
 * @author Sutra Zhou
 */
public interface EmailDao {
	/**
	 * Get {@link Email} by ID.
	 * 
	 * @param id
	 *            the ID
	 * @return the {@link Email}
	 */
	Email getEmail(String id);

	/**
	 * Get the primary {@link Email} of the specified user.
	 * 
	 * @param user
	 *            the user
	 * @return the primary {@link Email} of the specified user
	 */
	Email getPrimaryEmail(User user);

	/**
	 * Get all {@link Email} of the specified user.
	 * 
	 * @param user
	 *            the user
	 * @return all {@link Email} of the specified user
	 */
	Collection<Email> getEmails(User user);

	/**
	 * Insert a new {@link Email}.
	 * 
	 * @param email
	 *            the {@link Email} to insert
	 */
	void insertEmail(Email email);

	/**
	 * Update the {@link Email}.
	 * 
	 * @param email
	 *            the {@link Email} to update
	 */
	void updateEmail(Email email);

	/**
	 * Delete the {@link Email}.
	 * 
	 * @param id
	 *            the id of the {@link Email} that to delete
	 */
	void deleteEmail(String id);
}
