/**
 * Created on 2006-10-7 上午12:05:13
 */
package cn.net.openid.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cn.net.openid.Provider;
import cn.net.openid.utils.OpenIDUtils;

/**
 * @author Shutra
 * 
 */
public class LoginController extends SimpleFormController {
	private Provider provider;

	public void setProvider(Provider provider) {
		this.provider = provider;
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
		HttpSession session = request.getSession();
		LoginForm lf = (LoginForm) command;
		Map<String, String[]> parameterMap = (Map<String, String[]>) session
				.getAttribute("parameterMap");
		if (parameterMap != null) {
			lf.setOpenidUrl(OpenIDUtils.getFirstValue(parameterMap,
					"openid.identity"));
		}

		if (lf.getOpenidUrl() == null || lf.getOpenidUrl().length() == 0) {
			lf.setOpenidUrl(request.getParameter("openid"));
		}
		return super.referenceData(request, command, errors);
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
		LoginForm lf = (LoginForm) command;
		if (this.provider.checkPassword(lf)) {
			HttpSession session = request.getSession();
			session.setAttribute("cn.net.openid.identity", lf.getOpenidUrl());
			this.provider.checkIdSetupResponse(session.getAttribute(
					"cn.net.openid.identity").toString(),
					(Map<String, String[]>) request.getSession().getAttribute(
							"parameterMap"), response);
			return null;
		} else {
			return super.onSubmit(request, response, command, errors);
		}
	}
}
