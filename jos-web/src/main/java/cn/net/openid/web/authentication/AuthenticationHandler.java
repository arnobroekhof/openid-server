/**
 * Created on 2006-10-22 18:54:22
 */
package cn.net.openid.web.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.net.openid.Credential;

/**
 * @author Shutra
 * 
 */
public interface AuthenticationHandler {
	String describe(Credential credential);

	Credential gatherInfo(String username, HttpServletRequest req);

	void showEditForm(HttpServletRequest req, HttpServletResponse resp,
			String credentialId) throws ServletException, IOException;

	void showForm(HttpServletRequest req, HttpServletResponse resp,
			String username) throws ServletException, IOException;
}
