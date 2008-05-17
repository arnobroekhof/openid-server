/**
 * Created on 2006-10-16 上午12:39:17
 */
package cn.net.openid.dao;

import java.util.Collection;
import java.util.List;

import cn.net.openid.domain.Attribute;
import cn.net.openid.domain.AttributeValue;
import cn.net.openid.domain.Email;
import cn.net.openid.domain.JosConfiguration;
import cn.net.openid.domain.Password;
import cn.net.openid.domain.User;

/**
 * @author Sutra Zhou
 * 
 */
public class DaoFacadeImpl implements DaoFacade {
	private JosConfiguration josConfiguration;

	private UserDao userDao;
	private PasswordDao passwordDao;
	private EmailDao emailDao;
	private AttributeDao attributeDao;
	private AttributeValueDao attributeValueDao;

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
	 * （非 Javadoc）
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getPasswordByUserId(java.lang.String)
	 */
	public Password getPasswordByUserId(String userId) {
		return this.passwordDao.getPasswordByUserId(userId);
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
}
