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
 * Created on 2008-5-26 01:06:16
 */
package cn.net.openid.jos.dao;

import cn.net.openid.jos.domain.EmailConfirmationInfo;

/**
 * {@link EmailConfirmationInfo} Data Access Object.
 * 
 * @author Sutra Zhou
 */
public interface EmailConfirmationInfoDao {
	/**
	 * Get email confirmation info by the confirmation code.
	 * 
	 * @param confirmationCode
	 *            the confirmation code
	 * @return the email confirmation info that confirmation code is specified
	 */
	EmailConfirmationInfo getEmailConfirmationInfo(String confirmationCode);

	/**
	 * Insert a new email confirmation info.
	 * 
	 * @param emailConfirmationInfo
	 *            the email confirmation info to insert
	 */
	void insertEmailConfirmationInfo(
			EmailConfirmationInfo emailConfirmationInfo);

	/**
	 * Update the email confirmation info.
	 * 
	 * @param emailConfirmationInfo
	 *            the email confirmation info to update
	 */
	void updateEmailConfirmationInfo(
			EmailConfirmationInfo emailConfirmationInfo);
}
