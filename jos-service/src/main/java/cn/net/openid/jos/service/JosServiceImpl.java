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
 * Created on 2006-10-16 12:39:17
 */
package cn.net.openid.jos.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openid4java.server.ServerManager;

import cn.net.openid.jos.dao.AttributeDao;
import cn.net.openid.jos.dao.AttributeValueDao;
import cn.net.openid.jos.dao.DomainDao;
import cn.net.openid.jos.dao.EmailConfirmationInfoDao;
import cn.net.openid.jos.dao.EmailDao;
import cn.net.openid.jos.dao.PasswordDao;
import cn.net.openid.jos.dao.PersonaDao;
import cn.net.openid.jos.dao.RealmDao;
import cn.net.openid.jos.dao.SiteDao;
import cn.net.openid.jos.dao.UserDao;
import cn.net.openid.jos.domain.Attribute;
import cn.net.openid.jos.domain.AttributeValue;
import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.domain.DomainRuntime;
import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.EmailConfirmationInfo;
import cn.net.openid.jos.domain.Password;
import cn.net.openid.jos.domain.Persona;
import cn.net.openid.jos.domain.Realm;
import cn.net.openid.jos.domain.Site;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.service.exception.
EmailConfirmationInfoNotFoundException;
import cn.net.openid.jos.service.exception.LastPasswordException;
import cn.net.openid.jos.service.exception.NoPermissionException;
import cn.net.openid.jos.service.exception.PersonaInUseException;
import cn.net.openid.jos.service.exception.UnresolvedDomainException;

/**
 * Service implementation.
 * 
 * @author Sutra Zhou
 */
public class JosServiceImpl implements JosService {
	/**
	 * The logger.
	 */
	private static final Log LOG = LogFactory.getLog(JosServiceImpl.class);

	/**
	 * Indicate the logger is debug enabled.
	 */
	private static final boolean DEBUG = LOG.isDebugEnabled();

	/**
	 * Server managers.
	 */
	private final Map<Domain, ServerManager> serverManagers =
		new HashMap<Domain, ServerManager>();

	/**
	 * The configurator password.
	 */
	private String configuratorPassword;

	/**
	 * The available locales.
	 */
	private Collection<Locale> availableLocales;

	/**
	 * The pattern of the system reserved word.
	 */
	private Pattern systemReservedWordPattern;

	/**
	 * The password generator.
	 */
	private PasswordGenerator passwordGenerator;

	/**
	 * The minimum length of the generated one time password.
	 */
	private int oneTimePasswordMinLength;

	/**
	 * The maximum length of the generated one time password.
	 */
	private int oneTimePasswordMaxLength;

	/**
	 * The length of seed to generate email confirmation code.
	 */
	private int emailConfirmationCodeSeedLength;

	/* DAOs */

	/**
	 * The {@link Domain} DAO.
	 */
	private DomainDao domainDao;

	/**
	 * The {@link User} DAO.
	 */
	private UserDao userDao;

	/**
	 * The {@link Password} DAO.
	 */
	private PasswordDao passwordDao;

	/**
	 * The {@link Email} DAO.
	 */
	private EmailDao emailDao;

	/**
	 * The {@link EmailConfirmationInfo} DAO.
	 */
	private EmailConfirmationInfoDao emailConfirmationInfoDao;

	/**
	 * The {@link Attribute} DAO.
	 */
	private AttributeDao attributeDao;

	/**
	 * The {@link AttributeValue} DAO.
	 */
	private AttributeValueDao attributeValueDao;

	/**
	 * The {@link Realm} DAO.
	 */
	private RealmDao realmDao;

	/**
	 * The {@link Site} DAO.
	 */
	private SiteDao siteDao;

	/**
	 * The {@link Persona} DAO.
	 */
	private PersonaDao personaDao;

	/**
	 * The default constructor.
	 */
	public JosServiceImpl() {
		this.availableLocales = Collections.unmodifiableCollection(Arrays
				.asList(Locale.getAvailableLocales()));
	}

	/**
	 * Set the configurator password.
	 * 
	 * @param configuratorPassword
	 *            the configuratorPassword to set
	 */
	public void setConfiguratorPassword(final String configuratorPassword) {
		String s = StringUtils.trimToNull(configuratorPassword);
		if ("BLANK".equals(s) || StringUtils.isBlank(s)) {
			this.configuratorPassword = null;
		} else {
			this.configuratorPassword = s;
		}
	}

	/**
	 * Set the regular expression of the pattern of the system wide reserved
	 * word.
	 * 
	 * @param systemReservedWordPattern
	 *            the systemReservedWordPattern to set
	 */
	public void setSystemReservedWordPattern(
			final String systemReservedWordPattern) {
		this.systemReservedWordPattern = Pattern
				.compile(systemReservedWordPattern.trim());
	}

	/**
	 * Set the minimum length of the generated one time password.
	 * 
	 * @param oneTimePasswordMinLength
	 *            the oneTimePasswordMinLength to set
	 */
	public void setOneTimePasswordMinLength(
			final int oneTimePasswordMinLength) {
		this.oneTimePasswordMinLength = oneTimePasswordMinLength;
	}

	/**
	 * Set the maximum length of the generated one time password.
	 * 
	 * @param oneTimePasswordMaxLength
	 *            the oneTimePasswordMaxLength to set
	 */
	public void setOneTimePasswordMaxLength(
			final int oneTimePasswordMaxLength) {
		this.oneTimePasswordMaxLength = oneTimePasswordMaxLength;
	}

	/**
	 * Set the length of seed to generate email confirmation code.
	 * 
	 * @param emailConfirmationCodeSeedLength
	 *            the emailConfirmationCodeSeedLength to set
	 */
	public void setEmailConfirmationCodeSeedLength(
			final int emailConfirmationCodeSeedLength) {
		this.emailConfirmationCodeSeedLength = emailConfirmationCodeSeedLength;
	}

	/**
	 * Set the {@link Domain} DAO.
	 * 
	 * @param domainDao
	 *            the domainDao to set
	 */
	public void setDomainDao(final DomainDao domainDao) {
		this.domainDao = domainDao;
	}

	/**
	 * Set the {@link User} DAO.
	 * 
	 * @param userDao
	 *            the userDao to set
	 */
	public void setUserDao(final UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * Set the {@link Password} DAO.
	 * 
	 * @param passwordDao
	 *            the passwordDao to set
	 */
	public void setPasswordDao(final PasswordDao passwordDao) {
		this.passwordDao = passwordDao;
	}

	/**
	 * Set the {@link Email} DAO.
	 * 
	 * @param emailDao
	 *            the {@link Email} DAO to set
	 */
	public void setEmailDao(final EmailDao emailDao) {
		this.emailDao = emailDao;
	}

	/**
	 * Set the {@link EmailConfirmationInfo} DAO.
	 * 
	 * @param emailConfirmationInfoDao
	 *            the emailConfirmationInfoDao to set
	 */
	public void setEmailConfirmationInfoDao(
			final EmailConfirmationInfoDao emailConfirmationInfoDao) {
		this.emailConfirmationInfoDao = emailConfirmationInfoDao;
	}

	/**
	 * Set the {@link Attribute} DAO.
	 * 
	 * @param attributeDao
	 *            the attributeDao to set
	 */
	public void setAttributeDao(final AttributeDao attributeDao) {
		this.attributeDao = attributeDao;
	}

	/**
	 * @param attributeValueDao
	 *            the attributeValueDao to set
	 */
	public void setAttributeValueDao(
			final AttributeValueDao attributeValueDao) {
		this.attributeValueDao = attributeValueDao;
	}

	/**
	 * Set the {@link Realm} DAO.
	 * 
	 * @param realmDao
	 *            the realmDao to set
	 */
	public void setRealmDao(final RealmDao realmDao) {
		this.realmDao = realmDao;
	}

	/**
	 * Set the {@link Site} DAO.
	 * 
	 * @param siteDao
	 *            the siteDao to set
	 */
	public void setSiteDao(final SiteDao siteDao) {
		this.siteDao = siteDao;
	}

	/**
	 * Set the {@link Persona} DAO.
	 * 
	 * @param personaDao
	 *            the personaDao to set
	 */
	public void setPersonaDao(final PersonaDao personaDao) {
		this.personaDao = personaDao;
	}

	/**
	 * Set the {@link PasswordGenerator}.
	 * 
	 * @param passwordGenerator
	 *            the passwordGenerator to set
	 */
	public void setPasswordGenerator(
			final PasswordGenerator passwordGenerator) {
		this.passwordGenerator = passwordGenerator;
	}

	/**
	 * Set the available locales.
	 * 
	 * @param availableLocales
	 *            the available locales to set
	 */
	public void setAvailableLocales(final Collection<String> availableLocales) {
		this.availableLocales = new LinkedHashSet<Locale>(availableLocales
				.size());
		for (String language : availableLocales) {
			this.availableLocales.add(LocaleUtils.toLocale(language));
		}
		this.availableLocales = Collections
				.unmodifiableCollection(this.availableLocales);
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Locale> getAvailableLocales() {
		return this.availableLocales;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isSystemReservedWord(final String word) {
		return this.systemReservedWordPattern.matcher(word).matches();
	}

	/**
	 * New server manager for the {@link Domain}.
	 * 
	 * @param domain
	 *            the {@link Domain}
	 * @return the server manager for the {@link Domain}
	 */
	private synchronized ServerManager newServerManager(final Domain domain) {
		ServerManager serverManager = this.serverManagers.get(domain);
		if (serverManager == null) {
			LOG.debug("new a serverManager for " + domain);
			serverManager = new ServerManager();
			serverManager.setOPEndpointUrl(domain.getRuntime()
					.getEndpointUrl().toString());
			this.serverManagers.put(domain, serverManager);
		}
		return serverManager;
	}

	/**
	 * {@inheritDoc}
	 */
	public ServerManager getServerManager(final Domain domain) {
		ServerManager serverManager = this.serverManagers.get(domain);
		if (serverManager == null) {
			serverManager = newServerManager(domain);
		}
		return serverManager;
	}

	/**
	 * {@inheritDoc}
	 */
	public Domain parseDomain(final HttpServletRequest request) {
		LOG.debug("parseDomain is called.");

		Domain domain = null;

		URL requestUrl = this.buildURLQuietly(request.getRequestURL());

		// e.g. www.example.com
		String host = requestUrl.getHost();

		// Find domain by whole host if domain was not found.
		domain = this.getDomainByName(host);

		while (domain == null && StringUtils.countMatches(host, ".") >= 1) {
			host = host.substring(host.indexOf(".") + 1);
			// If we split the host, it must be type subdomain.
			// And the first segment of the host is username.
			domain = this.getDomainByName(host, Domain.TYPE_SUBDOMAIN);
		}

		if (domain != null) {
			DomainRuntime dr = domain.getRuntime();
			URL serverBaseUrl = DomainRuntime.buildServerBaseUrl(domain,
					requestUrl, request.getContextPath());
			dr.setServerBaseUrl(serverBaseUrl);

			URL endpointUrl = DomainRuntime.buildEndpointUrl(domain,
					serverBaseUrl);
			dr.setEndpointUrl(endpointUrl);

		} else {
			throw new UnresolvedDomainException(host);
		}
		return domain;
	}

	/**
	 * {@inheritDoc}
	 */
	public String parseUsername(final Domain domain,
			final HttpServletRequest request) {
		LOG.debug("parseUsername is called.");

		final String username;
		switch (domain.getType()) {
		case Domain.TYPE_SUBDOMAIN:
			URL url = this.buildURLQuietly(request.getRequestURL());
			username = parseUsernameFromSubdomain(domain.getName(), url
					.getHost());
			break;
		case Domain.TYPE_SUBDIRECTORY:
			String uri = request.getRequestURI();
			username = parseUsernameFromSubdirectory(request.getContextPath(),
					domain.getMemberPath(), uri);
			break;
		default:
			username = null;
			break;
		}

		final String ret;
		if (username != null && isUsername(domain, username)) {
			ret = username;
		} else {
			ret = null;
		}
		if (DEBUG) {
			LOG.debug("username: " + username);
		}
		return ret;
	}

	/**
	 * Returns whether the username is correct.
	 * 
	 * @param domain
	 *            the domain
	 * @param username
	 *            the username
	 * @return true if it is a correct username
	 */
	private boolean isUsername(final Domain domain, final String username) {
		boolean isUsername = !isSystemReservedWord(username)
				&& !username.equalsIgnoreCase(domain.getServerHost())
				&& domain.getUsernameConfiguration().isUsername(username)
				&& !domain.getUsernameConfiguration().isReserved(username)
				&& !domain.getUsernameConfiguration().isUnallowable(username);
		return isUsername;
	}

	/**
	 * Build URL quietly.
	 * 
	 * @param sb
	 *            the URL StringBuffer.
	 * @return the URL object from the string buffer.
	 * @throws IllegalArgumentException
	 *             if the url string buffer is malformed
	 * @see #buildURLQuietly(String)
	 */
	private URL buildURLQuietly(final StringBuffer sb) {
		return buildURLQuietly(sb.toString());
	}

	/**
	 * Build URL quietly.
	 * 
	 * @param urlString
	 *            the url string
	 * @return the URL object from the string.
	 * @throws IllegalArgumentException
	 *             if the url string is malformed
	 */
	private URL buildURLQuietly(final String urlString) {
		try {
			return new URL(urlString);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Check whether the input matches the pattern.
	 * 
	 * @param pattern
	 *            the pattern, can be null
	 * @param input
	 *            the input string, can be null
	 * @return fales if pattern or input is null
	 */
	private boolean isMatches(final Pattern pattern, final String input) {
		if (pattern == null || input == null) {
			return false;
		} else {
			return pattern.matcher(input).matches();
		}
	}

	/**
	 * Parse username from the host.
	 * 
	 * @param domainName
	 *            the domain name
	 * @param host
	 *            the host of the URL
	 * @return the username, null if the length of domainName and host are equal
	 */
	private String parseUsernameFromSubdomain(final String domainName,
			final String host) {
		if (domainName.length() == host.length()) {
			return null;
		} else {
			int index = host.lastIndexOf(domainName);
			try {
				return host.substring(0, index - 1);
			} catch (StringIndexOutOfBoundsException e) {
				throw new IllegalArgumentException(
						"The host should contains the domain name.", e);
			}
		}
	}

	/**
	 * Parse username from the request URI.
	 * 
	 * @param contextPath
	 *            the contenxt path of the HTTP request
	 * @param memberPath
	 *            the memberPath of the domain
	 * @param requestURI
	 *            the request URI
	 * @return the username
	 */
	private String parseUsernameFromSubdirectory(final String contextPath,
			final String memberPath, final String requestURI) {
		String prefix = buildMemberPageUriPrefix(contextPath, memberPath);
		String username;
		if (requestURI.startsWith(prefix)) {
			username = StringUtils.removeStart(requestURI, prefix);
		} else {
			username = null;
		}
		return username;
	}

	/**
	 * Returns the prefix of member page URI.
	 * 
	 * @param contextPath
	 *            the context path of the HTTP request
	 * @param memberPath
	 *            the member path of the domain
	 * @return the prefix of member page URI
	 */
	private String buildMemberPageUriPrefix(final String contextPath,
			final String memberPath) {
		StringBuilder sb = new StringBuilder().append(contextPath);
		if (StringUtils.isNotBlank(memberPath)) {
			sb.append("/").append(memberPath);
		}
		sb.append("/");

		String prefix = sb.toString();
		return prefix;
	}

	/***
	 * {@inheritDoc}
	 */
	public Domain getDomain(final String id) {
		return domainDao.getDomain(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public Domain getDomainByName(final String name) {
		return domainDao.getDomainByName(name);
	}

	/**
	 * {@inheritDoc}
	 */
	public Domain getDomainByName(final String name, final int type) {
		Domain domain = domainDao.getDomainByName(name);
		if (domain != null && domain.getType() == type) {
			return domain;
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveDomain(final Domain domain) {
		domainDao.saveDomain(domain);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean checkConfiguratorPassword(final String input) {
		if (StringUtils.isBlank(this.configuratorPassword)) {
			LOG.debug("password is blank, login is not allowed.");
			return false;
		} else {
			return this.configuratorPassword.equals(input);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public User getUser(final String id) {
		return userDao.getUser(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public User getUser(final Domain domain, final String username) {
		User user = userDao.getUser(domain, username);
		if (user != null) {
			// Set domain with the runtime domain.
			user.setDomain(domain);
		}
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	public User login(final Domain domain, final String username,
			final String passwordPlaintext) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		User user = getUser(domain, username);
		if (user == null) {
			return null;
		}

		Collection<Password> passwords = getPasswords(user);
		boolean foundPassword = false;
		String passwordShaHex = DigestUtils.shaHex(passwordPlaintext);
		for (Password password : passwords) {
			if (password.isUsable()
					&& password.getShaHex().equalsIgnoreCase(passwordShaHex)) {
				foundPassword = true;
				password.setUsedTimes(password.getUsedTimes() + 1);
				password.setLastUsedDate(new Date());
				passwordDao.updatePassword(password);
				break;
			}
		}
		return foundPassword ? user : null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void insertUser(final User user, final Password password) {
		userDao.insertUser(user);
		passwordDao.insertPassword(password);
	}

	/**
	 * {@inheritDoc}
	 */
	public void updatePassword(final User user, final String passwordId,
			final String name, final String passwordPlaintext) {
		Password password = getPassword(user, passwordId);
		boolean insert = false;
		if (password == null) {
			password = new Password(user);
			insert = true;
		}

		password.setName(name);
		password.setPlaintext(passwordPlaintext);
		password.setShaHex(DigestUtils.shaHex(password.getPlaintext()));

		if (insert) {
			passwordDao.insertPassword(password);
		} else {
			passwordDao.updatePassword(password);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void deletePasswords(final User user, final String[] passwordIds) {
		for (String passwordId : passwordIds) {
			Password password = passwordDao.getPassword(passwordId);
			if (password.getUser().equals(user)) {
				passwordDao.deletePassword(password.getId());
			}
		}
		if (passwordDao.getInfinitePasswordCount(user) == 0L) {
			throw new LastPasswordException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Password generateOneTimePassword(final User user,
			final Email email) {
		Password ret = null;
		Collection<Password> passwords = this.getPasswords(user);
		for (Password password : passwords) {
			if (password.isUsable()
					&& password.getMaximumServiceTimes() == Password.ONE_TIME) {
				ret = password;
				break;
			}
		}

		if (ret == null) {
			ret = new Password(user);
			ret.setMaximumServiceTimes(Password.ONE_TIME);

			ret.setName(email.getAddress());
			String passwordPlaintext = new String(this.passwordGenerator
					.generate(oneTimePasswordMinLength,
							oneTimePasswordMaxLength));
			ret.setPlaintext(passwordPlaintext);
			ret.setShaHex(DigestUtils.shaHex(ret.getPlaintext()));

			passwordDao.insertPassword(ret);
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteEmail(final User user, final String id) {
		Email email = emailDao.getEmail(id);
		if (email != null && email.getUser().equals(user)) {
			emailDao.deleteEmail(id);
		} else {
			throw new NoPermissionException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setPrimaryEmail(final User user, final String id) {
		Email email = getEmail(user, id);
		if (email != null) {
			Email oldPrimaryEmail = emailDao.getPrimaryEmail(user);
			if (oldPrimaryEmail != null) {
				oldPrimaryEmail.setPrimary(false);
				emailDao.updateEmail(oldPrimaryEmail);
			}
			email.setPrimary(true);
			emailDao.updateEmail(email);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Email getEmail(final User user, final String id) {
		Email email = emailDao.getEmail(id);
		return (email != null && email.getUser().equals(user)) ? email : null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Email> getEmails(final User user) {
		return emailDao.getEmails(user);
	}

	/**
	 * {@inheritDoc}
	 */
	public void insertEmail(final User user, final Email email) {
		if (user.equals(email.getUser())) {
			emailDao.insertEmail(email);
		} else {
			throw new NoPermissionException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String generateConfirmationCode(final Email email) {
		StringBuilder seed = new StringBuilder();
		seed.append(email.getUser().getId());
		seed.append(email.getUser().getUsername());
		seed.append(email.getUser().getCreationDate());
		seed.append(email.getAddress());
		seed.append(RandomStringUtils
				.randomAlphanumeric(emailConfirmationCodeSeedLength));
		seed.append(System.currentTimeMillis());
		seed.append(System.nanoTime());
		return DigestUtils.shaHex(seed.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	public EmailConfirmationInfo getEmailConfirmationInfo(
			final String confirmationCode) {
		return emailConfirmationInfoDao
				.getEmailConfirmationInfo(confirmationCode);
	}

	/**
	 * {@inheritDoc}
	 */
	public void insertEmailConfirmationInfo(final User user,
			final EmailConfirmationInfo emailConfirmationInfo) {
		if (user.equals(emailConfirmationInfo.getEmail().getUser())) {
			emailConfirmationInfoDao
					.insertEmailConfirmationInfo(emailConfirmationInfo);
		} else {
			throw new NoPermissionException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateEmailConfirmationInfo(final User user,
			final EmailConfirmationInfo emailConfirmationInfo) {
		if (user.equals(emailConfirmationInfo.getEmail().getUser())) {
			emailConfirmationInfoDao
					.updateEmailConfirmationInfo(emailConfirmationInfo);
		} else {
			throw new NoPermissionException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void confirmEmail(final String confirmationCode)
			throws EmailConfirmationInfoNotFoundException {
		EmailConfirmationInfo eci = emailConfirmationInfoDao
				.getEmailConfirmationInfo(confirmationCode);
		if (LOG.isDebugEnabled()) {
			LOG.debug("email confirmation info: " + eci);
		}
		if (eci == null || !eci.isSent() || eci.isConfirmed()) {
			throw new EmailConfirmationInfoNotFoundException();
		}

		eci.getEmail().setConfirmed(true);
		// Set primary if its the first confirmed e-mail address of the user.
		eci.getEmail().setPrimary(
				emailDao.getEmails(eci.getEmail().getUser()).size() == 1);
		eci.setConfirmed(true);
		eci.setConfirmedDate(new Date());
		emailConfirmationInfoDao.updateEmailConfirmationInfo(eci);
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Password> getPasswords(final User user) {
		return passwordDao.getPasswords(user);
	}

	/**
	 * {@inheritDoc}
	 */
	public Password getPassword(final User user, final String passwordId) {
		Password password = passwordDao.getPassword(passwordId);
		return (password != null && password.getUser().equals(user)) ? password
				: null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Attribute getAttribute(final String id) {
		return attributeDao.getAttribute(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Attribute> getAttributes() {
		return attributeDao.getAttributes();
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveAttribute(final Attribute attribute) {
		attributeDao.saveAttribute(attribute);
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteAttribute(final String id) {
		attributeDao.deleteAttribute(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<AttributeValue> getUserAttributeValues(User user) {
		return attributeValueDao.getUserAttributeValues(user);
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveAttributeValues(User user,
			Collection<AttributeValue> attributeValues) {
		for (AttributeValue attributeValue : attributeValues) {
			if (user.equals(attributeValue.getUser())) {
				attributeValueDao.saveAttributeValue(attributeValue);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAlwaysApprove(final User user, final String realmUrl) {
		Site site = siteDao.getSite(user, realmUrl);
		return site == null ? false : site.isAlwaysApprove();
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateApproval(final User user, final String realmUrl) {
		Site site = siteDao.getSite(user, realmUrl);
		site.setApprovals(site.getApprovals() + 1);
		siteDao.updateSite(site);
	}

	/**
	 * {@inheritDoc}
	 */
	public void allow(final User user, final String realmUrl,
			final Persona persona, final boolean forever) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("user: " + user);
			LOG.debug("realmUrl: " + realmUrl);
		}
		Site site = siteDao.getSite(user, realmUrl);
		if (site == null) {
			Realm realm = realmDao.getRealmByUrl(realmUrl);
			if (realm == null) {
				realm = new Realm();
				realm.setUrl(realmUrl);
				realmDao.insertRealm(realm);
			}
			site = new Site();
			site.setUser(user);
			site.setRealm(realm);
			site.setLastAttempt(new Date());
			site.setApprovals(1);
			site.setAlwaysApprove(forever);
			site.setPersona(persona);

			siteDao.insertSite(site);
		} else {
			site.setAlwaysApprove(forever);
			site.setApprovals(site.getApprovals() + 1);
			site.setPersona(persona);
			siteDao.updateSite(site);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Site> getSites(final User user) {
		return siteDao.getSites(user);
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Site> getTopSites(final User user, final int maxResults) {
		return siteDao.getTopSites(user, maxResults);
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Site> getLatestSites(final User user,
			final int maxResults) {
		return siteDao.getLatestSites(user, maxResults);
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Realm> getLatestRealms(final int maxResults) {
		return realmDao.getLatestRealms(maxResults);
	}

	/**
	 * {@inheritDoc}
	 */
	public Site getSite(final User user, final String realmUrl) {
		return siteDao.getSite(user, realmUrl);
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateAlwaysApprove(final User user, final String realmId,
			final boolean alwaysApprove) {
		siteDao.updateAlwaysApprove(user, realmId, alwaysApprove);
	}

	/**
	 * {@inheritDoc}
	 */
	public Persona getPersona(final User user, final String id) {
		Persona persona = personaDao.getPersona(id);
		return (persona != null && persona.getUser().equals(user)) ? persona
				: null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Persona> getPersonas(final User user) {
		return personaDao.getPersonas(user);
	}

	/**
	 * {@inheritDoc}
	 */
	public void insertPersona(final User user, final Persona persona) {
		if (user.equals(persona.getUser())) {
			personaDao.insertPersona(persona);
		} else {
			throw new NoPermissionException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void updatePersona(final User user, final Persona persona) {
		if (user.equals(persona.getUser())) {
			personaDao.updatePersona(persona);
		} else {
			throw new NoPermissionException();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void deletePersonas(final User user, final String[] personaIds) {
		for (String personaId : personaIds) {
			Persona persona = getPersona(user, personaId);
			if (persona != null) {
				if (personaDao.countSites(persona) != 0L) {
					throw new PersonaInUseException();
				} else {
					personaDao.deletePersona(persona);
				}
			}
		}
	}
}
