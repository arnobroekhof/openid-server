/**
 * Created on 2006-10-5 下午11:19:47
 */
package cn.net.openid.modes;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.net.openid.Service;
import cn.net.openid.utils.OpenIDNVFormat;

/**
 * @author Shutra
 * 
 */
public class CheckAuthenticationService implements Service {
	private static final Log log = LogFactory
			.getLog(CheckAuthenticationService.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.modes.Service#doService(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		HashMap<String, String> respParams = new LinkedHashMap<String, String>(
				3);
		respParams.put("openid.mode", "id_res");
		respParams.put("is_valid", isValid(req.getParameterMap()) ? "true"
				: "false");
		// responseParameters.put("invalidate_handle", "TODO");
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

	private boolean isValid(Map<String, String[]> parameterMap) {
		String[] signed = parameterMap.get("openid.signed")[0].toString()
				.split(",");
		Map<String, String> signedMap = new HashMap<String, String>(
				signed.length);
		for (int i = 0; i < signed.length; i++) {
			String s = signed[i];
			if (!StringUtils.isEmpty(s)) {
				String key = "openid." + s;
				signedMap.put(key, parameterMap.get(key)[0].toString());
			}
		}
		
		return true;
	}

}
