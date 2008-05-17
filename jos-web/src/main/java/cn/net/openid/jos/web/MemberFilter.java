/**
 * Created on 2006-10-18 下午11:49:06
 */
package cn.net.openid.jos.web;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.net.openid.jos.dao.DaoFacade;

/**
 * @author Sutra Zhou
 * 
 */
public class MemberFilter implements Filter {
	private Log log = LogFactory.getLog(this.getClass());

	private ServletContext context;

	private Pattern fromPattern;

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
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		StringBuffer url = req.getRequestURL();
		log.debug("url: " + url);

		Matcher matcher = this.fromPattern.matcher(url);
		if (matcher.find()) {
			String path = "/member/" + matcher.group(1);// ->/member/$1
			log.debug("path: " + path);
			RequestDispatcher rd = this.context.getRequestDispatcher(path);
			rd.forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.context = filterConfig.getServletContext();
		DaoFacade daoFacade = (DaoFacade) WebApplicationContextUtils
				.getWebApplicationContext(this.context).getBean("daoFacade");
		log.debug("fromPattern: "
				+ daoFacade.getJosConfiguration().getMemberFilterFromPattern());
		this.fromPattern = Pattern.compile(daoFacade.getJosConfiguration()
				.getMemberFilterFromPattern(), Pattern.CASE_INSENSITIVE);
	}

}
