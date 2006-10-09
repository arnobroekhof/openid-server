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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.net.openid.Modes;

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

	private Modes modes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(config.getServletContext());
		this.modes = ((Modes) wac.getBean("modes"));
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
		super.service(req, resp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.modes.getGetModes().get(req.getParameter("openid.mode")).service(
				req, resp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.modes.getPostModes().get(req.getParameter("openid.mode")).service(
				req, resp);
	}
}
