/**
 * Created on 2006-11-7 下午11:34:02
 */
package cn.net.openid.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.net.openid.JosConfiguration;

/**
 * @author Sutra Zhou
 * 
 */
public class OpenIdServletContextListener implements ServletContextListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		String openIdConfigurationBeanName = sc
				.getInitParameter(JosConfiguration.CONFIGURATION_BEAN_NAME);
		String openIdConfigurationAttributeName = sc
				.getInitParameter(JosConfiguration.CONFIGURATION_ATTRIBUTE_NAME);
		JosConfiguration openIdConfiguration = (JosConfiguration) WebApplicationContextUtils
				.getWebApplicationContext(sc).getBean(
						openIdConfigurationBeanName);
		sce.getServletContext().setAttribute(openIdConfigurationAttributeName,
				openIdConfiguration);
	}
}
