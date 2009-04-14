/**
 * Created on 2006-11-6 21:14:44
 */
package cn.net.openid.jos.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.web.AbstractJosController;

/**
 * Human language selection controller.
 * 
 * @author Sutra Zhou
 */
public class HumanLanguageSelectionController extends AbstractJosController {
	private ModelAndView modelAndView;

	/**
	 * {@inheritDoc}
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("locales", this.getJosService()
				.getAvailableLocales());
		return modelAndView;
	}

	public void setFormView(String formView) {
		this.modelAndView = new ModelAndView(formView);
	}
}
