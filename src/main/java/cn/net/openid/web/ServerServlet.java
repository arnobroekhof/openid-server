/**
 * Created on 2006-10-5 下午05:58:31
 */
package cn.net.openid.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.net.openid.OpenIdConfiguration;

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
				.getInitParameter(OpenIdConfiguration.CONFIGURATION_BEAN_NAME);
		OpenIdConfiguration openidConfiguration = (OpenIdConfiguration) WebApplicationContextUtils
				.getWebApplicationContext(config.getServletContext()).getBean(
						openidConfigurationBeanName);

		server = new SampleServer(openidConfiguration.getOpenIdServer());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			server.processRequest(req, resp);
		} catch (Exception e) {
			log.error("error.", e);
			throw new ServletException(e);
		}
	}
}
