/**
 * Created on 2006-10-16 上午12:39:17
 */
package cn.net.openid.jos.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.net.openid.jos.dao.AttributeDao;
import cn.net.openid.jos.dao.AttributeValueDao;
import cn.net.openid.jos.dao.EmailConfirmationInfoDao;
import cn.net.openid.jos.dao.EmailDao;
import cn.net.openid.jos.dao.PasswordDao;
import cn.net.openid.jos.dao.PersonaDao;
import cn.net.openid.jos.dao.RealmDao;
import cn.net.openid.jos.dao.SiteDao;
import cn.net.openid.jos.dao.UserDao;
import cn.net.openid.jos.domain.Attribute;
import cn.net.openid.jos.domain.AttributeValue;
import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.domain.EmailConfirmationInfo;
import cn.net.openid.jos.domain.JosConfiguration;
import cn.net.openid.jos.domain.Password;
import cn.net.openid.jos.domain.Persona;
import cn.net.openid.jos.domain.Realm;
import cn.net.openid.jos.domain.Site;
import cn.net.openid.jos.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public class JosServiceImpl implements JosService {
	private static final Log log = LogFactory.getLog(JosServiceImpl.class);

	private JosConfiguration josConfiguration;

	private UserDao userDao;
	private PasswordDao passwordDao;
	private EmailDao emailDao;
	private EmailConfirmationInfoDao emailConfirmationInfoDao;
	private AttributeDao attributeDao;
	private AttributeValueDao attributeValueDao;
	private RealmDao realmDao;
	private SiteDao siteDao;
	private PersonaDao personaDao;

	/**
	 * @param josConfiguration
	 *            the josConfiguration to set
	 */
	public void setJosConfiguration(JosConfiguration josConfiguration) {
		this.josConfiguration = josConfiguration;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#buildOpenidUrl(java.lang.String)
	 */
	public String buildOpenidUrl(String username) {
		return String.format("%1$s%2$s%3$s", josConfiguration
				.getIdentifierPrefix(), username, josConfiguration
				.getIdentifierSuffix());
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
	 * @see cn.net.openid.dao.DaoFacade#getUserByUsername(java.lang.String)
	 */
	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#getUser(java.lang.String,
	 *      java.lang.String)
	 */
	public User getUser(String username, String passwordPlaintext) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		User user = getUserByUsername(username);
		if (user == null) {
			return null;
		}

		Collection<Password> passwords = getPasswords(user);
		boolean foundPassword = false;
		String passwordShaHex = DigestUtils.shaHex(passwordPlaintext);
		for (Password password : passwords) {
			if (password.getShaHex().equalsIgnoreCase(passwordShaHex)) {
				foundPassword = true;
				break;
			}
		}
		return foundPassword ? user : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#insertUser(cn.net.openid.User,
	 *      cn.net.openid.domain.Password)
	 */
	public void insertUser(User user, Password password) {
		userDao.insertUser(user);
		passwordDao.insertPassword(password);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#updatePassword(cn.net.openid.jos.domain.User,
	 *      java.lang.String, java.lang.String, java.lang.String)
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
	 * @see cn.net.openid.jos.service.JosService#deletePasswords(cn.net.openid.jos.domain.User,
	 *      java.lang.String[])
	 */
	public void deletePasswords(User user, String[] passwordIds)
			throws LastPasswordException {
		for (String passwordId : passwordIds) {
			Password password = passwordDao.getPassword(passwordId);
			if (password.getUser().equals(user)) {
				passwordDao.deletePassword(password.getId());
			}
		}
		if (passwordDao.getPasswords(user).size() == 0) {
			throw new LastPasswordException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#deleteEmail(cn.net.openid.jos.domain.User,
	 *      java.lang.String)
	 */
	public void deleteEmail(User user, String id) {
		Email email = emailDao.getEmail(id);
		if (email != null && email.getUser().equals(user)) {
			emailDao.deleteEmail(id);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#setPrimaryEmail(cn.net.openid.jos.domain.User,
	 *      java.lang.String)
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
	 * @see cn.net.openid.jos.service.JosService#getEmail(cn.net.openid.jos.domain.User,
	 *      java.lang.String)
	 */
	public Email getEmail(User user, String id) {
		Email email = emailDao.getEmail(id);
		return (email != null && email.getUser().equals(user)) ? email : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#getEmails(cn.net.openid.jos.domain.User)
	 */
	public Collection<Email> getEmails(User user) {
		return emailDao.getEmails(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#insertEmail(cn.net.openid.jos.domain.User,
	 *      cn.net.openid.jos.domain.Email)
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
	 * @see cn.net.openid.jos.service.JosService#generateConfirmationCode(cn.net.openid.jos.domain.Email)
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
	 * @see cn.net.openid.jos.service.JosService#getEmailConfirmationInfo(java.lang.String)
	 */
	public EmailConfirmationInfo getEmailConfirmationInfo(
			String confirmationCode) {
		return emailConfirmationInfoDao
				.getEmailConfirmationInfo(confirmationCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#insertEmailConfirmationInfo(cn.net.openid.jos.domain.User,
	 *      cn.net.openid.jos.domain.EmailConfirmationInfo)
	 */
	public void insertEmailConfirmationInfo(User user,
			EmailConfirmationInfo emailConfirmationInfo) {
		if (user.equals(emailConfirmationInfo.getEmail().getUser())) {
			emailConfirmationInfoDao
					.insertEmailConfirmationInfo(emailConfirmationInfo);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#updateEmailConfirmationInfo(cn.net.openid.jos.domain.User,
	 *      cn.net.openid.jos.domain.EmailConfirmationInfo)
	 */
	public void updateEmailConfirmationInfo(User user,
			EmailConfirmationInfo emailConfirmationInfo) {
		if (user.equals(emailConfirmationInfo.getEmail().getUser())) {
			emailConfirmationInfoDao
					.updateEmailConfirmationInfo(emailConfirmationInfo);
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
	 * @see cn.net.openid.jos.service.JosService#getPasswords(cn.net.openid.jos.domain.User)
	 */
	public Collection<Password> getPasswords(User user) {
		return passwordDao.getPasswords(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#getPassword(cn.net.openid.jos.domain.User,
	 *      java.lang.String)
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
	 * @see cn.net.openid.dao.DaoFacade#saveAttribute(cn.net.openid.domain.Attribute)
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
	 * @see cn.net.openid.jos.service.JosService#getUserAttributeValues(cn.net.openid.jos.domain.User)
	 */
	public Collection<AttributeValue> getUserAttributeValues(User user) {
		return attributeValueDao.getUserAttributeValues(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#saveAttributeValues(cn.net.openid.jos.domain.User,
	 *      java.util.Collection)
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
	 * @see cn.net.openid.dao.DaoFacade#getJosConfiguration()
	 */
	public JosConfiguration getJosConfiguration() {
		return josConfiguration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#isAlwaysApprove(cn.net.openid.jos.domain.User,
	 *      java.lang.String)
	 */
	public boolean isAlwaysApprove(User user, String realmUrl) {
		Site site = siteDao.getSite(user, realmUrl);
		return site == null ? false : site.isAlwaysApprove();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#updateApproval(cn.net.openid.jos.domain.User,
	 *      java.lang.String)
	 */
	public void updateApproval(User user, String realmUrl) {
		Site site = siteDao.getSite(user, realmUrl);
		site.setApprovals(site.getApprovals() + 1);
		siteDao.updateSite(site);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#allow(cn.net.openid.jos.domain.User,
	 *      java.lang.String, cn.net.openid.jos.domain.Persona, boolean)
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
	 * @see cn.net.openid.jos.service.JosService#getSites(cn.net.openid.jos.domain.User)
	 */
	public List<Site> getSites(User user) {
		return siteDao.getSites(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#getSite(cn.net.openid.jos.domain.User,
	 *      java.lang.String)
	 */
	public Site getSite(User user, String realmUrl) {
		return siteDao.getSite(user, realmUrl);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#updateAlwaysApprove(cn.net.openid.jos.domain.User,
	 *      java.lang.String, boolean)
	 */
	public void updateAlwaysApprove(User user, String realmId,
			boolean alwaysApprove) {
		siteDao.updateAlwaysApprove(user, realmId, alwaysApprove);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#getPersona(cn.net.openid.jos.domain.User,
	 *      java.lang.String)
	 */
	public Persona getPersona(User user, String id) {
		Persona persona = personaDao.getPersona(id);
		return (persona != null && persona.getUser().equals(user)) ? persona
				: null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#getPersonas(cn.net.openid.jos.domain.User)
	 */
	public Collection<Persona> getPersonas(User user) {
		return personaDao.getPersonas(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#insertPersona(cn.net.openid.jos.domain.User,
	 *      cn.net.openid.jos.domain.Persona)
	 */
	public void insertPersona(User user, Persona persona) {
		if (user.equals(persona.getUser())) {
			personaDao.insertPersona(persona);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#updatePersona(cn.net.openid.jos.domain.User,
	 *      cn.net.openid.jos.domain.Persona)
	 */
	public void updatePersona(User user, Persona persona) {
		if (user.equals(persona.getUser())) {
			personaDao.updatePersona(persona);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#deletePersonas(cn.net.openid.jos.domain.User,
	 *      java.lang.String[])
	 */
	public void deletePersonas(User user, String[] personaIds) {
		for (String personaId : personaIds) {
			Persona persona = getPersona(user, personaId);
			if (persona != null) {
				personaDao.deletePersona(persona);
			}
		}
	}
}
