/**
 * 
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.web.AbstractJosController;
import cn.net.openid.jos.web.UserSession;

/**
 * @author Sutra Zhou
 * 
 */
public class DeleteEmailController extends AbstractJosController {
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserSession userSession = getUserSession(request);
		String id = request.getParameter("id");
		if (!StringUtils.isEmpty(id)) {
			josService.deleteEmail(userSession.getUser(), id);
		}
		return new ModelAndView("email-remove-success");
	}
}
