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

	/**
	 * 
	 */
	public ApprovingForm() {
		this.attributes = new ArrayList<Attribute>();
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
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
