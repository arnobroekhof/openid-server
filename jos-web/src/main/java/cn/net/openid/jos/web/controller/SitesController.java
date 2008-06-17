/**
 * Created on 2008-05-18 11:47:09
 */
package cn.net.openid.jos.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Site;
import cn.net.openid.jos.web.AbstractJosController;

/**
 * @author Sutra Zhou
 * 
 */
public class SitesController extends AbstractJosController {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Update alwaysApprove.
		String[] realmIds = request.getParameterValues("realmId");
		String userId = getUser(request).getUserId();
		if (realmIds != null) {
			for (String realmId : realmIds) {
				this.josService.updateAlwaysApprove(userId, realmId, request
						.getParameter("alwaysApprove_" + realmId) != null);
			}
		}

		return new ModelAndView("sites", getModel(userId));
	}

	private Map<String, List<Site>> getModel(String userId) {
		List<Site> sites = this.josService.getSites(userId);
		Map<String, List<Site>> model = new HashMap<String, List<Site>>(1);
		model.put("sites", sites);
		return model;
	}

}
