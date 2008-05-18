/**
 * Created on 2008-05-18 11:47:09
 */
package cn.net.openid.jos.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Site;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.WebUtils;

/**
 * @author Sutra Zhou
 * 
 */
public class SitesController extends AbstractJosSimpleFormController {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#processFormSubmission(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String[] realmIds = request.getParameterValues("realmId");
		String userId = WebUtils.getOrCreateUserSession(request.getSession())
				.getUserId();
		for (String realmId : realmIds) {
			this.josService.updateAlwaysApprove(userId, realmId, request
					.getParameter("alwaysApprove_" + realmId) != null);
		}
		request.setAttribute("sites", this.josService.getSites(userId));
		return super.processFormSubmission(request, response, command, errors);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Map<String, List<Site>> referenceData(HttpServletRequest request)
			throws Exception {
		return this.getModel(WebUtils.getOrCreateUserSession(
				request.getSession()).getUserId());
	}

	private Map<String, List<Site>> getModel(String userId) {
		List<Site> sites = this.josService.getSites(userId);
		Map<String, List<Site>> model = new HashMap<String, List<Site>>(1);
		model.put("sites", sites);
		return model;
	}
}
