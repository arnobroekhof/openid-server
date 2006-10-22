/**
 * Created on 2006-10-22 下午07:22:43
 */
package cn.net.openid.web.authentication.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.net.openid.Credential;
import cn.net.openid.web.authentication.AuthenticationHandler;

/**
 * @author Shutra
 * 
 */
public class PasswordAuthenticationHandler implements AuthenticationHandler {

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
		resp.sendRedirect("/authentication/handlers/password");
	}

}
