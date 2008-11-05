/**
 * Created on 2008-9-14 12:59:29
 */
package cn.net.openid.jos.web.filter;

import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;

/**
 * @author Sutra Zhou
 * 
 */
public class DomainFilterTest {
	@Test
	public void testPattern() {
		Pattern p = Pattern
				.compile("^/(robots\\.txt|.*\\.(css|js|ico|png|gif)|domain-configurator|domain-configurator-login|hl)(;jsessionid=.*)?$");

		assertTrue(p.matcher("/robots.txt").matches());
		assertTrue(p.matcher("/robots.txt;jsessionid=abc123").matches());

		assertTrue(p.matcher("/styles/domain-configurator.css").matches());
		assertTrue(p.matcher(
				"/styles/domain-configurator.css;jsessionid=abc123").matches());

		assertTrue(p.matcher("/scripts/domain-configurator.js").matches());
		assertTrue(p.matcher(
				"/scripts/domain-configurator.js;jsessionid=abc123").matches());

		assertTrue(p.matcher("/images/login-bg.gif").matches());
		assertTrue(p.matcher("/images/login-bg.gif;jsessionid=abc123")
				.matches());

		assertTrue(p.matcher("/domain-configurator").matches());
		assertTrue(p.matcher("/domain-configurator;jsessionid=abc123")
				.matches());

		assertTrue(p.matcher("/domain-configurator-login").matches());
		assertTrue(p.matcher("/domain-configurator-login;jsessionid=abc123")
				.matches());

		assertTrue(p.matcher("/hl").matches());
		assertTrue(p.matcher("/hl;jsessionid=abc123").matches());
	}
}
