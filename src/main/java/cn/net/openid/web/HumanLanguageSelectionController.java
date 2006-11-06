/**
 * Created on 2006-11-6 下午09:14:44
 */
package cn.net.openid.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Human language selection controller.
 * 
 * @author Shutra
 * 
 */
public class HumanLanguageSelectionController extends AbstractController {
	private static Locale[] locales = Locale.getAvailableLocales();

	private ModelAndView modelAndView;

	private LocaleResolver localeResolver;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("locales", locales);
		request.setAttribute("locale", this.localeResolver
				.resolveLocale(request));
		return modelAndView;
	}

	public void setFormView(String formView) {
		this.modelAndView = new ModelAndView(formView);
	}

	/**
	 * @param localeResolver
	 *            the localeResolver to set
	 */
	public void setLocaleResolver(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}

}
