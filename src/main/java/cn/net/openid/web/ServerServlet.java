/**
 * Created on 2006-10-5 下午05:58:31
 */
package cn.net.openid.web;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.net.openid.OpenidConfiguration;

/**
 * @author Shutra
 * 
 */
public class ServerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2796635946888123803L;

	private static final Log log = LogFactory.getLog(ServerServlet.class);

	private SampleServer server;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String openidConfigurationBeanName = config.getServletContext()
				.getInitParameter(OpenidConfiguration.CONFIGURATION_BEAN_NAME);
		OpenidConfiguration openidConfiguration = (OpenidConfiguration) WebApplicationContextUtils
				.getWebApplicationContext(config.getServletContext()).getBean(
						openidConfigurationBeanName);

		server = new SampleServer(openidConfiguration.getOpenidServer());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (log.isDebugEnabled()) {
			log.debug("A " + req.getMethod() + " called: ");
			Map<String, String[]> parameterMap = req.getParameterMap();
			for (Iterator<String> iter = parameterMap.keySet().iterator(); iter
					.hasNext();) {
				String key = iter.next();
				log.debug(key + "=" + parameterMap.get(key)[0]);
			}
		}
		try {
			String responseText = server.processRequest(req, resp);
			log.debug("responseText: " + responseText);
			resp.sendRedirect(responseText);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
