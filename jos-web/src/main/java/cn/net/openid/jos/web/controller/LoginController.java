/**
 * Created on 2006-10-7 上午12:05:13
 */
package cn.net.openid.jos.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.ApprovingRequest;
import cn.net.openid.jos.web.ApprovingRequestProcessor;
import cn.net.openid.jos.web.UserSession;
import cn.net.openid.jos.web.form.LoginForm;

/**
 * @author Sutra Zhou
 * 
 */
public class LoginController extends AbstractJosSimpleFormController {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(LoginController.class);

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
		User user = josService.getUser(lf.getUsername(), lf.getPassword());
		if (user == null) {
			errors.rejectValue("username", "error.login.failed");
		} else {
			HttpSession session = request.getSession();
			UserSession userSession = getUserSession(session);
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
		String token = (String) request.getAttribute("token");
		UserSession userSession = getUserSession(request);
		ApprovingRequest checkIdRequest = userSession
				.getApprovingRequest(token);
		if (checkIdRequest != null) {
			new ApprovingRequestProcessor(request, response, josService,
					serverManager, checkIdRequest).checkId();
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

		return null;
	}
}
