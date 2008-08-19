/**
 * Created on 2008-5-12 00:55:46
 */
package cn.net.openid.jos.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.openid4java.message.AuthRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Domain;
import cn.net.openid.jos.domain.Persona;
import cn.net.openid.jos.domain.Site;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.ApprovingRequest;
import cn.net.openid.jos.web.ApprovingRequestProcessor;
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
	 * @see
	 * org.springframework.web.servlet.mvc.BaseCommandController#initBinder(
	 * javax.servlet.http.HttpServletRequest,
	 * org.springframework.web.bind.ServletRequestDataBinder)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#isFormSubmission
	 * (javax.servlet.http.HttpServletRequest)
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
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#referenceData
	 * (javax.servlet.http.HttpServletRequest, java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request,
			Object command, Errors errors) throws Exception {
		ApprovingForm form = (ApprovingForm) command;
		String token = request.getParameter("token");
		form.setToken(token);

		UserSession userSession = getUserSession(request);
		ApprovingRequest checkIdRequest = userSession
				.getApprovingRequest(token);
		if (checkIdRequest != null) {
			AuthRequest authReq = checkIdRequest.getAuthRequest();
			form.setAuthRequest(authReq);
			String realmUrl = authReq.getRealm();
			Site site = getJosService()
					.getSite(userSession.getUser(), realmUrl);
			if (site != null && site.getPersona() != null) {
				form.setPersonaId(site.getPersona().getId());
			}
		}
		Map<String, Object> models = new HashMap<String, Object>();
		models.put("personas", this.getJosService().getPersonas(
				userSession.getUser()));
		return models;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.BaseCommandController#onBindAndValidate
	 * (javax.servlet.http.HttpServletRequest, java.lang.Object,
	 * org.springframework.validation.BindException)
	 */
	@Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		ApprovingForm form = (ApprovingForm) command;
		if ((request.getParameter("allow_once") != null || request
				.getParameter("allow_forever") != null)
				&& StringUtils.isEmpty(form.getPersonaId())) {
			errors.rejectValue("personaId", "required", "Persona is required.");
		}
		super.onBindAndValidate(request, command, errors);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		Domain domain = this.getDomain(request);
		ApprovingForm form = (ApprovingForm) command;

		UserSession userSession = getUserSession(request);

		ApprovingRequest checkIdRequest = userSession.getApprovingRequest(form
				.getToken());

		String personaId = request.getParameter("personaId");

		ApprovingRequestProcessor arp = new ApprovingRequestProcessor(request,
				response, getJosService(), this.getJosService()
						.getServerManager(domain), checkIdRequest);

		Persona persona;
		if (request.getParameter("allow_once") != null) {
			persona = getJosService().getPersona(userSession.getUser(),
					personaId);
			arp.checkId(ApprovingRequestProcessor.ALLOW_ONCE, persona);
		} else if (request.getParameter("allow_forever") != null) {
			persona = getJosService().getPersona(userSession.getUser(),
					personaId);
			arp.checkId(ApprovingRequestProcessor.ALLOW_FOREVER, persona);
		} else if (request.getParameter("deny") != null) {
			arp.checkId(ApprovingRequestProcessor.DENY, null);
		} else {
			arp.checkId(ApprovingRequestProcessor.DENY, null);
		}

		return null;
	}
}
