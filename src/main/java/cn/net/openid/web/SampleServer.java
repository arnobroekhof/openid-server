/**
 * 
 */
package cn.net.openid.web;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openid4java.message.DirectError;
import org.openid4java.message.Message;
import org.openid4java.message.ParameterList;
import org.openid4java.server.ServerManager;

/**
 * @author <a href="mailto:zhoushuqun@gmail.com">Sutra Zhou</a>
 * 
 */
public class SampleServer {
	// instantiate a ServerManager object
	public ServerManager manager = new ServerManager();

	public SampleServer(String OPEndpointUrl) {
		manager.setOPEndpointUrl(OPEndpointUrl);
	}

	public void processRequest(HttpServletRequest httpReq,
			HttpServletResponse httpResp) throws Exception {
		HttpSession session = httpReq.getSession();
		ParameterList request;
		// Completing the authz and authn process by redirecting here
		if ("complete".equals(httpReq.getParameter("_action"))) {
			// On a redirect from the OP authn & authz sequence
			request = (ParameterList) session.getAttribute("parameterlist");
		} else {
			// extract the parameters from the request
			request = new ParameterList(httpReq.getParameterMap());
		}

		String mode = request.hasParameter("openid.mode") ? request
				.getParameterValue("openid.mode") : null;

		Message response;
		String responseText = null;

		if ("associate".equals(mode)) {
			// --- process an association request ---
			response = manager.associationResponse(request);
			responseText = response.keyValueFormEncoding();
		} else if ("checkid_setup".equals(mode)
				|| "checkid_immediate".equals(mode)) {
			// interact with the user and obtain data needed to continue
			// List userData = userInteraction(request);

			String userSelectedId = null;
			String userSelectedClaimedId = null;
			Boolean authenticatedAndApproved = null;

			if ((session.getAttribute("authenticatedAndApproved") == null)
					|| (!((Boolean) session
							.getAttribute("authenticatedAndApproved")))) {
				session.setAttribute("parameterlist", request);
				httpResp.sendRedirect("provider-authorization");
				return;
			} else {
				userSelectedId = (String) session
						.getAttribute("openid.claimed_id");
				userSelectedClaimedId = (String) session
						.getAttribute("openid.identity");
				authenticatedAndApproved = (Boolean) session
						.getAttribute("authenticatedAndApproved");
				// Remove the parameterlist so this provider can accept requests
				// from elsewhere
				session.removeAttribute("parameterlist");
				// Makes you authorize each and every time
				session.setAttribute("authenticatedAndApproved", false);
			}

			// --- process an authentication request ---
			response = manager.authResponse(request, userSelectedId,
					userSelectedClaimedId, authenticatedAndApproved
							.booleanValue());

			if (response instanceof DirectError)
				directResponse(httpResp, response.keyValueFormEncoding());
			else {
				// caller will need to decide which of the following to use:

				// option1: GET HTTP-redirect to the return_to URL
				// return response.getDestinationUrl(true);
				httpResp.sendRedirect(response.getDestinationUrl(true));
				return;

				// option2: HTML FORM Redirection
				// RequestDispatcher dispatcher =
				// getServletContext().getRequestDispatcher("formredirection.jsp");
				// httpReq.setAttribute("prameterMap",
				// response.getParameterMap());
				// httpReq.setAttribute("destinationUrl",
				// response.getDestinationUrl(false));
				// dispatcher.forward(request, response);
				// return null;
			}
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
		httpResp.getWriter().write(responseText);
	}

	private String directResponse(HttpServletResponse httpResp, String response)
			throws IOException {
		ServletOutputStream os = httpResp.getOutputStream();
		os.write(response.getBytes());
		os.close();

		return null;
	}
}
