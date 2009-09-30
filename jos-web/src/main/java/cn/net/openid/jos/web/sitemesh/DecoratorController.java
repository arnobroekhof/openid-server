/**
 * Copyright (c) 2006-2009, Redv.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Redv.com nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * Created on 2008-9-7 08:59:10
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
	/**
	 * The view.
	 */
	private String view;

	/**
	 * @param view
	 *            the view to set
	 */
	public void setView(final String view) {
		this.view = view;
	}

	/**
	 * {@inheritDoc}
	 */
	public ModelAndView handleRequest(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
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

			StringWriter buffer;

			buffer = new StringWriter();
			htmlPage.writeBody(OutputConverter.getWriter(buffer));
			context.put("body", buffer.toString());

			buffer = new StringWriter();
			htmlPage.writeHead(OutputConverter.getWriter(buffer));
			context.put("head", buffer.toString());
			context.put("page", htmlPage);
		}

		return new ModelAndView(this.view).addAllObjects(context);
	}
}
