/**
 * Created on 2008-6-26 上午01:47:18
 */
package cn.net.openid.jos.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * When I using <xmp> <div id="hl">
 * <ul>
 * #foreach($l in $request.locales)
 * <li><a
 * href="?hl=$l#if($!request.getParameter('self'))&amp;self=$!request.getParameter('self')#end">$l.getDisplayName($l)</a></li>
 * #end
 * <li class="last-child"><a
 * href="hl#if($!request.getParameter('self'))?self=$!request.getParameter('self')#end">»</a></li>
 * </ul>
 * </div> </xmp>cause a warning:
 * 
 * <pre>
 * [org.apache.velocity.app.VelocityEngine:46] - Warning! The iterative  is an Enumeration in the #foreach() loop at [0,0] in template header.vm. Because it's not resetable, if used in more than once, this may lead to unexpected results.
 * </pre>
 * 
 * So I create this filter.
 * 
 * @author Sutra Zhou
 * 
 */
public class UserAgentLocalesFilter implements Filter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		Enumeration<Locale> localesEnum = request.getLocales();
		Collection<Locale> localesCollection = new ArrayList<Locale>();
		while (localesEnum.hasMoreElements()) {
			localesCollection.add(localesEnum.nextElement());
		}
		request.setAttribute("userAgentLocales", localesCollection);

		chain.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
