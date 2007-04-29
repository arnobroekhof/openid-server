/**
 * Created on 2007-4-30 上午12:28:23
 */
package cn.net.openid.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openid4java.message.ParameterList;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @author Sutra Zhou
 * 
 */
public class ProviderAuthorizationController implements Controller {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// HOWTO:
		// the session var parameterlist contains openid authreq message
		// parameters
		// this JSP should set the session attribute var
		// "authenticatedAndApproved" and
		// redirect to provider.jsp?_action=complete
		HttpSession session = request.getSession();

		ParameterList requestp = (ParameterList) session
				.getAttribute("parameterlist");
		String openidrealm = requestp.hasParameter("openid.realm") ? requestp
				.getParameterValue("openid.realm") : null;
		String openidreturnto = requestp.hasParameter("openid.return_to") ? requestp
				.getParameterValue("openid.return_to")
				: null;
		String openidclaimedid = requestp.hasParameter("openid.claimed_id") ? requestp
				.getParameterValue("openid.claimed_id")
				: null;
		String openididentity = requestp.hasParameter("openid.identity") ? requestp
				.getParameterValue("openid.identity")
				: null;
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");

		if (userSession.getOpenidUrl().equals(openididentity)) {
			String site = (String) (openidrealm == null ? openidreturnto
					: openidrealm);
			// No need to change openid.* session vars
			session.setAttribute("authenticatedAndApproved", true);
			response.sendRedirect("openid.server?_action=complete");
		} else {
			response.getWriter().write("Openid hacking.");
		}
		return null;
	}
}
