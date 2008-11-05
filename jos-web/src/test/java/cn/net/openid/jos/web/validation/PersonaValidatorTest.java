/**
 * Created on 2008-9-15 02:00:03
 */
package cn.net.openid.jos.web.validation;

import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;

/**
 * @author Sutra Zhou
 * 
 */
public class PersonaValidatorTest {
	@Test
	public void testEmail() {
		String p = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern pattern = Pattern.compile(p);
		assertTrue(pattern.matcher("abc@example.com").matches());
	}

}
