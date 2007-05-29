/**
 * Created on 2006-10-7 上午12:05:13
 */
package cn.net.openid.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openid4java.message.ParameterList;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cn.net.openid.Credential;
import cn.net.openid.User;
import cn.net.openid.dao.DaoFacade;

/**
 * @author Sutra Zhou
 * 
 */
public class LoginController extends SimpleFormController {
	private static final Log log = LogFactory.getLog(LoginController.class);

	private DaoFacade daoFacade;

	private User check(LoginForm lf) {
		User user = daoFacade.getUserByUsername(lf.getUsername());
		List<Credential> credentials = daoFacade.getCredentials(user.getId());
		for (Credential c : credentials) {
			log.debug("Password: " + new String(c.getInfo()));
			if (new String(c.getInfo()).equals(new String(lf.getPassword()
					.getBytes()))) {
				return user;
			}
		}
		return null;
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
			UserSession userSession = new UserSession(user);
			userSession.setLoggedIn(true);
			userSession.setOpenidUrl(this.daoFacade.buildOpenidUrl(lf
					.getUsername()));
			session.setAttribute("userSession", userSession);

			session.setAttribute("cn.net.openid.username", lf.getUsername()
					.toLowerCase());
			session.setAttribute("cn.net.openid.identity", this.daoFacade
					.buildOpenidUrl(lf.getUsername()));
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
		HttpSession session = request.getSession();

		ParameterList pl = (ParameterList) session
				.getAttribute("parameterlist");
		if (pl == null) {
			return super.onSubmit(request, response, command, errors);
		} else {
			response.sendRedirect("provider-authorization");
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.Errors)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws Exception {
		LoginForm form = (LoginForm) command;

		if (StringUtils.isEmpty(form.getUsername())) {
			form.setUsername(request.getParameter("username"));
		}

		return super.referenceData(request, command, errors);
	}

	/**
	 * @param daoFacade
	 *            the daoFacade to set
	 */
	public void setDaoFacade(DaoFacade daoFacade) {
		this.daoFacade = daoFacade;
	}
}
