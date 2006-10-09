/**
 * Created on 2006-10-5 下午11:18:21
 */
package cn.net.openid.modes;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.net.openid.Service;
import cn.net.openid.utils.OpenIDNVFormat;

/**
 * @author Shutra
 * 
 */
public class AssociateService implements Service {
	private static final Log log = LogFactory.getLog(AssociateService.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.modes.Service#doService(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String assocType = req.getParameter("openid.assoc_type");
		if (assocType != null && !assocType.equals("HMAC-SHA1")) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Unknown association type.");
		}
		String sessionType = req.getParameter("openid.session_type");

		HashMap<String, String> respParams = new HashMap<String, String>();
		// respParams.put("assoc_type", "TODO"); // TODO
		respParams.put("assoc_handle", "TODO");// TODO.
		respParams.put("expires_in", String.valueOf(1800));
		if (sessionType.length() > 0) {
			respParams.put("session_type", sessionType);
		}
		if (log.isDebugEnabled()) {
			log.debug("The response is: \n"
					+ OpenIDNVFormat.encodeToString(respParams));
		}

		resp.reset();
		resp.setContentType("text/plain; charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		OutputStream out = resp.getOutputStream();
		OpenIDNVFormat.encodeToStream(respParams, out);
		out.close();
	}
}
