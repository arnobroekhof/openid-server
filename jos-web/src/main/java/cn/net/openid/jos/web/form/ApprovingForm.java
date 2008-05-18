/**
 * Created on 2008-05-18 13:36:49
 */
package cn.net.openid.jos.web.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sutra Zhou
 * 
 */
public class ApprovingForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2752745018691700211L;

	private String token;
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
		 * @param value
		 * @param isRequired
		 * @param label
		 */
		public Attribute(String name, String value, boolean isRequired,
				String label) {
			this.name = name;
			this.value = value;
			this.isRequired = isRequired;
			this.label = label;
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
}
