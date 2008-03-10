/**
 * Created on 2008-3-10 下午11:04:50
 */
package cn.net.openid.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cn.net.openid.dao.DaoFacade;
import cn.net.openid.domain.Email;
import cn.net.openid.domain.User;

/**
 * @author sutra
 * 
 */
public class EmailController extends SimpleFormController {
	private DaoFacade daoFacade;

	/**
	 * @param daoFacade
	 *            要设置的 daoFacade
	 */
	public void setDaoFacade(DaoFacade daoFacade) {
		this.daoFacade = daoFacade;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		String userId = userSession.getUserId();
		User user = this.daoFacade.getUser(userId);
		Email email = (Email) super.formBackingObject(request);
		email.setUser(user);
		return email;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java.lang.Object)
	 */
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		Email email = (Email) command;
		this.daoFacade.insertEmail(email);
		return super.onSubmit(command);
	}

}
