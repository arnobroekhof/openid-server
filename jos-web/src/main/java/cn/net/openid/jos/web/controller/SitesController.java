/**
 * Created on 2008-05-18 11:47:09
 */
package cn.net.openid.jos.web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Site;
import cn.net.openid.jos.domain.User;
import cn.net.openid.jos.web.AbstractJosController;

/**
 * @author Sutra Zhou
 * 
 */
public class SitesController extends AbstractJosController {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Update alwaysApprove.
		String[] realmIds = request.getParameterValues("realmId");
		User user = getUser(request);
		if (realmIds != null) {
			for (String realmId : realmIds) {
				this.getJosService()
						.updateAlwaysApprove(
								user,
								realmId,
								request
										.getParameter("alwaysApprove_"
												+ realmId) != null);
			}
		}

		return new ModelAndView("sites", getModel(user));
	}

	private Map<String, Collection<Site>> getModel(User user) {
		Collection<Site> sites = getJosService().getSites(user);
		Map<String, Collection<Site>> model = new HashMap<String, Collection<Site>>(
				1);
		model.put("sites", sites);
		return model;
	}

}
