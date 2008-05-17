/**
 * 
 */
package cn.net.openid.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.net.openid.dao.DaoFacade;

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
		DaoFacade daoFacade = (DaoFacade) WebApplicationContextUtils
				.getWebApplicationContext(sce.getServletContext()).getBean(
						"daoFacade");
		sce.getServletContext().setAttribute("josConfiguration",
				daoFacade.getJosConfiguration());
	}
}
