/**
 * Copyright (c) 2006-2009, Redv.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Redv.com nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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
import org.openid4java.message.sreg.SRegMessage;
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
 */
public class ApprovingController extends AbstractJosSimpleFormController {
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isFormSubmission(final HttpServletRequest request) {
		return request.getParameter("allow_once") != null
				|| request.getParameter("allow_forever") != null
				|| request.getParameter("deny") != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Map<String, Object> referenceData(
			final HttpServletRequest request, final Object command,
			final Errors errors) throws Exception {
		Map<String, Object> models = new HashMap<String, Object>();

		ApprovingForm form = (ApprovingForm) command;
		String token = request.getParameter("token");
		form.setToken(token);

		UserSession userSession = getUserSession(request);
		ApprovingRequest checkIdRequest = userSession
				.getApprovingRequest(token);
		if (checkIdRequest != null) {
			AuthRequest authReq = checkIdRequest.getAuthRequest();
			form.setAuthRequest(authReq);

			if (authReq.hasExtension(SRegMessage.OPENID_NS_SREG)) {
				models.put("personas", this.getJosService().getPersonas(
						userSession.getUser()));

				String realmUrl = authReq.getRealm();
				Site site = getJosService().getSite(userSession.getUser(),
						realmUrl);
				if (site != null && site.getPersona() != null) {
					form.setPersonaId(site.getPersona().getId());
				}
			}
		}
		return models;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onBindAndValidate(final HttpServletRequest request,
			final Object command, final BindException errors) throws Exception {
		ApprovingForm form = (ApprovingForm) command;
		boolean allow = request.getParameter("allow_once") != null
				|| request.getParameter("allow_forever") != null;
		if (allow) {
			boolean sreg = getUserSession(request).getApprovingRequest(
					form.getToken()).getAuthRequest().hasExtension(
					SRegMessage.OPENID_NS_SREG);
			if (sreg && StringUtils.isEmpty(form.getPersonaId())) {
				errors.rejectValue("personaId", "required",
						"Persona is required.");
			}
		}
		super.onBindAndValidate(request, command, errors);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {
		Domain domain = this.getDomain(request);
		ApprovingForm form = (ApprovingForm) command;

		UserSession userSession = getUserSession(request);

		ApprovingRequest checkIdRequest = userSession.getApprovingRequest(form
				.getToken());

		String personaId = request.getParameter("personaId");

		ApprovingRequestProcessor arp = new ApprovingRequestProcessor(request,
				response, getJosService(), this.getJosService()
						.getServerManager(domain), checkIdRequest);

		Persona persona = null;
		if (request.getParameter("allow_once") != null) {
			if (personaId != null) {
				persona = getJosService().getPersona(userSession.getUser(),
						personaId);
			}
			arp.checkId(ApprovingRequestProcessor.ALLOW_ONCE, persona);
		} else if (request.getParameter("allow_forever") != null) {
			if (personaId != null) {
				persona = getJosService().getPersona(userSession.getUser(),
						personaId);
			}
			arp.checkId(ApprovingRequestProcessor.ALLOW_FOREVER, persona);
		} else if (request.getParameter("deny") != null) {
			arp.checkId(ApprovingRequestProcessor.DENY, null);
		} else {
			arp.checkId(ApprovingRequestProcessor.DENY, null);
		}

		return null;
	}
}
