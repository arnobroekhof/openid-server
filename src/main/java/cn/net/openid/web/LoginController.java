/**
 * Created on 2006-10-7 上午12:05:13
 */
package cn.net.openid.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cn.net.openid.Provider;
import cn.net.openid.User;
import cn.net.openid.dao.DaoFacade;
import cn.net.openid.utils.OpenIDUtils;

/**
 * @author Shutra
 * 
 */
public class LoginController extends SimpleFormController {
	private Provider provider;

	@SuppressWarnings("unused")
	private DaoFacade daoFacade;

	private User check(LoginForm lf) {
		return this.provider.checkCredential(lf);
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
			errors.rejectValue("username", "", "认证失败。");
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

		Map<String, String[]> pm = (Map<String, String[]>) request.getSession()
				.getAttribute("parameterMap");
		if (pm != null) {
			this.provider.checkIdSetupResponse(session.getAttribute(
					"cn.net.openid.identity").toString(), pm, response);
			return null;
		} else {
			return super.onSubmit(request, response, command, errors);
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

		HttpSession session = request.getSession();

		Map<String, String[]> parameterMap = (Map<String, String[]>) session
				.getAttribute("parameterMap");
		if (parameterMap != null) {
			form.setUsername(this.provider.getUsername(OpenIDUtils
					.getFirstValue(parameterMap, "openid.identity")));
		}

		if (StringUtils.isEmpty(form.getUsername())) {
			form.setUsername(this.provider.getUsername(request
					.getParameter("openidUrl")));
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

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

}
