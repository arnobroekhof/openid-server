/**
 * Created on 2006-10-16 上午12:39:17
 */
package cn.net.openid.dao;

import java.util.Collection;
import java.util.List;

import cn.net.openid.Credential;
import cn.net.openid.CredentialException;
import cn.net.openid.CredentialHandler;
import cn.net.openid.OpenIdConfiguration;
import cn.net.openid.domain.Attribute;
import cn.net.openid.domain.AttributeValue;
import cn.net.openid.domain.Email;
import cn.net.openid.domain.Password;
import cn.net.openid.domain.User;

/**
 * @author Shutra
 * 
 */
public class DaoFacadeImpl implements DaoFacade {
	private OpenIdConfiguration openIdConfiguration;
	private UserDao userDao;
	private PasswordDao passwordDao;
	private EmailDao emailDao;
	private CredentialDao credentialDao;
	private CredentialHandlerDao credentialHandlerDao;
	private AttributeDao attributeDao;
	private AttributeValueDao attributeValueDao;

	/**
	 * @param credentialDao
	 *            the credentialDao to set
	 */
	public void setCredentialDao(CredentialDao credentialDao) {
		this.credentialDao = credentialDao;
	}

	public void setCredentialHandlerDao(
			CredentialHandlerDao credentialHandlerDao) {
		this.credentialHandlerDao = credentialHandlerDao;
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

	/**
	 * @param openIdConfiguration
	 *            the openIdConfiguration to set
	 */
	public void setOpenIdConfiguration(OpenIdConfiguration openIdConfiguration) {
		this.openIdConfiguration = openIdConfiguration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#buildOpenidUrl(java.lang.String)
	 */
	public String buildOpenidUrl(String username) {
		return String.format("%1$s%2$s%3$s", this.openIdConfiguration
				.getOpenIdUrlPrefix(), username, this.openIdConfiguration
				.getOpenIdUrlSuffix());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#deleteCredential(cn.net.openid.Credential)
	 */
	public void deleteCredential(Credential credential)
			throws CredentialException {
		if (this.credentialDao.countCredentials(credential.getUser().getId()) == 1) {
			throw new CredentialException();
		}
		this.credentialDao.deleteCredential(credential);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getCredential(java.lang.String)
	 */
	public Credential getCredential(String id) {
		return this.credentialDao.getCredential(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getCredentialHandler(java.lang.String)
	 */
	public CredentialHandler getCredentialHandler(String id) {
		return this.credentialHandlerDao.getCredentialHandler(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getCredentialHandlers()
	 */
	public List<CredentialHandler> getCredentialHandlers() {
		return this.credentialHandlerDao.getCredentialHandlers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getCredentiasl(java.lang.String)
	 */
	public List<Credential> getCredentials(String userId) {
		return this.credentialDao.getCredentials(userId);
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
	 * @see cn.net.openid.dao.DaoFacade#saveCredential(cn.net.openid.Credential)
	 */
	public String insertCredential(Credential credential) {
		return this.credentialDao.insertCredential(credential);
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
	 * @see cn.net.openid.dao.DaoFacade#updateCredential(cn.net.openid.Credential)
	 */
	public void updateCredential(Credential credential) {
		this.credentialDao.updateCredential(credential);
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
	 * @see cn.net.openid.dao.DaoFacade#saveAttribute(cn.net.openid.domain.Attribute)
	 */
	public void saveAttribute(Attribute attribute) {
		this.attributeDao.saveAttribute(attribute);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.dao.DaoFacade#getUserAttributeValues(java.lang.String)
	 */
	public List<AttributeValue> getUserAttributeValues(String userId) {
		return this.attributeValueDao.getUserAttributeValues(userId);
	}

}
