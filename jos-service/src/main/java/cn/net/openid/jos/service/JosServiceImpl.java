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
		return String.format("%1$s%2$s%3$s", this.josConfiguration
				.getIdentifierPrefix(), username, this.josConfiguration
				.getIdentifierSuffix());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getUser(java.lang.String)
	 */
	public User getUser(String id) {
		return this.userDao.getUser(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getUserByUsername(java.lang.String)
	 */
	public User getUserByUsername(String username) {
		return this.userDao.getUserByUsername(username);
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

		Collection<Password> passwords = getPasswords(user.getId());
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
		this.userDao.insertUser(user);
		this.passwordDao.insertPassword(password);

		// Credential credential = new Credential();
		// credential.setUser(user);
		// CredentialHandler credentialHandler = this.getCredentialHandler("1");
		// if (credentialHandler == null) {
		// throw new RuntimeException("没有找到密码凭据类型。");
		// }
		// credential.setHandler(credentialHandler);
		// try {
		// credential.setInfo(password.getPassword().getBytes("UTF-8"));
		// } catch (UnsupportedEncodingException e) {
		// throw new RuntimeException(e);
		// }
		//
		// this.credentialDao.insertCredential(credential);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#updateUser(cn.net.openid.User)
	 */
	public void updateUser(User user) {
		this.userDao.updateUser(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#updatePassword(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updatePassword(String userId, String passwordId, String name,
			String passwordPlaintext) {
		Password password = this.getPassword(userId, passwordId);
		boolean insert = false;
		if (password == null) {
			password = new Password(this.getUser(userId));
			insert = true;
		}

		password.setName(name);
		password.setPlaintext(passwordPlaintext);
		password.setShaHex(DigestUtils.shaHex(password.getPlaintext()));

		if (insert) {
			this.passwordDao.insertPassword(password);
		} else {
			this.passwordDao.updatePassword(password);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#deletePasswords(java.lang.String,
	 *      java.lang.String[])
	 */
	public void deletePasswords(String userId, String[] passwordIds)
			throws LastPasswordException {
		User user = userDao.getUser(userId);
		for (String passwordId : passwordIds) {
			Password password = passwordDao.getPassword(passwordId);
			if (password.getUser().equals(user)) {
				passwordDao.deletePassword(password.getId());
			}
		}
		if (passwordDao.getPasswords(user.getId()).size() == 0) {
			throw new LastPasswordException();
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.DaoFacade#deleteEmail(java.lang.String)
	 */
	public void deleteEmail(String id) {
		this.emailDao.deleteEmail(id);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getEmail(java.lang.String)
	 */
	public Email getEmail(String id) {
		return this.emailDao.getEmail(id);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getEmailsByUserId(java.lang.String)
	 */
	public Collection<Email> getEmailsByUserId(String userId) {
		return this.emailDao.getEmailsByUserId(userId);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.DaoFacade#insertEmail(cn.net.openid.domain.Email)
	 */
	public void insertEmail(Email email) {
		this.emailDao.insertEmail(email);
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
		return this.emailConfirmationInfoDao
				.getEmailConfirmationInfo(confirmationCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#insertEmailConfirmationInfo(cn.net.openid.jos.domain.EmailConfirmationInfo)
	 */
	public void insertEmailConfirmationInfo(
			EmailConfirmationInfo emailConfirmationInfo) {
		this.emailConfirmationInfoDao
				.insertEmailConfirmationInfo(emailConfirmationInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#updateEmailConfirmationInfo(cn.net.openid.jos.domain.EmailConfirmationInfo)
	 */
	public void updateEmailConfirmationInfo(
			EmailConfirmationInfo emailConfirmationInfo) {
		this.emailConfirmationInfoDao
				.updateEmailConfirmationInfo(emailConfirmationInfo);
	}

	public void confirmEmail(String confirmationCode)
			throws EmailConfirmationInfoNotFoundException {
		EmailConfirmationInfo eci = this.emailConfirmationInfoDao
				.getEmailConfirmationInfo(confirmationCode);
		log.debug("email confirmation info: " + eci);
		if (eci == null || !eci.isSent() || eci.isConfirmed()) {
			throw new EmailConfirmationInfoNotFoundException();
		}

		eci.getEmail().setConfirmed(true);
		eci.setConfirmed(true);
		eci.setConfirmedDate(new Date());
		this.emailConfirmationInfoDao.updateEmailConfirmationInfo(eci);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#getPasswords(java.lang.String)
	 */
	public Collection<Password> getPasswords(String userId) {
		return this.passwordDao.getPasswords(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#getPassword(java.lang.String,
	 *      java.lang.String)
	 */
	public Password getPassword(String userId, String passwordId) {
		Password password = this.passwordDao.getPassword(passwordId);
		if (password != null && !password.getUser().getId().equals(userId)) {
			password = null;
		}
		return password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getAttribute(java.lang.String)
	 */
	public Attribute getAttribute(String id) {
		return this.attributeDao.getAttribute(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getAttributes()
	 */
	public Collection<Attribute> getAttributes() {
		return this.attributeDao.getAttributes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#saveAttribute(cn.net.openid.domain.Attribute)
	 */
	public void saveAttribute(Attribute attribute) {
		this.attributeDao.saveAttribute(attribute);
	}

	public void deleteAttribute(String id) {
		this.attributeDao.deleteAttribute(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getUserAttributeValues(java.lang.String)
	 */
	public List<AttributeValue> getUserAttributeValues(String userId) {
		return this.attributeValueDao.getUserAttributeValues(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#saveAttributeValues(java.util.Collection)
	 */
	public void saveAttributeValues(Collection<AttributeValue> attributeValues) {
		for (AttributeValue attributeValue : attributeValues) {
			this.attributeValueDao.saveAttributeValue(attributeValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getJosConfiguration()
	 */
	public JosConfiguration getJosConfiguration() {
		return this.josConfiguration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#isAlwaysApprove(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean isAlwaysApprove(String userId, String realmUrl) {
		Site site = this.siteDao.getSite(userId, realmUrl);
		return site == null ? false : site.isAlwaysApprove();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#updateApproval(java.lang.String,
	 *      java.lang.String)
	 */
	public void updateApproval(String userId, String realmUrl) {
		Site site = this.siteDao.getSite(userId, realmUrl);
		site.setApprovals(site.getApprovals() + 1);
		this.siteDao.updateSite(site);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#allow(java.lang.String,
	 *      java.lang.String, cn.net.openid.jos.domain.Persona, boolean)
	 */
	public void allow(String userId, String realmUrl, Persona persona,
			boolean forever) {
		if (log.isDebugEnabled()) {
			log.debug("userId: " + userId);
			log.debug("realmUrl: " + realmUrl);
		}
		Site site = this.siteDao.getSite(userId, realmUrl);
		if (site == null) {
			Realm realm = this.realmDao.getRealmByUrl(realmUrl);
			if (realm == null) {
				realm = new Realm();
				realm.setUrl(realmUrl);
				this.realmDao.insertRealm(realm);
			}
			site = new Site();
			site.setUser(this.userDao.getUser(userId));
			site.setRealm(realm);
			site.setLastAttempt(new Date());
			site.setApprovals(1);
			site.setAlwaysApprove(forever);
			site.setPersona(persona);

			this.siteDao.insertSite(site);
		} else {
			site.setAlwaysApprove(forever);
			site.setApprovals(site.getApprovals() + 1);
			site.setPersona(persona);
			this.siteDao.updateSite(site);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#getSites(java.lang.String)
	 */
	public List<Site> getSites(String userId) {
		return this.siteDao.getSites(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#getSite(java.lang.String,
	 *      java.lang.String)
	 */
	public Site getSite(String userId, String realmUrl) {
		return this.siteDao.getSite(userId, realmUrl);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#updateAlwaysApprove(java.lang.String,
	 *      java.lang.String, boolean)
	 */
	public void updateAlwaysApprove(String userId, String realmId,
			boolean alwaysApprove) {
		this.siteDao.updateAlwaysApprove(userId, realmId, alwaysApprove);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#getPersona(java.lang,String,
	 *      java.lang.String)
	 */
	public Persona getPersona(String userId, String id) {
		Persona persona = this.personaDao.getPersona(id);
		if (persona != null && persona.getUser().getId().equals(userId)) {
			return persona;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#getDefaultPersona(java.lang.String)
	 */
	public Persona getDefaultPersona(String userId) {
		Collection<Persona> personas = this.personaDao.getPersonas(userId);
		Persona persona = null;
		if (!personas.isEmpty()) {
			persona = personas.iterator().next();
		}
		return persona;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#getPersonas(java.lang.String)
	 */
	public Collection<Persona> getPersonas(String userId) {
		return this.personaDao.getPersonas(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#insertPersona(cn.net.openid.jos.domain.Persona)
	 */
	public void insertPersona(Persona persona) {
		this.personaDao.insertPersona(persona);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#updatePersona(cn.net.openid.jos.domain.Persona)
	 */
	public void updatePersona(Persona persona) {
		this.personaDao.updatePersona(persona);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.service.JosService#deletePersonas(java.lang.String,
	 *      java.lang.String[])
	 */
	public void deletePersonas(String userId, String[] personaIds) {
		for (String personaId : personaIds) {
			Persona persona = this.getPersona(userId, personaId);
			if (persona != null) {
				this.personaDao.deletePersona(persona);
			}
		}
	}
}
