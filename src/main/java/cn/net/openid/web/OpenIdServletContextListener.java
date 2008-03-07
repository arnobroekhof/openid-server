/**
 * Created on 2006-11-7 下午11:34:02
 */
package cn.net.openid.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.net.openid.OpenIdConfiguration;

/**
 * @author Shutra
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
				.getInitParameter(OpenIdConfiguration.CONFIGURATION_BEAN_NAME);
		String openIdConfigurationAttributeName = sc
				.getInitParameter(OpenIdConfiguration.CONFIGURATION_ATTRIBUTE_NAME);
		OpenIdConfiguration openIdConfiguration = (OpenIdConfiguration) WebApplicationContextUtils
				.getWebApplicationContext(sc).getBean(
						openIdConfigurationBeanName);
		sce.getServletContext().setAttribute(openIdConfigurationAttributeName,
				openIdConfiguration);
	}
}
