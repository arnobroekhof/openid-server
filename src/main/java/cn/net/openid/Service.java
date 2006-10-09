/**
 * Created on 2006-10-5 下午11:14:51
 */
package cn.net.openid;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shutra
 * 
 */
public interface Service {
	void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException;
}
