/**
 * Created on 2006-10-15 下午09:20:29
 */
package cn.net.openid.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cn.net.openid.Credential;
import cn.net.openid.CredentialHandler;
import cn.net.openid.User;
import cn.net.openid.dao.DaoFacade;

/**
 * @author Shutra
 * 
 */
public class RegisterController extends SimpleFormController {

	private DaoFacade daoFacade;

	public RegisterController() {
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
		RegisterForm form = (RegisterForm) command;
		String username = form.getUsername();

		if (!errors.hasErrors()) {
			User user = this.daoFacade.getUserByUsername(username);
			if (user != null) {
				errors.rejectValue("username",
						"error.register.usernameAlreadyExists");
			}
		}

		super.onBindAndValidate(request, command, errors);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java.lang.Object,
	 *      org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(Object command, BindException errors)
			throws Exception {
		RegisterForm form = (RegisterForm) command;
		User user = new User();
		user.setUsername(form.getUsername());
		Credential credential = new Credential();
		credential.setUser(user);
		CredentialHandler credentialHandler = this.daoFacade
				.getCredentialHandler("1");
		if (credentialHandler == null) {
			throw new RuntimeException("没有找到密码凭据类型。");
		}
		credential.setHandler(credentialHandler);
		credential.setInfo(form.getPassword().getBytes());
		this.daoFacade.insertUser(user, credential);
		return super.onSubmit(command, errors);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws Exception {
		RegisterForm form = (RegisterForm) command;
		form.setUsername(request.getParameter("username"));
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
