/**
 * Created on 2006-10-23 上午12:17:37
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

import cn.net.openid.CredentialHandler;
import cn.net.openid.Provider;
import cn.net.openid.dao.DaoFacade;
import cn.net.openid.utils.OpenIDUtils;
import cn.net.openid.web.authentication.AuthenticationHandler;

/**
 * @author Shutra
 * 
 */
public class LoginHandlersController extends SimpleFormController {
	private DaoFacade daoFacade;

	private Provider provider;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#onBindAndValidate(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		LoginHandlersForm form = (LoginHandlersForm) command;
		CredentialHandler credentialHandler;
		if (form.getCredentialHandler().getId() == null
				|| (credentialHandler = daoFacade.getCredentialHandler(form
						.getCredentialHandler().getId())) == null) {
			errors.rejectValue("credentialHandler.id", "error", "请选择凭据类型。");
		} else {
			form.setCredentialHandler(credentialHandler);
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
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		LoginHandlersForm form = (LoginHandlersForm) command;
		AuthenticationHandler authenticationHandler = (AuthenticationHandler) Class
				.forName(form.getCredentialHandler().getClassName())
				.newInstance();
		authenticationHandler.showForm(request, response, form.getUsername());
		return super.onSubmit(request, response, command, errors);
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
		LoginHandlersForm form = (LoginHandlersForm) command;
		form.setCredentialHandlers(daoFacade.getCredentialHandlers());
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

	/**
	 * @param provider
	 *            the provider to set
	 */
	public void setProvider(Provider provider) {
		this.provider = provider;
	}
}
