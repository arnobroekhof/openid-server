/**
 * 
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Email;
import cn.net.openid.jos.web.AbstractJosController;
import cn.net.openid.jos.web.UserSession;
import cn.net.openid.jos.web.WebUtils;

/**
 * @author Sutra Zhou
 * 
 */
public class DeleteEmailController extends AbstractJosController {
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserSession userSession = WebUtils.getOrCreateUserSession(request
				.getSession());
		String id = request.getParameter("id");
		Email email = this.josService.getEmail(id);
		if (email.getUser().getId().equals(userSession.getUserId())) {
			this.josService.deleteEmail(id);
		}
		return new ModelAndView("email-success");
	}
}
