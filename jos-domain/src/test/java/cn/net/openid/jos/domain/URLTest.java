/**
 * Created on 2008-8-13 12:40:15
 */
package cn.net.openid.jos.domain;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

/**
 * @author Sutra Zhou
 * 
 */
public class URLTest {
	@Test
	public void testURLPort() throws MalformedURLException {
		// http with default port
		URL url = new URL("http://www.example.com/");
		assertEquals(-1, url.getPort());
		assertEquals(80, url.getDefaultPort());

		// http with default port specified
		url = new URL("http://www.example.com:80/");
		assertEquals(80, url.getPort());
		assertEquals(80, url.getDefaultPort());

		// http with non-default port
		url = new URL("http://www.example.com:8080/");
		assertEquals(8080, url.getPort());
		assertEquals(80, url.getDefaultPort());

		// https with default port
		url = new URL("https://www.example.com/");
		assertEquals(-1, url.getPort());
		assertEquals(443, url.getDefaultPort());

		// https with default port specified
		url = new URL("https://www.example.com:443/");
		assertEquals(443, url.getPort());
		assertEquals(443, url.getDefaultPort());

		// https with non-default port
		url = new URL("https://www.example.com:8443/");
		assertEquals(8443, url.getPort());
		assertEquals(443, url.getDefaultPort());
	}

}
