/**
 * Created on 2006-10-29 上午02:58:32
 */
package cn.net.openid.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cn.net.openid.Credential;
import cn.net.openid.dao.DaoFacade;

/**
 * @author Shutra
 * 
 */
public class EditPasswordController extends SimpleFormController {
	private DaoFacade daoFacade;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		EditPasswordForm editPasswordForm = (EditPasswordForm) super
				.formBackingObject(request);
		editPasswordForm.setCredentialId(request.getParameter("id"));
		return editPasswordForm;
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
		super.onBindAndValidate(request, command, errors);
		EditPasswordForm editPasswordForm = (EditPasswordForm) command;
		if (StringUtils.isEmpty(editPasswordForm.getPassword())) {
			errors.rejectValue("password", "error", "密码不能为空。");
		}
		if (!StringUtils.equals(editPasswordForm.getPassword(),
				editPasswordForm.getRetypedPassword())) {
			errors.rejectValue("retypedPassword", "error", "两次输入的密码不一致。");
		}
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
		EditPasswordForm editPasswordForm = (EditPasswordForm) command;
		Credential credential = this.daoFacade.getCredential(editPasswordForm
				.getCredentialId());
		if (credential == null) {
			HttpSession session = request.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			credential = new Credential();
			credential.setUser(daoFacade.getUser(userSession.getUserId()));
			credential.setHandler(this.daoFacade.getCredentialHandler("1"));
			credential
					.setInfo(editPasswordForm.getPassword().getBytes("UTF-8"));
			this.daoFacade.insertCredential(credential);
		} else {
			credential
					.setInfo(editPasswordForm.getPassword().getBytes("UTF-8"));
			this.daoFacade.updateCredential(credential);
		}
		return super.onSubmit(request, response, command, errors);
	}

	/**
	 * @param daoFacade
	 *            the daoFacade to set
	 */
	public void setDaoFacade(DaoFacade daoFacade) {
		this.daoFacade = daoFacade;
	}

}
