/**
 * Created on 2006-10-22 下午07:02:17
 */
package cn.net.openid.web.authentication.handlers.googleaccount;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.net.openid.Credential;
import cn.net.openid.web.WebUtils;
import cn.net.openid.web.authentication.AuthenticationHandler;

/**
 * @author Shutra
 * 
 */
public class GoogleAccountAuthenticationHandler implements
		AuthenticationHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.web.authentication.AuthenticationHandler#gatherInfo(javax.servlet.http.HttpServletRequest)
	 */
	public Credential gatherInfo(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.web.authentication.AuthenticationHandler#showForm(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void showForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.sendRedirect(resp.encodeRedirectURL(WebUtils.getContextPath(req)
				+ "/google-account.login"));
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
	 * @see cn.net.openid.web.authentication.AuthenticationHandler#showForm(java.lang.String,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void showForm(String username, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
