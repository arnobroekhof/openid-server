/**
 * Created on 2006-10-5 下午11:19:27
 */
package cn.net.openid.modes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.net.openid.Provider;
import cn.net.openid.Service;
import cn.net.openid.utils.OpenIDUtils;

/**
 * @author Shutra
 * 
 */
public class CheckIdSetupService implements Service {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(CheckIdSetupService.class);

	private Provider provider;

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.modes.Service#doService(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		HttpSession session = req.getSession();

		String claimedIdentifier = req.getParameter("openid.identity");
		String verifiedIdentifier = ObjectUtils.toString(session
				.getAttribute("cn.net.openid.identity"));
		if (verifiedIdentifier.length() > 0
				&& verifiedIdentifier.equalsIgnoreCase(claimedIdentifier)) {
			this.provider.checkIdSetupResponse(verifiedIdentifier, req
					.getParameterMap(), resp);
		} else {
			Map<String, String[]> map = req.getParameterMap();
			HashMap<String, String[]> serializableMap = new HashMap<String, String[]>(
					map);
			req.getSession().setAttribute("parameterMap", serializableMap);
			resp.sendRedirect(OpenIDUtils.getContextPath(req) + "/login");
		}

	}
}
