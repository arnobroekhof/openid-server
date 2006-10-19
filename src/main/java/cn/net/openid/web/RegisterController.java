/**
 * Created on 2006-10-15 下午09:20:29
 */
package cn.net.openid.web;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cn.net.openid.Credential;
import cn.net.openid.User;
import cn.net.openid.dao.DaoFacade;

/**
 * @author Shutra
 * 
 */
public class RegisterController extends SimpleFormController {

	private DaoFacade daoFacade;

	private Pattern pattern;

	public RegisterController() {
		pattern = Pattern.compile("[a-z]{1,16}");
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
		String member = form.getMember();
		if (member != null) {
			Matcher m = this.pattern.matcher(member);
			if (!m.matches()) {
				errors.rejectValue("member", "error", "错误的格式。");
			}
		}

		if (!errors.hasErrors()) {
			User user = this.daoFacade.getUserByUsername(member);
			if (user != null) {
				errors.rejectValue("member", "error",
						"该OpenID已经被其他人申请了，你只能换一个了。");
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
		user.setUsername(form.getMember());
		this.daoFacade.saveUser(user);
		Credential credential = new Credential();
		credential.setUser(user);
		credential.setInfo(form.getPassword().getBytes());
		this.daoFacade.saveCredential(credential);
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
		form.setMember(request.getParameter("member"));
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
