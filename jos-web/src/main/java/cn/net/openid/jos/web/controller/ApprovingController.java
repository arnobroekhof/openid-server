/**
 * Created on 2008-5-12 00:55:46
 */
package cn.net.openid.jos.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.message.AuthRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Persona;
import cn.net.openid.jos.domain.Site;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.CheckIdRequest;
import cn.net.openid.jos.web.CheckIdRequestProcessor;
import cn.net.openid.jos.web.UserSession;
import cn.net.openid.jos.web.form.ApprovingForm;

/**
 * @author Sutra Zhou
 * 
 */
public class ApprovingController extends AbstractJosSimpleFormController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#initBinder(javax.servlet.http.HttpServletRequest,
	 *      org.springframework.web.bind.ServletRequestDataBinder)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#isFormSubmission(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean isFormSubmission(HttpServletRequest request) {
		return request.getParameter("allow_once") != null
				|| request.getParameter("allow_forever") != null
				|| request.getParameter("deny") != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request,
			Object command, Errors errors) throws Exception {
		ApprovingForm form = (ApprovingForm) command;
		String token = request.getParameter("token");
		form.setToken(token);

		UserSession userSession = getUser(request);
		String userId = userSession.getUserId();
		CheckIdRequest checkIdRequest = userSession.getRequest(token);
		if (checkIdRequest != null) {
			AuthRequest authReq = checkIdRequest.getAuthRequest();
			form.setAuthRequest(authReq);
			String realmUrl = authReq.getRealm();
			Site site = josService.getSite(userId, realmUrl);
			if (site != null && site.getPersona() != null) {
				form.setPersonaId(site.getPersona().getId());
			}
		}
		Map<String, Object> models = new HashMap<String, Object>();
		models.put("personas", this.josService.getPersonas(userId));
		return models;
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
		ApprovingForm form = (ApprovingForm) command;

		UserSession userSession = getUser(request);

		CheckIdRequest checkIdRequest = userSession.getRequest(form.getToken());
		AuthRequest authReq = checkIdRequest.getAuthRequest();

		String userId = userSession.getUserId();
		String personaId = request.getParameter("personaId");

		Boolean approved;
		Persona persona;
		if (request.getParameter("allow_once") != null) {
			approved = Boolean.TRUE;
			persona = josService.getPersona(personaId);

			josService.allow(userId, authReq.getRealm(), persona, false);
		} else if (request.getParameter("allow_forever") != null) {
			approved = Boolean.TRUE;
			persona = josService.getPersona(personaId);

			josService.allow(userId, authReq.getRealm(), persona, true);
		} else if (request.getParameter("deny") != null) {
			approved = Boolean.FALSE;
			persona = null;
		} else {
			approved = Boolean.FALSE;
			persona = null;
		}

		new CheckIdRequestProcessor(request, response, josService,
				serverManager, checkIdRequest).redirectToReturnToPage(approved,
				persona);
		return null;
	}
}
