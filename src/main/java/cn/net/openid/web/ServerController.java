/**
 * Created on 2006-10-5 下午05:58:31
 */
package cn.net.openid.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.message.DirectError;
import org.openid4java.message.Message;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.openid4java.server.ServerManager;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Sutra Zhou
 * 
 */
public class ServerController extends AbstractDaoFacadeController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2796635946888123803L;

	public static void writeResponse(HttpServletResponse httpResp,
			String response) throws IOException {
		ServletOutputStream os = httpResp.getOutputStream();
		try {
			os.write(response.getBytes());
		} finally {
			os.close();
		}
	}

	public static void redirectToReturnToPage(ServerManager manager,
			HttpServletRequest httpReq, HttpServletResponse httpResp,
			ParameterList request, boolean approved) throws MessageException,
			IOException {
		ApprovingController.response(manager, httpReq, httpResp, request,
				approved);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Received a request.");
		try {
			String responseText = processRequest(request, response);

			if (responseText != null) {
				writeResponse(response, responseText);
			}
		} catch (Exception e) {
			log.error("error.", e);
			throw new ServletException(e);
		}
		return null;
	}

	private String processRequest(HttpServletRequest httpReq,
			HttpServletResponse httpResp) throws Exception {
		// extract the parameters from the request
		ParameterList request = new ParameterList(httpReq.getParameterMap());

		String mode = request.getParameterValue("openid.mode");

		Message response;
		String responseText;

		if ("associate".equals(mode)) {
			// --- process an association request ---
			response = this.serverManager.associationResponse(request);
			responseText = response.keyValueFormEncoding();
		} else if ("checkid_setup".equals(mode)
				|| "checkid_immediate".equals(mode)) {
			this.checkId(httpReq, httpResp, request);
			responseText = null;
		} else if ("check_authentication".equals(mode)) {
			// --- processing a verification request ---
			response = this.serverManager.verify(request);
			responseText = response.keyValueFormEncoding();
		} else {
			// --- error response ---
			response = DirectError.createDirectError("Unknown request");
			responseText = response.keyValueFormEncoding();
		}

		// return the result to the user
		return responseText;
	}

	private void checkId(HttpServletRequest httpReq,
			HttpServletResponse httpResp, ParameterList request)
			throws IOException {
		UserSession userSession = WebUtils.getUserSession(httpReq);
		if (userSession == null) {
			// redirect to login page.
			httpResp.sendRedirect("login");
		} else if (this.isAllowForever()) {
			// return to `return_to' page.
			try {
				redirectToReturnToPage(this.serverManager, httpReq, httpResp,
						request, true);
			} catch (MessageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// redirect to approving page.
			httpReq.getSession().setAttribute("request", request);
			httpResp.sendRedirect("approving");
		}
	}

	private boolean isAllowForever() {
		return true;
	}
}
