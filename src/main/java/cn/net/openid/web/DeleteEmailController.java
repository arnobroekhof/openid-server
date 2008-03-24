/**
 * 
 */
package cn.net.openid.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cn.net.openid.dao.DaoFacade;
import cn.net.openid.domain.Email;

/**
 * @author sutra
 * 
 */
public class DeleteEmailController implements Controller {
	private DaoFacade daoFacade;

	/**
	 * @param daoFacade
	 *            the daoFacade to set
	 */
	public void setDaoFacade(DaoFacade daoFacade) {
		this.daoFacade = daoFacade;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserSession userSession = (UserSession) request.getSession()
				.getAttribute("userSession");
		String id = request.getParameter("id");
		Email email = this.daoFacade.getEmail(id);
		if (email.getUser().getId().equals(userSession.getUserId())) {
			this.daoFacade.deleteEmail(id);
		}
		return new ModelAndView("email-success");
	}
}
