/**
 * 
 */
package cn.net.openid.jos.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.net.openid.jos.service.JosService;

/**
 * @author sutra
 * 
 */
public class JosServletContextListener implements ServletContextListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		JosService josService = WebUtils.getJosService(sce.getServletContext());
		sce.getServletContext().setAttribute("josConfiguration",
				josService.getJosConfiguration());
	}
}
