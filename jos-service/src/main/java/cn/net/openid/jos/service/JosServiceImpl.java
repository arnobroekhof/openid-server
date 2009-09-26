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
import cn.net.openid.jos.service.exception.EmailConfirmationInfoNotFoundException;
import cn.net.openid.jos.service.exception.LastPasswordException;
import cn.net.openid.jos.service.exception.NoPermissionException;
import cn.net.openid.jos.service.exception.PersonaInUseException;
import cn.net.openid.jos.service.exception.UnresolvedDomainException;

/**
 * @author Sutra Zhou
 * 
 */
public class JosServiceImpl implements JosService {
	private static final Log log = LogFactory.getLog(JosServiceImpl.class);

	private final Map<Domain, ServerManager> serverManagers = new HashMap<Domain, ServerManager>();

	private String configuratorPassword;
	private Collection<Locale> availableLocales;
	private Pattern systemReservedWordPattern;
	private PasswordGenerator passwordGenerator;

	/* DAOs */

	private DomainDao domainDao;
	private UserDao userDao;
	private PasswordDao passwordDao;
	private EmailDao emailDao;
	private EmailConfirmationInfoDao emailConfirmationInfoDao;
	private AttributeDao attributeDao;
	private AttributeValueDao attributeValueDao;
	private RealmDao realmDao;
	private SiteDao siteDao;
	private PersonaDao personaDao;

	public JosServiceImpl() {
		this.availableLocales = Collections.unmodifiableCollection(Arrays
				.asList(Locale.getAvailableLocales()));
	}

	/**
	 * @param configuratorPassword
	 *            the configuratorPassword to set
	 */
	public void setConfiguratorPassword(String configuratorPassword) {
		String s = StringUtils
				.trimToNull(configuratorPassword);
		if ("BLANK".equals(s) || StringUtils.isBlank(s)) {
			this.configuratorPassword = null;
		} else {
			this.configuratorPassword = s;
		}
	}

	/**
	 * @param systemReservedWordPattern
	 *            the systemReservedWordPattern to set
	 */
	public void setSystemReservedWordPattern(String systemReservedWordPattern) {
		this.systemReservedWordPattern = Pattern
				.compile(systemReservedWordPattern.trim());
	}

	/**
	 * 
	 * @param domainDao
	 *            the domainDao to set
	 */
	public void setDomainDao(DomainDao domainDao) {
		this.domainDao = domainDao;
	}

	/**
	 * @param userDao
	 *            the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @param passwordDao
	 *            the passwordDao to set
	 */
	public void setPasswordDao(PasswordDao passwordDao) {
		this.passwordDao = passwordDao;
	}

	/**
	 * @param emailDao
	 *            要设置的 emailDao
	 */
	public void setEmailDao(EmailDao emailDao) {
		this.emailDao = emailDao;
	}

	/**
	 * @param emailConfirmationInfoDao
	 *            the emailConfirmationInfoDao to set
	 */
	public void setEmailConfirmationInfoDao(
			EmailConfirmationInfoDao emailConfirmationInfoDao) {
		this.emailConfirmationInfoDao = emailConfirmationInfoDao;
	}

	/**
	 * @param attributeDao
	 *            the attributeDao to set
	 */
	public void setAttributeDao(AttributeDao attributeDao) {
		this.attributeDao = attributeDao;
	}

	/**
	 * @param attributeValueDao
	 *            the attributeValueDao to set
	 */
	public void setAttributeValueDao(AttributeValueDao attributeValueDao) {
		this.attributeValueDao = attributeValueDao;
	}

	/**
	 * @param realmDao
	 *            the realmDao to set
	 */
	public void setRealmDao(RealmDao realmDao) {
		this.realmDao = realmDao;
	}

	/**
	 * @param siteDao
	 *            the siteDao to set
	 */
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}

	/**
	 * @param personaDao
	 *            the personaDao to set
	 */
	public void setPersonaDao(PersonaDao personaDao) {
		this.personaDao = personaDao;
	}

	/**
	 * 
	 * @param passwordGenerator
	 *            the passwordGenerator to set
	 */
	public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
		this.passwordGenerator = passwordGenerator;
	}

	public void setAvailableLocales(Collection<String> availableLocales) {
		this.availableLocales = new LinkedHashSet<Locale>(availableLocales
				.size());
		for (String language : availableLocales) {
			this.availableLocales.add(LocaleUtils.toLocale(language));
		}
		this.availableLocales = Collections
				.unmodifiableCollection(this.availableLocales);
	}

	public Collection<Locale> getAvailableLocales() {
		return this.availableLocales;
	}

	public boolean isSystemReservedWord(String word) {
		return this.systemReservedWordPattern.matcher(word).matches();
	}

	private synchronized ServerManager newServerManager(Domain domain) {
		ServerManager serverManager = this.serverManagers.get(domain);
		if (serverManager == null) {
			log.debug("new a serverManager for " + domain);
			serverManager = new ServerManager();
			serverManager.setOPEndpointUrl(domain.getRuntime()
					.getOpenidServerUrl().toString());
			this.serverManagers.put(domain, serverManager);
		}
		return serverManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#getServerManager(cn.net.openid.jos
	 * .domain.Domain)
	 */
	public ServerManager getServerManager(Domain domain) {
		ServerManager serverManager = this.serverManagers.get(domain);
		if (serverManager == null) {
			serverManager = newServerManager(domain);
		}
		return serverManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecn.net.openid.jos.service.JosService#parseDomain(javax.servlet.http.
	 * HttpServletRequest)
	 */
	public Domain parseDomain(HttpServletRequest request) {
		log.debug("parseDomain is called.");

		Domain domain = null;

		URL requestUrl = this.buildURLQuietly(request.getRequestURL()
				.toString());

		// e.g. www.example.com
		String host = requestUrl.getHost();
		int dotCount = StringUtils.countMatches(host, ".");
		if (dotCount >= 2) {
			int firstDot = host.indexOf(".");
			String domainNameType1 = host.substring(firstDot + 1);
			// If we split the host, it must be type subdomain.
			// And the first segement of the host is username.
			domain = this.getDomainByName(domainNameType1,
					Domain.TYPE_SUBDOMAIN);
		}

		// Find domain by whole host if domain was not found.
		if (domain == null) {
			domain = this.getDomainByName(host);
		}

		if (domain != null) {
			domain.getRuntime().setServerBaseUrl(
					DomainRuntime.buildServerBaseUrl(domain, requestUrl,
							request.getContextPath()));

			try {
				URL openidServerUrl = new URL(domain.getRuntime()
						.getServerBaseUrl(), "server");
				domain.getRuntime().setOpenidServerUrl(openidServerUrl);
			} catch (MalformedURLException e) {
				throw new IllegalArgumentException(e);
			}

		} else {
			throw new UnresolvedDomainException(host);
		}
		return domain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#parseUsername(cn.net.openid.jos.
	 * domain.Domain, javax.servlet.http.HttpServletRequest)
	 */
	public String parseUsername(Domain domain, HttpServletRequest request) {
		log.debug("parseUsername is called.");

		String username = null;
		switch (domain.getType()) {
		case Domain.TYPE_SUBDOMAIN:
			URL url = this.buildURLQuietly(request.getRequestURL().toString());
			username = parseUsernameFromSubdomain(domain.getName(), url
					.getHost());
			break;
		case Domain.TYPE_SUBDIRECTORY:
			String uri = request.getRequestURI();
			username = parseUsernameFromSubdirectory(request.getContextPath(),
					domain.getMemberPath(), uri);
			break;
		default:
			break;
		}

		if (username == null) {
			return null;
		} else {
			// Check whether it's an unallowable username.
			Pattern pattern = domain.getUsernameConfiguration()
					.getUnallowablePattern();
			return isMatches(pattern, username) ? null : username;
		}
	}

	private URL buildURLQuietly(String urlString) {
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
	private boolean isMatches(Pattern pattern, String input) {
		if (pattern == null || input == null) {
			return false;
		} else {
			return pattern.matcher(input).matches();
		}
	}

	/**
	 * Parse username from the host.
	 * 
	 * @param host
	 *            the host of the URL.
	 * @return the username, null if the length of domainName and host are equal
	 */
	private String parseUsernameFromSubdomain(String domainName, String host) {
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
	private String parseUsernameFromSubdirectory(String contextPath,
			String memberPath, String requestURI) {
		int memberPathLength = memberPath == null ? 0 : memberPath.length() + 1;
		return requestURI.substring(contextPath.length() + memberPathLength);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#getDomain(java.lang.String)
	 */
	public Domain getDomain(String id) {
		return domainDao.getDomain(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public Domain getDomainByName(String name) {
		return domainDao.getDomainByName(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#getDomainByName(java.lang.String,
	 * int)
	 */
	public Domain getDomainByName(String name, int type) {
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
	public void saveDomain(Domain domain) {
		domainDao.saveDomain(domain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#checkConfiguratorPassword(String)
	 */
	public boolean checkConfiguratorPassword(String input) {
		if (StringUtils.isBlank(this.configuratorPassword)) {
			log.debug("password is blank, login is not allowed.");
			return false;
		} else {
			return this.configuratorPassword.equals(input);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getUser(java.lang.String)
	 */
	public User getUser(String id) {
		return userDao.getUser(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#getUser(cn.net.openid.jos.domain
	 * .Domain, java.lang.String)
	 */
	public User getUser(Domain domain, String username) {
		User user = userDao.getUser(domain, username);
		if (user != null) {
			// Set domain with the runtime domain.
			user.setDomain(domain);
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#login(cn.net.openid.jos.domain
	 * .Domain, java.lang.String, java.lang.String)
	 */
	public User login(Domain domain, String username, String passwordPlaintext) {
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
			if (password.isUseful()
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#insertUser(cn.net.openid.User,
	 * cn.net.openid.domain.Password)
	 */
	public void insertUser(User user, Password password) {
		userDao.insertUser(user);
		passwordDao.insertPassword(password);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#updatePassword(cn.net.openid.jos
	 * .domain.User, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updatePassword(User user, String passwordId, String name,
			String passwordPlaintext) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#deletePasswords(cn.net.openid.jos
	 * .domain.User, java.lang.String[])
	 */
	public void deletePasswords(User user, String[] passwordIds)
			throws LastPasswordException {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#generateOneTimePassword(cn.net
	 * .openid .jos.domain.User, cn.net.openid.jos.domain.Email)
	 */
	public Password generateOneTimePassword(User user, Email email) {
		Password ret = null;
		Collection<Password> passwords = this.getPasswords(user);
		for (Password password : passwords) {
			if (password.isUseful()
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
					.generate(16, 32));
			ret.setPlaintext(passwordPlaintext);
			ret.setShaHex(DigestUtils.shaHex(ret.getPlaintext()));

			passwordDao.insertPassword(ret);
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#deleteEmail(cn.net.openid.jos.domain
	 * .User, java.lang.String)
	 */
	public void deleteEmail(User user, String id) {
		Email email = emailDao.getEmail(id);
		if (email != null && email.getUser().equals(user)) {
			emailDao.deleteEmail(id);
		} else {
			throw new NoPermissionException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#setPrimaryEmail(cn.net.openid.jos
	 * .domain.User, java.lang.String)
	 */
	public void setPrimaryEmail(User user, String id) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#getEmail(cn.net.openid.jos.domain
	 * .User, java.lang.String)
	 */
	public Email getEmail(User user, String id) {
		Email email = emailDao.getEmail(id);
		return (email != null && email.getUser().equals(user)) ? email : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#getEmails(cn.net.openid.jos.domain
	 * .User)
	 */
	public Collection<Email> getEmails(User user) {
		return emailDao.getEmails(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#insertEmail(cn.net.openid.jos.domain
	 * .User, cn.net.openid.jos.domain.Email)
	 */
	public void insertEmail(User user, Email email) {
		if (user.equals(email.getUser())) {
			emailDao.insertEmail(email);
		} else {
			throw new NoPermissionException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#generateConfirmationCode(cn.net.
	 * openid.jos.domain.Email)
	 */
	public String generateConfirmationCode(Email email) {
		StringBuilder seed = new StringBuilder();
		seed.append(email.getUser().getId());
		seed.append(email.getUser().getUsername());
		seed.append(email.getUser().getCreationDate());
		seed.append(email.getAddress());
		seed.append(RandomStringUtils.randomAlphanumeric(40));
		seed.append(System.currentTimeMillis());
		seed.append(System.nanoTime());
		return DigestUtils.shaHex(seed.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#getEmailConfirmationInfo(java.lang
	 * .String)
	 */
	public EmailConfirmationInfo getEmailConfirmationInfo(
			String confirmationCode) {
		return emailConfirmationInfoDao
				.getEmailConfirmationInfo(confirmationCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#insertEmailConfirmationInfo(cn.net
	 * .openid.jos.domain.User, cn.net.openid.jos.domain.EmailConfirmationInfo)
	 */
	public void insertEmailConfirmationInfo(User user,
			EmailConfirmationInfo emailConfirmationInfo) {
		if (user.equals(emailConfirmationInfo.getEmail().getUser())) {
			emailConfirmationInfoDao
					.insertEmailConfirmationInfo(emailConfirmationInfo);
		} else {
			throw new NoPermissionException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#updateEmailConfirmationInfo(cn.net
	 * .openid.jos.domain.User, cn.net.openid.jos.domain.EmailConfirmationInfo)
	 */
	public void updateEmailConfirmationInfo(User user,
			EmailConfirmationInfo emailConfirmationInfo) {
		if (user.equals(emailConfirmationInfo.getEmail().getUser())) {
			emailConfirmationInfoDao
					.updateEmailConfirmationInfo(emailConfirmationInfo);
		} else {
			throw new NoPermissionException();
		}
	}

	public void confirmEmail(String confirmationCode)
			throws EmailConfirmationInfoNotFoundException {
		EmailConfirmationInfo eci = emailConfirmationInfoDao
				.getEmailConfirmationInfo(confirmationCode);
		if (log.isDebugEnabled()) {
			log.debug("email confirmation info: " + eci);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#getPasswords(cn.net.openid.jos.domain
	 * .User)
	 */
	public Collection<Password> getPasswords(User user) {
		return passwordDao.getPasswords(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#getPassword(cn.net.openid.jos.domain
	 * .User, java.lang.String)
	 */
	public Password getPassword(User user, String passwordId) {
		Password password = passwordDao.getPassword(passwordId);
		return (password != null && password.getUser().equals(user)) ? password
				: null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getAttribute(java.lang.String)
	 */
	public Attribute getAttribute(String id) {
		return attributeDao.getAttribute(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getAttributes()
	 */
	public Collection<Attribute> getAttributes() {
		return attributeDao.getAttributes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.dao.DaoFacade#saveAttribute(cn.net.openid.domain.Attribute)
	 */
	public void saveAttribute(Attribute attribute) {
		attributeDao.saveAttribute(attribute);
	}

	public void deleteAttribute(String id) {
		attributeDao.deleteAttribute(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#getUserAttributeValues(cn.net.openid
	 * .jos.domain.User)
	 */
	public Collection<AttributeValue> getUserAttributeValues(User user) {
		return attributeValueDao.getUserAttributeValues(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#saveAttributeValues(cn.net.openid
	 * .jos.domain.User, java.util.Collection)
	 */
	public void saveAttributeValues(User user,
			Collection<AttributeValue> attributeValues) {
		for (AttributeValue attributeValue : attributeValues) {
			if (user.equals(attributeValue.getUser())) {
				attributeValueDao.saveAttributeValue(attributeValue);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#isAlwaysApprove(cn.net.openid.jos
	 * .domain.User, java.lang.String)
	 */
	public boolean isAlwaysApprove(User user, String realmUrl) {
		Site site = siteDao.getSite(user, realmUrl);
		return site == null ? false : site.isAlwaysApprove();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#updateApproval(cn.net.openid.jos
	 * .domain.User, java.lang.String)
	 */
	public void updateApproval(User user, String realmUrl) {
		Site site = siteDao.getSite(user, realmUrl);
		site.setApprovals(site.getApprovals() + 1);
		siteDao.updateSite(site);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#allow(cn.net.openid.jos.domain.User,
	 * java.lang.String, cn.net.openid.jos.domain.Persona, boolean)
	 */
	public void allow(User user, String realmUrl, Persona persona,
			boolean forever) {
		if (log.isDebugEnabled()) {
			log.debug("user: " + user);
			log.debug("realmUrl: " + realmUrl);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#getSites(cn.net.openid.jos.domain
	 * .User)
	 */
	public Collection<Site> getSites(User user) {
		return siteDao.getSites(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#getTopSites(cn.net.openid.jos.domain
	 * .User, int)
	 */
	public Collection<Site> getTopSites(User user, int maxResults) {
		return siteDao.getTopSites(user, maxResults);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#getLatestSites(cn.net.openid.jos
	 * .domain.User, int)
	 */
	public Collection<Site> getLatestSites(User user, int maxResults) {
		return siteDao.getLatestSites(user, maxResults);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#getRecentRealms(int)
	 */
	public Collection<Realm> getLatestRealms(int maxResults) {
		return realmDao.getLatestRealms(maxResults);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#getSite(cn.net.openid.jos.domain
	 * .User, java.lang.String)
	 */
	public Site getSite(User user, String realmUrl) {
		return siteDao.getSite(user, realmUrl);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#updateAlwaysApprove(cn.net.openid
	 * .jos.domain.User, java.lang.String, boolean)
	 */
	public void updateAlwaysApprove(User user, String realmId,
			boolean alwaysApprove) {
		siteDao.updateAlwaysApprove(user, realmId, alwaysApprove);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#getPersona(cn.net.openid.jos.domain
	 * .User, java.lang.String)
	 */
	public Persona getPersona(User user, String id) {
		Persona persona = personaDao.getPersona(id);
		return (persona != null && persona.getUser().equals(user)) ? persona
				: null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#getPersonas(cn.net.openid.jos.domain
	 * .User)
	 */
	public Collection<Persona> getPersonas(User user) {
		return personaDao.getPersonas(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#insertPersona(cn.net.openid.jos.
	 * domain.User, cn.net.openid.jos.domain.Persona)
	 */
	public void insertPersona(User user, Persona persona) {
		if (user.equals(persona.getUser())) {
			personaDao.insertPersona(persona);
		} else {
			throw new NoPermissionException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#updatePersona(cn.net.openid.jos.
	 * domain.User, cn.net.openid.jos.domain.Persona)
	 */
	public void updatePersona(User user, Persona persona) {
		if (user.equals(persona.getUser())) {
			personaDao.updatePersona(persona);
		} else {
			throw new NoPermissionException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.net.openid.jos.service.JosService#deletePersonas(cn.net.openid.jos
	 * .domain.User, java.lang.String[])
	 */
	public void deletePersonas(User user, String[] personaIds)
			throws PersonaInUseException {
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
