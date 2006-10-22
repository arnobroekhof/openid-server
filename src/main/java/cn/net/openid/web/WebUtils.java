/**
 * Created on 2006-10-22 下午07:12:00
 */
package cn.net.openid.web;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shutra
 * 
 */
public class WebUtils {
	public static String getContextPath(HttpServletRequest req) {
		String ret = req.getContextPath();
		if (ret.equals("/")) {
			return "";
		} else {
			return ret;
		}
	}
}
