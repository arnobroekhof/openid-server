/**
 * Created on 2006-10-16 上午12:31:39
 */
package cn.net.openid.jos.service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.openid4java.server.ServerManager;

import cn.net.openid.jos.domain.Attribute;
import cn.net.openid.jos.domain.AttributeValue;
import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.EmailConfirmationInfo;
import cn.net.openid.jos.domain.Password;
import cn.net.openid.jos.domain.Persona;
import cn.net.openid.jos.domain.Site;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.service.exception.PersonaInUseException;

/**
 * @author Sutra Zhou
 * 
 */
public interface JosService {
	ServerManager getServerManager(Domain domain);

	/**
	 * Parse domain from the request.
	 * 
	 * @param request
	 *            the HTTP request
	 * @return the domain if it was found, else null
	 */
	Domain parseDomain(HttpServletRequest request)
			throws UnresolvedDomainException;

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

	Domain getDomain(String id);

	Domain getDomainByName(String name);

	Domain getDomainByName(String name, int type);

	void insertDomain(Domain domain);

	User getUser(String id);

	User getUser(Domain domain, String username);

	User getUser(Domain domain, String username, String passwordPlaintext);

	/**
	 * Generate a random string for EmailConfiratmionInfo.
	 * 
	 * @param email
	 *            the email as a random seed
	 * @return a string which length is 40.
	 */
	String generateConfirmationCode(Email email);

	EmailConfirmationInfo getEmailConfirmationInfo(String confirmationCode);

	void confirmEmail(String confirmationCode)
			throws EmailConfirmationInfoNotFoundException;

	Attribute getAttribute(String id);

	Collection<Attribute> getAttributes();

	void saveAttribute(Attribute attribute);

	void deleteAttribute(String id);

	/* User's methods */

	Collection<Password> getPasswords(User user);

	Password getPassword(User user, String passwordId);

	void updatePassword(User user, String passwordId, String name,
			String passwordPlaintext);

	void deletePasswords(User user, String[] passwordIds)
			throws LastPasswordException;

	/**
	 * 该方法的事务处理由Spring的事务处理保证。
	 * 
	 * @param user
	 * @param password
	 */
	void insertUser(User user, Password password);

	Email getEmail(User user, String id);

	Collection<Email> getEmails(User user);

	void insertEmail(User user, Email email);

	void deleteEmail(User user, String id);

	void setPrimaryEmail(User user, String id);

	void insertEmailConfirmationInfo(User user,
			EmailConfirmationInfo emailConfirmationInfo);

	void updateEmailConfirmationInfo(User user,
			EmailConfirmationInfo emailConfirmationInfo);

	Collection<AttributeValue> getUserAttributeValues(User user);

	void saveAttributeValues(User user,
			Collection<AttributeValue> attributeValues);

	boolean isAlwaysApprove(User user, String realmUrl);

	void updateApproval(User user, String realmUrl);

	void updateAlwaysApprove(User user, String realmId, boolean alwaysApprove);

	/**
	 * When user click allow(allow_once or allow_forever) button, invoke this
	 * method to login the site approvals.
	 * 
	 * @param user
	 * @param realm
	 * @param persona
	 * @param forever
	 */
	void allow(User user, String realm, Persona persona, boolean forever);

	Site getSite(User user, String realmUrl);

	Collection<Site> getSites(User user);

	Persona getPersona(User user, String id);

	Collection<Persona> getPersonas(User user);

	void insertPersona(User user, Persona persona);

	void updatePersona(User user, Persona persona);

	void deletePersonas(User user, String[] personaIds)
			throws PersonaInUseException;
}
