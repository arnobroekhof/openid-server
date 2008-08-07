/**
 * Created on 2008-8-7 下午11:49:20
 */
package cn.net.openid.jos.web.filter;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.net.openid.jos.service.JosService;

/**
 * @author Sutra Zhou
 * 
 */
public abstract class OncePerRequestServiceFilter extends OncePerRequestFilter {
	protected final Log log = LogFactory.getLog(this.getClass());

	private JosService service;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.filter.GenericFilterBean #initFilterBean()
	 */
	@Override
	protected void initFilterBean() throws ServletException {
		super.initFilterBean();
		this.service = (JosService) WebApplicationContextUtils
				.getWebApplicationContext(this.getServletContext()).getBean(
						"josService");

	}

	protected JosService getService() {
		return this.service;
	}

}
