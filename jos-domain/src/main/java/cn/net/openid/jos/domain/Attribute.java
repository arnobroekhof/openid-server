/**
 * Created on 2008-3-24 23:13:38
 */
package cn.net.openid.jos.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sutra Zhou
 * 
 */
public class Attribute extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2109982803475487846L;

	private Persona persona;
	private String alias;
	private String type;
	private List<String> values;

	/**
	 * Initializes an attribute.
	 */
	public Attribute() {
		this((Persona) null, (String) null, (String) null,
				new ArrayList<String>());
	}

	/**
	 * Initializes an attribute.
	 * 
	 * @param persona
	 *            the persona
	 * @param alias
	 *            the alias
	 * @param type
	 *            the type uri
	 * @param values
	 *            the values
	 */
	public Attribute(Persona persona, String alias, String type,
			List<String> values) {
		super();
		this.persona = persona;
		this.alias = alias;
		this.type = type;
		this.values = values;
	}

	/**
	 * Initializes an attribute.
	 * 
	 * @param persona
	 *            the persona
	 * @param alias
	 *            the alias
	 * @param type
	 *            the type uri
	 * @param values
	 *            the values
	 */
	public Attribute(Persona persona, String alias, String type, String[] values) {
		this(persona, alias, type, Arrays.asList(values));
	}

	/**
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * @param persona
	 *            the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 *            the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the values
	 */
	public List<String> getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(List<String> values) {
		this.values = values;
	}

	public void addValue(String value) {
		this.values.add(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getAlias() == null) ? 0 : getAlias().hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Attribute))
			return false;
		final Attribute other = (Attribute) obj;
		if (getAlias() == null) {
			if (other.getAlias() != null)
				return false;
		} else if (!getAlias().equals(other.getAlias()))
			return false;
		return true;
	}

}
