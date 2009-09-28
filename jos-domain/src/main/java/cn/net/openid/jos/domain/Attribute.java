/**
 * Copyright (c) 2006-2009, Redv.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Redv.com nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * Created on 2008-3-24 23:13:38
 */
package cn.net.openid.jos.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the extended attribute of {@link Persona}.
 * 
 * @author Sutra Zhou
 */
public class Attribute extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2109982803475487846L;

	private static final int PRIME = 31;

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
	public Attribute(final Persona persona, final String alias,
			final String type, final List<String> values) {
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
	public Attribute(final Persona persona, final String alias,
			final String type, final String[] values) {
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
		int result = 1;
		result = PRIME * result
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
