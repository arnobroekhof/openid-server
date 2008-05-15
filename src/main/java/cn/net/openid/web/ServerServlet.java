/**
 * Created on 2006-10-5 下午05:58:31
 */
package cn.net.openid.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openid4java.message.DirectError;
import org.openid4java.message.Message;
import org.openid4java.message.ParameterList;
import org.openid4java.server.ServerManager;
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

	private ServerManager manager;

	private static final String MANAGER = "manager";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		// Retrive configuration.
		String openidConfigurationBeanName = config.getServletContext()
				.getInitParameter(OpenIdConfiguration.CONFIGURATION_BEAN_NAME);
		OpenIdConfiguration openidConfiguration = (OpenIdConfiguration) WebApplicationContextUtils
				.getWebApplicationContext(config.getServletContext()).getBean(
						openidConfigurationBeanName);

		// instantiate a ServerManager object
		String endPointUrl = openidConfiguration.getOpenIdServer();
		manager = new ServerManager();
		manager.setOPEndpointUrl(endPointUrl);
		// for a working demo, not enforcing RP realm discovery
		// since this new feature is not deployed
		// manager.getRealmVerifier().setEnforceRpId(true);

		config.getServletContext().setAttribute(MANAGER, manager);
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
		log.debug("Received a request.");
		try {
			String responseText = processRequest(req, resp);

			if (responseText != null) {
				writeResponse(resp, responseText);
			}
		} catch (Exception e) {
			log.error("error.", e);
			throw new ServletException(e);
		}
	}

	private String processRequest(HttpServletRequest httpReq,
			HttpServletResponse httpResp) throws Exception {

		// extract the parameters from the request
		ParameterList request = new ParameterList(httpReq.getParameterMap());

		String mode = request.hasParameter("openid.mode") ? request
				.getParameterValue("openid.mode") : null;

		Message response;
		String responseText;

		if ("associate".equals(mode)) {
			// --- process an association request ---
			response = manager.associationResponse(request);
			responseText = response.keyValueFormEncoding();
		} else if ("checkid_setup".equals(mode)
				|| "checkid_immediate".equals(mode)) {
			httpReq.getSession().setAttribute("request", request);
			httpResp.sendRedirect("approving");
			responseText = null;
		} else if ("check_authentication".equals(mode)) {
			// --- processing a verification request ---
			response = manager.verify(request);
			responseText = response.keyValueFormEncoding();
		} else {
			// --- error response ---
			response = DirectError.createDirectError("Unknown request");
			responseText = response.keyValueFormEncoding();
		}

		// return the result to the user
		return responseText;
	}

	public static ServerManager getManager(ServletContext servletContext) {
		return (ServerManager) servletContext.getAttribute(MANAGER);
	}

	public static void writeResponse(HttpServletResponse httpResp,
			String response) throws IOException {
		log.debug(response);
		ServletOutputStream os = httpResp.getOutputStream();
		try {
			os.write(response.getBytes());
		} finally {
			os.close();
		}
	}

}
