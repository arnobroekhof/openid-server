/**
 * Created on 2006-10-22 下午07:22:43
 */
package cn.net.openid.web.authentication.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.net.openid.Credential;
import cn.net.openid.web.WebUtils;
import cn.net.openid.web.authentication.AuthenticationHandler;

/**
 * @author Shutra
 * 
 */
public class PasswordAuthenticationHandler implements AuthenticationHandler {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(PasswordAuthenticationHandler.class);

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
	 * @see cn.net.openid.web.authentication.AuthenticationHandler#showForm(java.lang.String,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void showForm(String username, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(resp.encodeRedirectURL(WebUtils.getContextPath(req)
				+ "/login?username=" + username));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.web.authentication.AuthenticationHandler#describe(cn.net.openid.Credential)
	 */
	public String describe(Credential credential) {
		return "******";
	}

}
