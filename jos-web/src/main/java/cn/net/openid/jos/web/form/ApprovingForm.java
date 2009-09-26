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
 * Created on 2008-05-18 13:36:49
 */
package cn.net.openid.jos.web.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openid4java.message.AuthRequest;

import cn.net.openid.jos.domain.Persona;

/**
 * @author Sutra Zhou
 * 
 */
public class ApprovingForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2752745018691700211L;

	private static final Log log = LogFactory.getLog(Attribute.class);

	private String token;
	private String personaId;
	private List<Attribute> attributes;
	private AuthRequest authRequest;

	/**
	 * 
	 */
	public ApprovingForm() {
		this.attributes = new ArrayList<Attribute>();
	}

	/**
	 * @return the check id request token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the check id request token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the personaId
	 */
	public String getPersonaId() {
		return personaId;
	}

	/**
	 * @param personaId
	 *            the personaId to set
	 */
	public void setPersonaId(String personaId) {
		this.personaId = personaId;
	}

	/**
	 * @return the authRequest
	 */
	public AuthRequest getAuthRequest() {
		return authRequest;
	}

	/**
	 * @param authRequest
	 *            the authRequest to set
	 */
	public void setAuthRequest(AuthRequest authRequest) {
		this.authRequest = authRequest;
	}

	public class Attribute {

		private String name;
		private String value;
		private boolean isRequired;
		private String label;

		/**
		 * 
		 */
		public Attribute() {
		}

		/**
		 * @param name
		 * @param persona
		 * @param isRequired
		 * @param label
		 */
		public Attribute(String name, Persona persona, boolean isRequired,
				String label) {
			this.name = name;
			this.isRequired = isRequired;
			this.label = label;
			if (persona != null) {
				if (name.equals("email")) {
					this.value = persona.getEmail();
				} else if (name.equals("country")) {
					this.value = persona.getCountry();
				} else if (name.equals("fullname")) {
					this.value = persona.getFullname();
				} else if (name.equals("gender")) {
					this.value = persona.getGender();
				} else if (name.equals("language")) {
					this.value = persona.getLanguage();
				} else if (name.equals("nickname")) {
					this.value = persona.getNickname();
				} else if (name.equals("postcode")) {
					this.value = persona.getPostcode();
				} else if (name.equals("timezone")) {
					this.value = persona.getTimezone();
				}
			}
			log.debug("attribute name & value is: " + this.name + "="
					+ this.value);
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @param value
		 *            the value to set
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * @return the isRequired
		 */
		public boolean isRequired() {
			return isRequired;
		}

		/**
		 * @param isRequired
		 *            the isRequired to set
		 */
		public void setRequired(boolean isRequired) {
			this.isRequired = isRequired;
		}

		/**
		 * @return the label
		 */
		public String getLabel() {
			return label;
		}

		/**
		 * @param label
		 *            the label to set
		 */
		public void setLabel(String label) {
			this.label = label;
		}
	}

	/**
	 * @return the attributes
	 */
	public List<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
