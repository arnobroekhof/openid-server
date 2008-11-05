/**
 * Created on 2008-6-26 01:47:18
 */
package cn.net.openid.jos.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * When I using <xmp> <div id="hl">
 * <ul>
 * #foreach($l in $request.locales)
 * <li><a href="?hl=$l#if($!request.getParameter('self'))&amp;self=$!request.getParameter('self')#end"
 * >$l.getDisplayName($l)</a></li>
 * #end <li class="last-child"><a href=
 * "hl#if($!request.getParameter('self'))?self=$!request.getParameter('self')#end"
 * >»</a></li>
 * </ul>
 * </div> </xmp>cause a warning:
 * 
 * <pre>
 * [org.apache.velocity.app.VelocityEngine:46] - Warning! The iterative  is an Enumeration in the #foreach() loop at [0,0] in template header.vm. Because it's not resetable, if used in more than once, this may lead to unexpected results.
 * </pre>
 * 
 * So I create this filter.
 * <p>
 * Get all locales from user agent and retain all that in the human language
 * selection list.
 * </p>
 * 
 * @author Sutra Zhou
 * 
 */
public class UserAgentLocalesFilter extends OncePerRequestServiceFilter {
	private static final String USER_AGENT_LOCALES = "userAgentLocales";

	@SuppressWarnings("unchecked")
	public static Collection<Locale> getUserAgentLocales(ServletRequest request) {
		return (Collection<Locale>) request.getAttribute(USER_AGENT_LOCALES);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		request.setAttribute(USER_AGENT_LOCALES, this.getLocales(request));
		filterChain.doFilter(request, response);
	}

	/**
	 * Get locales from the HTTP request, and removed all that not is
	 * unavailable.
	 * 
	 * @param request
	 *            the HTTP request.
	 * @return a collection
	 */
	@SuppressWarnings("unchecked")
	private Collection<Locale> getLocales(HttpServletRequest request) {
		Enumeration<Locale> localesEnum = request.getLocales();
		Collection<Locale> localesCollection = new ArrayList<Locale>();
		while (localesEnum.hasMoreElements()) {
			localesCollection.add(localesEnum.nextElement());
		}
		localesCollection.retainAll(this.getService().getAvailableLocales());
		return localesCollection;
	}
}
