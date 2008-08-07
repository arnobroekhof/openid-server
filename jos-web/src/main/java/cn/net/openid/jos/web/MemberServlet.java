/**
 * Created on 2006-10-7 下午02:05:12
 */
package cn.net.openid.jos.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Sutra Zhou
 * 
 */
public class MemberServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6670900579691443871L;

	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(MemberServlet.class);

	private ServletContext context;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.context = config.getServletContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (pathInfo != null && pathInfo.length() > 1) {
			req.setAttribute("username", req.getPathInfo().substring(1));
			this.context.getRequestDispatcher("/member.jsp").forward(req, resp);
		} else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

}
