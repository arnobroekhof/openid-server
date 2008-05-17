/**
 * 
 */
package cn.net.openid.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.domain.Email;
import cn.net.openid.web.AbstractJosController;
import cn.net.openid.web.UserSession;

/**
 * @author Sutra Zhou
 * 
 */
public class DeleteEmailController extends AbstractJosController {
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
