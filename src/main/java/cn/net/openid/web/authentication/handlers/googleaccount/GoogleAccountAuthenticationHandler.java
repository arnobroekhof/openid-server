/**
 * Created on 2006-10-22 下午07:02:17
 */
package cn.net.openid.web.authentication.handlers.googleaccount;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.net.openid.Credential;
import cn.net.openid.web.WebUtils;
import cn.net.openid.web.authentication.AuthenticationHandler;

/**
 * @author Shutra
 * 
 */
public class GoogleAccountAuthenticationHandler implements
		AuthenticationHandler {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(GoogleAccountAuthenticationHandler.class);

	public static String USERNAME_SESSION = GoogleAccountAuthenticationHandler.class
			.getPackage().getName()
			+ "."
			+ GoogleAccountAuthenticationHandler.class.getName()
			+ ".username";

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.web.authentication.AuthenticationHandler#describe(cn.net.openid.Credential)
	 */
	public String describe(Credential credential) {
		try {
			return new String(credential.getInfo(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.web.authentication.AuthenticationHandler#gatherInfo(java.lang.String,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public Credential gatherInfo(String username, HttpServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.web.authentication.AuthenticationHandler#showEditForm(java.lang.String,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void showEditForm(HttpServletRequest req, HttpServletResponse resp,
			String credentialId) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.setAttribute("action", "edit");

		if (credentialId != null) {
			session.setAttribute("id", credentialId);
		}
		resp.sendRedirect(resp.encodeRedirectURL(WebUtils.getContextPath(req)
				+ "/google-account.login"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.web.authentication.AuthenticationHandler#showForm(java.lang.String,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void showForm(HttpServletRequest req, HttpServletResponse resp,
			String username) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.removeAttribute("action");
		session.removeAttribute("id");
		session.setAttribute(USERNAME_SESSION, username);
		resp.sendRedirect(resp.encodeRedirectURL(WebUtils.getContextPath(req)
				+ "/google-account.login"));
	}

}
