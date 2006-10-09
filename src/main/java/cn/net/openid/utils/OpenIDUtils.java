/*
 * Created on 2006-10-5 下午08:43:58
 */
package cn.net.openid.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

public class OpenIDUtils {
	static Log log = LogFactory.getLog(OpenIDUtils.class);

	protected static String timestampFormatString = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	protected static String terseLocalDateformatString = "MM/dd/yy HH:mm:ss z";

	public static SimpleDateFormat getTimestampFormat() {
		SimpleDateFormat format = new SimpleDateFormat(timestampFormatString);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		return format;
	}

	public static Date decodeTimestampFormat(String timestamp)
			throws ParseException {
		return getTimestampFormat().parse(timestamp);
	}

	public static String encodeTimestampFormat(Date date) {
		return getTimestampFormat().format(date);
	}

	public static String formatTerseLocalDate(Date date) {
		return new SimpleDateFormat(terseLocalDateformatString).format(date);
	}

	public static Date zeroMillis(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/***************************************************************************
	 * Returns a value map containing all of the request parameters (or only
	 * parameters with names beginning with prefix, if specified) in the
	 * provided servlet request. Strips the prefix from the names.
	 * 
	 * @param servletRequest
	 * @return
	 */
	public static HashMap servletRequestToValueMap(String prefix,
			HttpServletRequest servletRequest) {
		HashMap<String, String> valueMap = new HashMap<String, String>();
		Enumeration parameterNamesEnumeration = servletRequest
				.getParameterNames();
		while (parameterNamesEnumeration.hasMoreElements()) {
			String name = (String) parameterNamesEnumeration.nextElement();
			if (prefix == null || name.startsWith(prefix)) {
				valueMap.put(name.substring(((prefix != null) ? prefix + "."
						: "").length()), servletRequest.getParameter(name)
						.trim());
			}
		}
		return valueMap;
	}

	/***************************************************************************
	 * Returns a value map containing all of the properties (or only properties
	 * with names beginning with prefix, if specified) in the provided
	 * Properties. Strips the prefix from the names.
	 * 
	 * @return
	 */
	public static HashMap propertiesToValueMap(String prefix,
			Properties properties) {
		HashMap<String, String> valueMap = new HashMap<String, String>();
		Enumeration propertyNames = properties.propertyNames();
		while (propertyNames.hasMoreElements()) {
			String name = (String) propertyNames.nextElement();
			if (prefix == null || name.startsWith(prefix)) {
				valueMap.put(name.substring(((prefix != null) ? prefix + "."
						: "").length()), properties.getProperty(name).trim());
			}
		}
		return valueMap;
	}

	/***************************************************************************
	 * Return a query string containing all of the name/value pairs in the
	 * specificed map If prefix is specified, adds "[prefix]." to the beginning
	 * of every value name, to ensure that it intermingles safely with other
	 * parameters
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String valueMapToQueryString(String prefix, HashMap map,
			String characterEncoding) throws UnsupportedEncodingException {
		StringBuffer output = new StringBuffer();
		Iterator iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String name = (String) iterator.next();
			if (output.length() > 0) {
				output.append("&");
			}
			output.append(((prefix != null) ? prefix + "." : "") + name)
					.append("=").append(
							URLEncoder.encode((String) map.get(name),
									characterEncoding));
		}
		return output.toString();
	}

	public static String appendValueMapToURL(String prefix, String URL,
			HashMap map, String characterEncoding)
			throws UnsupportedEncodingException {
		String output = URL;
		if (map.size() > 0) {
			String separator = (URL.indexOf("?") > -1) ? "&" : "?";
			output += separator
					+ valueMapToQueryString(prefix, map, characterEncoding);
		}
		return output;
	}

	public static HashMap retrieveImplementationClassesFromProperties(
			String propertyKey, Class superclass) {
		Properties sysProps = System.getProperties();
		Enumeration sysPropNames = sysProps.propertyNames();
		HashMap<String, Class> propDefinedImpls = new HashMap<String, Class>();
		while (sysPropNames.hasMoreElements()) {
			String name = (String) sysPropNames.nextElement();
			if (name.toLowerCase().startsWith(propertyKey)
					&& name.length() > propertyKey.length() + 1) {
				String assocTypeIdentifier = name.substring(propertyKey
						.length() + 1);
				String implClassName = sysProps.getProperty(name);
				try {
					Class assocImplClass = Class.forName(implClassName);
					Object instance = assocImplClass.newInstance();
					if (superclass.isInstance(instance)) {
						propDefinedImpls.put(assocTypeIdentifier,
								assocImplClass);
					} else {
						log.warn("Class " + implClassName
								+ " specified in property " + name
								+ " but does not extend "
								+ superclass.getName());
					}
				} catch (ClassNotFoundException e) {
					log
							.warn("Could not find class " + implClassName
									+ " specified in property " + name
									+ " as implementaton of "
									+ superclass.getName(), e);
				} catch (Exception e) {
					log.warn("Error instantiating class " + implClassName
							+ " specified in property " + name
							+ " as implementation of " + superclass.getName(),
							e);
				}
			}
		}
		return propDefinedImpls;
	}

	/***************************************************************************
	 * Obtain a URL to the root of the context that serviced the given request.
	 * 
	 * @return absolute URL matching scheme, servername, port, and context path
	 *         of the given request
	 */
	public static String getBaseURL(HttpServletRequest request) {
		String url = request.getScheme() + "://" + request.getServerName();
		if (request.getLocalPort() != 80) {
			url += ":" + request.getLocalPort();
		}
		if (request.getContextPath() != null) {
			url += request.getContextPath();
		}
		return url;
	}

	/***************************************************************************
	 * Obtain a URL to the servlet that serviced the given request.
	 * 
	 * @return absolute URL matching scheme, servername, port, context path, and
	 *         servlet path of the request
	 */
	public static String getServletPath(HttpServletRequest request) {
		String url = getBaseURL(request);
		url += request.getServletPath();
		return url;
	}

	public static byte[] xorByteArrays(byte[] a, byte[] b) {
		byte[] result = new byte[a.length];
		for (int i = 0; i < a.length; i++) {
			result[i] = (byte) (a[i] ^ b[i]);
		}
		return result;
	}

	public static String getContextPath(
			javax.servlet.http.HttpServletRequest request) {
		String ctx = request.getContextPath();
		if (ctx.equals("/")) {
			return "";
		} else {
			return ctx;
		}
	}

	public static String getFirstValue(Map<String, String[]> map, String key) {
		String[] ss = map.get(key);
		if (ss != null && ss.length > 0) {
			return ss[0];
		} else {
			return null;
		}
	}
}
