/**
 * Created on 2006-10-28 下午09:55:36
 */
package cn.net.openid.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cn.net.openid.Credential;
import cn.net.openid.dao.DaoFacade;

/**
 * @author Shutra
 * 
 */
public class CredentialsController implements Controller {
	private DaoFacade daoFacade;

	/**
	 * @param daoFacade
	 *            the daoFacade to set
	 */
	public void setDaoFacade(DaoFacade daoFacade) {
		this.daoFacade = daoFacade;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		List<Credential> credentials = daoFacade.getCredentials(userSession
				.getUserId());
		request.setAttribute("credentials", credentials);
		return new ModelAndView("credentials");
	}

}
