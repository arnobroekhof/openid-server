/**
 * Created on 2008-9-7 上午08:59:10
 */
package cn.net.openid.jos.web.sitemesh;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.RequestConstants;
import com.opensymphony.module.sitemesh.util.OutputConverter;

/**
 * @author Sutra Zhou
 * @com.opensymphony.module.sitemesh.velocity.VelocityDecoratorServlet
 */
public class DecoratorController implements Controller {
	private String view;

	/**
	 * @param view
	 *            the view to set
	 */
	public void setView(String view) {
		this.view = view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> context = new HashMap<String, Object>();

		HTMLPage htmlPage = (HTMLPage) request
				.getAttribute(RequestConstants.PAGE);
		context.put("base", request.getContextPath());

		// For backwards compatability with apps that used the old
		// VelocityDecoratorServlet
		// that extended VelocityServlet instead of VelocityViewServlet
		context.put("req", request);
		context.put("res", response);

		if (htmlPage == null) {
			context.put("title", "Title?");
			context.put("body", "<p>Body?</p>");
			context.put("head", "<!-- head -->");
		} else {
			context.put("title", OutputConverter.convert(htmlPage.getTitle()));
			{
				StringWriter buffer = new StringWriter();
				htmlPage.writeBody(OutputConverter.getWriter(buffer));
				context.put("body", buffer.toString());
			}
			{
				StringWriter buffer = new StringWriter();
				htmlPage.writeHead(OutputConverter.getWriter(buffer));
				context.put("head", buffer.toString());
			}
			context.put("page", htmlPage);
		}

		return new ModelAndView(this.view).addAllObjects(context);
	}
}
