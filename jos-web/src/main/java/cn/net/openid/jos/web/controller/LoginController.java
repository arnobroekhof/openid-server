/**
 * Created on 2006-10-7 上午12:05:13
 */
package cn.net.openid.jos.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openid4java.message.MessageException;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Password;
import cn.net.openid.jos.domain.Site;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.UserSession;
import cn.net.openid.jos.web.WebUtils;
import cn.net.openid.jos.web.form.LoginForm;

/**
 * @author Sutra Zhou
 * 
 */
public class LoginController extends AbstractJosSimpleFormController {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(LoginController.class);

	private User check(LoginForm lf) {
		User user = josService.getUserByUsername(lf.getUsername());
		if (user == null) {
			return null;
		}

		// List<Credential> credentials =
		// daoFacade.getCredentials(user.getId());
		// for (Credential c : credentials) {
		// log.debug("Password: " + new String(c.getInfo()));
		// try {
		// if (new String(c.getInfo(), "UTF-8").equals(new String(lf
		// .getPassword().getBytes("UTF-8")))) {
		// return user;
		// }
		// } catch (UnsupportedEncodingException e) {
		// throw new RuntimeException(e);
		// }
		// }
		Password password = this.josService.getPasswordByUserId(user.getId());
		if (password.getPasswordShaHex().equalsIgnoreCase(
				DigestUtils.shaHex(lf.getPassword()))) {
			return user;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#onBindAndValidate(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		LoginForm lf = (LoginForm) command;
		User user = this.check(lf);
		if (user == null) {
			errors.rejectValue("username", "error.login.failed");
		} else {
			HttpSession session = request.getSession();
			UserSession userSession = WebUtils.getOrCreateUserSession(session);
			userSession.setUser(user);
			userSession.setLoggedIn(true);
			userSession.setIdentifier(this.josService.buildOpenidUrl(lf
					.getUsername()));
		}
		super.onBindAndValidate(request, command, errors);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      org.springframework.validation.BindException)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		LoginForm form = (LoginForm) command;
		String token = form.getToken();
		UserSession userSession = WebUtils.getOrCreateUserSession(request
				.getSession());
		if (userSession.hasRequest(token)) {
			Site site = this.josService.getSite(userSession.getUserId(),
					userSession.getRequest(token).getRealm());
			if (site != null && site.isAlwaysApprove()) {
				this.josService.updateApproval(userSession.getUserId(),
						userSession.getRequest(token).getRealm());
				// return to `return_to' page.
				try {
					ServerController.redirectToReturnToPage(this.serverManager,
							request, response, userSession.getRequest(token),
							true, site.getPersona());
				} catch (MessageException e) {
					log.error("", e);
				}
			} else {
				// redirect to approving page.
				response.sendRedirect("approving?token=" + token);
			}
		}
		return super.onSubmit(request, response, command, errors);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	protected Map<Object, Object> referenceData(HttpServletRequest request,
			Object command, Errors errors) throws Exception {
		LoginForm form = (LoginForm) command;

		if (StringUtils.isEmpty(form.getUsername())) {
			form.setUsername(request.getParameter("username"));
		}

		form.setToken(request.getParameter("token"));
		return null;
	}
}
