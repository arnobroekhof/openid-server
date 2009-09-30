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
 * Created on 2006-10-16 12:31:39
 */
package cn.net.openid.jos.service;

import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.openid4java.server.ServerManager;

import cn.net.openid.jos.domain.Attribute;
import cn.net.openid.jos.domain.AttributeValue;
import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.EmailConfirmationInfo;
import cn.net.openid.jos.domain.Password;
import cn.net.openid.jos.domain.Persona;
import cn.net.openid.jos.domain.Realm;
import cn.net.openid.jos.domain.Site;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.service.exception.
EmailConfirmationInfoNotFoundException;

/**
 * Service interface of Java OpenID Server.
 * 
 * @author Sutra Zhou
 */
public interface JosService {
	/**
	 * Get all available locales.
	 * 
	 * @return a collection of available locales.
	 */
	Collection<Locale> getAvailableLocales();

	/**
	 * Check if the word is reserved by sytem.
	 * 
	 * @param word
	 *            the word to check
	 * @return true if reserved, otherwise false
	 */
	boolean isSystemReservedWord(String word);

	/**
	 * Get OpenID server manager of the specified domain.
	 * 
	 * @param domain
	 *            the domain
	 * @return the OpenID server manager of the specified domain
	 */
	ServerManager getServerManager(Domain domain);

	/**
	 * Parse domain from the request. Throw UnresolvedDomainException if the
	 * domain has not resolved.
	 * 
	 * @param request
	 *            the HTTP request
	 * @return the domain if it was found, else null
	 */
	Domain parseDomain(HttpServletRequest request);

	/**
	 * Parse username from the request of the specified domain.
	 * 
	 * @param domain
	 *            the domain
	 * @param request
	 *            the HTTP request
	 * @return the username parsed from the request of the specified domain, if
	 *         it was not found, null returned
	 */
	String parseUsername(Domain domain, HttpServletRequest request);

	/**
	 * Get domain by ID.
	 * 
	 * @param id
	 *            the domain ID
	 * @return the domain that ID is the specified
	 */
	Domain getDomain(String id);

	/**
	 * Get domain by the name.
	 * 
	 * @param name
	 *            the domain name
	 * @return the domain that name is the specified
	 */
	Domain getDomainByName(String name);

	/**
	 * Get domain by the name.
	 * 
	 * @param name
	 *            the domain name
	 * @param type
	 *            domain type
	 * @return the domain that name is the specified
	 */
	Domain getDomainByName(String name, int type);

	/**
	 * Save domain.
	 * <p>
	 * Insert the domain if the {@link Domain#getId()} is null, else update.
	 * 
	 * @param domain
	 *            the domain to save
	 */
	void saveDomain(Domain domain);

	/**
	 * Check if the configurator password is correct.
	 * 
	 * @param input
	 *            user input password
	 * @return true if correct, otherwise false
	 */
	boolean checkConfiguratorPassword(String input);

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
	 * Check if password is correct.
	 * 
	 * @param domain
	 *            the domain of the user
	 * @param username
	 *            the username
	 * @param passwordPlaintext
	 *            the password in plain text
	 * @return the logged in user if username and password is matched in the
	 *         domain, otherwise null
	 */
	User login(Domain domain, String username, String passwordPlaintext);

	/**
	 * Generate a random string for EmailConfiratmionInfo.
	 * 
	 * @param email
	 *            the email as a random seed
	 * @return a string which length is 40.
	 */
	String generateConfirmationCode(Email email);

	/**
	 * Get email confirmation info by the confirmation code.
	 * 
	 * @param confirmationCode
	 *            the confirmation code
	 * @return the email confirmation info that confirmation code is specified
	 */
	EmailConfirmationInfo getEmailConfirmationInfo(String confirmationCode);

	/**
	 * Confirm the email.
	 * <p>
	 * Set the email as confirmed, and set as primary email if it is the first
	 * one that user confirmed.
	 * </p>
	 * 
	 * @param confirmationCode
	 *            the email confirmation code string
	 * @throws EmailConfirmationInfoNotFoundException
	 *             if email confirmation info not found, or the confirmation
	 *             info has not been sent, or the confirmation info is used
	 */
	void confirmEmail(String confirmationCode)
			throws EmailConfirmationInfoNotFoundException;

	/**
	 * Get attribute by ID.
	 * 
	 * @param id
	 *            the attribute ID
	 * @return the attribute
	 */
	Attribute getAttribute(String id);

	/**
	 * Get all attributes.
	 * 
	 * @return all attributes
	 */
	Collection<Attribute> getAttributes();

	/**
	 * Save an attribute.
	 * <p>
	 * Insert if {@link Attribute#getId()} is null, else insert.
	 * </p>
	 * 
	 * @param attribute
	 *            the attribute to save.
	 */
	void saveAttribute(Attribute attribute);

	/**
	 * Delete the attribute by ID.
	 * 
	 * @param id
	 *            the ID of the attribute which to delete
	 */
	void deleteAttribute(String id);

	/* User's methods */

	/**
	 * Get all {@link Password} of the specified user.
	 * 
	 * @param user
	 *            the user
	 * @return all {@link Password} of the specified user
	 */
	Collection<Password> getPasswords(User user);

	/**
	 * Get {@link Password} by ID of the specified {@link User}.
	 * 
	 * @param user
	 *            the {@link User}
	 * @param passwordId
	 *            the {@link Password} ID
	 * @return the {@link Password}
	 */
	Password getPassword(User user, String passwordId);

	/**
	 * Update the {@link Password}.
	 * 
	 * @param user
	 *            the owner of the {@link Password}
	 * @param passwordId
	 *            the ID of the {@link Password} to update
	 * @param name
	 *            the display name to update to
	 * @param passwordPlaintext
	 *            the plain text to update to
	 */
	void updatePassword(User user, String passwordId, String name,
			String passwordPlaintext);

	/**
	 * Delete the {@link Password} of the specified {@link User}.
	 * <p>
	 * Deletes only the passwords that owned by the specified user. Throws
	 * LastPasswordException if no infinite password after deleted.
	 * </p>
	 * 
	 * @param user
	 *            the owner
	 * @param passwordIds
	 *            the IDs of the {@link Password} that to delete
	 */
	void deletePasswords(User user, String[] passwordIds);

	/**
	 * Generates an one time password for the user to sent to the email.
	 * 
	 * @param user
	 *            the user
	 * @param email
	 *            the email to send to
	 * @return the generated {@link Password}
	 */
	Password generateOneTimePassword(User user, Email email);

	/**
	 * Insert a new {@link User}.
	 * 
	 * @param user
	 *            the {@link User} to insert
	 * @param password
	 *            the {@link Password} for the user
	 */
	void insertUser(User user, Password password);

	/**
	 * Get {@link Email} by ID for the specified {@link User}.
	 * 
	 * @param user
	 *            the user
	 * @param id
	 *            the ID of the email to get
	 * @return the {@link Email}
	 */
	Email getEmail(User user, String id);

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
	 * @param user
	 *            the owner
	 * @param email
	 *            the {@link Email} to insert
	 */
	void insertEmail(User user, Email email);

	/**
	 * Delete the {@link Email}.
	 * 
	 * @param user
	 *            the operator
	 * @param id
	 *            the id of the {@link Email} that to delete
	 */
	void deleteEmail(User user, String id);

	/**
	 * Set the primary email for the sepecified {@link User}.
	 * 
	 * @param user
	 *            the user
	 * @param id
	 *            the primary email id
	 */
	void setPrimaryEmail(User user, String id);

	/**
	 * Insert a new email confirmation info.
	 * 
	 * @param user
	 *            the owner
	 * @param emailConfirmationInfo
	 *            the email confirmation info to insert
	 */
	void insertEmailConfirmationInfo(User user,
			EmailConfirmationInfo emailConfirmationInfo);

	/**
	 * Update the email confirmation info.
	 * 
	 * @param user
	 *            the owner
	 * @param emailConfirmationInfo
	 *            the email confirmation info to update
	 */
	void updateEmailConfirmationInfo(User user,
			EmailConfirmationInfo emailConfirmationInfo);

	Collection<AttributeValue> getUserAttributeValues(User user);

	void saveAttributeValues(User user,
			Collection<AttributeValue> attributeValues);

	/**
	 * Get if always approve.
	 * 
	 * @param user
	 *            the user
	 * @param realmUrl
	 *            the realm url
	 * @return alwaysApprove true if always approve, false otherwise
	 */
	boolean isAlwaysApprove(User user, String realmUrl);

	/**
	 * Increase the approval count of the realm for the user.
	 * 
	 * @param user
	 *            the user
	 * @param realmUrl
	 *            the URL of the realm
	 */
	void updateApproval(User user, String realmUrl);

	/**
	 * Update the always approve of the realm for the user to the specifed
	 * value.
	 * 
	 * @param user
	 *            the user
	 * @param realmId
	 *            the ID of the realm to update
	 * @param alwaysApprove
	 *            if always approve
	 */
	void updateAlwaysApprove(User user, String realmId, boolean alwaysApprove);

	/**
	 * When user click allow(allow_once or allow_forever) button, invoke this
	 * method to login the site approvals.
	 * 
	 * @param user
	 *            the user
	 * @param realm
	 *            the reaml
	 * @param persona
	 *            the persona
	 * @param forever
	 *            if approve forever
	 */
	void allow(User user, String realm, Persona persona, boolean forever);

	/**
	 * Get {@link Site} by {@link User} and {@link Realm} URL.
	 * 
	 * @param user
	 *            the {@link User}
	 * @param realmUrl
	 *            the {@link Realm} URL
	 * @return the {@link Site}
	 */
	Site getSite(User user, String realmUrl);

	/**
	 * Get all sites of the specified {@link User}.
	 * 
	 * @param user
	 *            the {@link User}
	 * @return all sites of the specified {@link User}
	 */
	Collection<Site> getSites(User user);

	/**
	 * Get top visited sites of the specified user.
	 * 
	 * @param user
	 *            the user
	 * @param maxResults
	 *            max results
	 * @return top visited sites of the specified user
	 */
	Collection<Site> getTopSites(User user, int maxResults);

	/**
	 * Get latest sites that the specified user logged on.
	 * 
	 * @param user
	 *            the user
	 * @param maxResults
	 *            max results
	 * @return latest sites that the user logged on
	 */
	Collection<Site> getLatestSites(User user, int maxResults);

	/**
	 * Get the latest realms.
	 * 
	 * @param maxResults
	 *            max results
	 * @return latest realms
	 */
	Collection<Realm> getLatestRealms(int maxResults);

	/**
	 * Get {@link Persona} by ID for the specified {@link User}.
	 * 
	 * @param user
	 *            the {@link User}
	 * @param id
	 *            the {@link Persona} ID
	 * @return the {@link Persona} which ID is the specified
	 */
	Persona getPersona(User user, String id);

	/**
	 * Get all {@link Persona} of the specified user.
	 * 
	 * @param user
	 *            the user
	 * @return all {@link Persona} of the specified user
	 */
	Collection<Persona> getPersonas(User user);

	/**
	 * Insert a new {@link Persona} of the specifed {@link User}.
	 * 
	 * @param user
	 *            the user
	 * @param persona
	 *            the {@link Persona} to insert
	 */
	void insertPersona(User user, Persona persona);

	/**
	 * Update the {@link Persona} of the specified {@link User}.
	 * 
	 * @param user
	 *            the {@link User}
	 * @param persona
	 *            the {@link Persona} to update
	 */
	void updatePersona(User user, Persona persona);

	/**
	 * Delete the {@link Persona} of the specified {@link User}.
	 * 
	 * @param user
	 *            the {@link User}
	 * @param personaIds
	 *            the IDs of {@link Persona} that to delete
	 */
	void deletePersonas(User user, String[] personaIds);
}
