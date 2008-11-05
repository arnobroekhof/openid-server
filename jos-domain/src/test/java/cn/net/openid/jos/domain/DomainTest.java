/**
 * Created on 2008-8-13 12:15:55
 */
package cn.net.openid.jos.domain;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Sutra Zhou
 * 
 */
public class DomainTest {
	private Domain domain;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		domain = new Domain();
	}

	/**
	 * Test method for
	 * {@link cn.net.openid.jos.domain.Domain#getIdentifierPrefix()}.
	 * 
	 * @throws MalformedURLException
	 */
	@Test
	public void testGetIdentifierPrefix() throws MalformedURLException {
		URL url = new URL("http://example1.com/jos-webapp/");
		domain.getRuntime().setServerBaseUrl(url);
		domain.setName("example1.com");
		domain.setType(Domain.TYPE_SUBDOMAIN);
		assertEquals("http://", domain.getIdentifierPrefix());
	}

	/**
	 * Test method for
	 * {@link cn.net.openid.jos.domain.Domain#getIdentifierPrefix()}.
	 * 
	 * @throws MalformedURLException
	 */
	@Test
	public void testGetIdentifierPrefixSubdirectory()
			throws MalformedURLException {
		URL url = new URL("http://example1.com/jos-webapp/");
		domain.getRuntime().setServerBaseUrl(url);
		domain.setName("example1.com");
		domain.setType(Domain.TYPE_SUBDIRECTORY);
		assertEquals("http://example1.com/jos-webapp/", domain
				.getIdentifierPrefix());

		domain.setMemberPath("abc");
		assertEquals("http://example1.com/jos-webapp/abc/", domain
				.getIdentifierPrefix());
	}

	/**
	 * Test method for
	 * {@link cn.net.openid.jos.domain.Domain#getIdentifierPrefix()}.
	 * 
	 * @throws MalformedURLException
	 */
	@Test
	public void testGetIdentifierPrefixSubdirectoryRoot()
			throws MalformedURLException {
		URL url = new URL("http://example1.com/");
		domain.getRuntime().setServerBaseUrl(url);
		domain.setName("example1.com");
		domain.setType(Domain.TYPE_SUBDIRECTORY);
		assertEquals("http://example1.com/", domain.getIdentifierPrefix());

		domain.setMemberPath("abc");
		assertEquals("http://example1.com/abc/", domain.getIdentifierPrefix());
	}

	/**
	 * Test method for
	 * {@link cn.net.openid.jos.domain.Domain#getIdentifierSuffix()}.
	 * 
	 * @throws MalformedURLException
	 */
	@Test
	public void testGetIdentifierSuffix() throws MalformedURLException {
		URL url = new URL("http://example1.com/jos-webapp/");
		domain.getRuntime().setServerBaseUrl(url);
		domain.setName("example1.com");
		domain.setType(Domain.TYPE_SUBDOMAIN);
		assertEquals(".example1.com/jos-webapp/", domain.getIdentifierSuffix());

		url = new URL("http://example1.com:80/jos-webapp/");
		domain.getRuntime().setServerBaseUrl(url);
		domain.setName("example1.com");
		domain.setType(Domain.TYPE_SUBDOMAIN);
		assertEquals(".example1.com/jos-webapp/", domain.getIdentifierSuffix());
	}

}
