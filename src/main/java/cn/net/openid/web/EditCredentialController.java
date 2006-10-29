/**
 * Created on 2006-10-29 上午03:53:17
 */
package cn.net.openid.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cn.net.openid.Credential;
import cn.net.openid.dao.DaoFacade;

/**
 * @author Shutra
 * 
 */
public class EditCredentialController implements Controller {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1312827625002644956L;

	private DaoFacade daoFacade;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String credentialHandlerId = request
				.getParameter("credentialHandlerId");

		if (!StringUtils.isEmpty(id)) {
			Credential credential = daoFacade.getCredential(id);
			credential.getAuthenticationHandler().showEditForm(
					request, response,
					credential == null ? null : credential.getId());
			return null;
		} else if (!StringUtils.isEmpty(credentialHandlerId)) {
			this.daoFacade.getCredentialHandler(credentialHandlerId)
					.getAuthenticationHandler().showEditForm(request, response,
							null);
			return null;
		} else {
			request.setAttribute("credentialHandlers", this.daoFacade
					.getCredentialHandlers());
			return new ModelAndView("edit-credential");
		}

	}

	/**
	 * @param daoFacade
	 *            the daoFacade to set
	 */
	public void setDaoFacade(DaoFacade daoFacade) {
		this.daoFacade = daoFacade;
	}

}
