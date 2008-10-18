/**
 * 
 */
package cn.net.openid.jos.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sutra
 * 
 */
public class PersonaTest {
	private Persona persona;
	private Attribute attribute;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		persona = new Persona();
		Attribute attribute = new Attribute();
		attribute.setId("test Id");
		attribute.setAlias("testAlias");
		attribute.setType("testType");
		attribute.addValue("testValue");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link cn.net.openid.jos.domain.Persona#getAttributes()}.
	 */
	@Test
	public void testGetAttributes() {
		assertNotNull(persona.getAttributes());
		persona.addAttribute(attribute);
		assertEquals(1, persona.getAttributes().size());
	}

	/**
	 * Test method for
	 * {@link cn.net.openid.jos.domain.Persona#setAttributes(java.util.Set)}.
	 */
	@Test
	public void testSetAttributes() {
		Set<Attribute> attributes = new HashSet<Attribute>();
		attributes.add(attribute);
		persona.setAttributes(attributes);
		assertEquals(1, persona.getAttributes().size());
	}
}
