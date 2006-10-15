/**
 * Created on 2006-10-15 下午09:20:29
 */
package cn.net.openid.web;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;

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
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java.lang.Object,
	 *      org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(Object command, BindException errors)
			throws Exception {
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
		String member = form.getMember();
		if (member != null) {
			Matcher m = this.pattern.matcher(member);
			if (!m.matches()) {
				errors.rejectValue("member", "错误的格式。");
			}
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
