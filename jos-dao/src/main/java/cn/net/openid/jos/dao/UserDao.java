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
 * Created on 2006-10-16 12:16:09
 */
package cn.net.openid.jos.dao;

import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.domain.User;

/**
 * {@link User} Data Access Object.
 * 
 * @author Sutra Zhou
 */
public interface UserDao {
	/**
	 * Get user by user ID.
	 * 
	 * @param id
	 *            the user ID
	 * @return the user that ID is the specified one
	 */
	User getUser(String id);

	/**
	 * Get user by {@link Domain} and username.
	 * 
	 * @param domain
	 *            the {@link Domain}
	 * @param username
	 *            the username
	 * @return the user that username is the specified one in the specified
	 *         {@link Domain}
	 */
	User getUser(Domain domain, String username);

	/**
	 * Insert a new {@link User}.
	 * 
	 * @param user
	 *            the {@link User} to insert
	 */
	void insertUser(User user);

	/**
	 * Update the {@link User}.
	 * 
	 * @param user
	 *            the {@link User} to update
	 */
	void updateUser(User user);
}
