/**
 * Created on 2006-10-29 上午03:53:17
 */
package cn.net.openid.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cn.net.openid.Credential;
import cn.net.openid.CredentialException;
import cn.net.openid.dao.DaoFacade;

/**
 * @author Shutra
 * 
 */
public class DeleteCredentialController extends SimpleFormController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1312827625002644956L;

	private DaoFacade daoFacade;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		String id = request.getParameter("id");
		Object command = this.daoFacade.getCredential(id);
		return command;
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
		Credential credential = (Credential) command;
		try {
			this.daoFacade.deleteCredential(credential);
		} catch (CredentialException ce) {
			errors.rejectValue("id", "error.credential.lastOne");
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
