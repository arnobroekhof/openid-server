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
 * Created on 2008-3-5 21:40:34
 */
package cn.net.openid.jos.domain;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Persona entity. One user may have many personas.
 * 
 * @author Sutra Zhou
 */
public class Persona extends BaseEntity implements Externalizable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1223541515197309353L;

	/**
	 * The prime value for hash code calculating.
	 */
	private static final int PRIME = 31;

	/**
	 * The owner of the persona.
	 */
	private User user = new User();

	/**
	 * The display name.
	 */
	private String name;

	/**
	 * Any UTF-8 string that the End User wants to use as a nickname.
	 */
	private String nickname;

	/**
	 * The email address.
	 */
	private String email;

	/**
	 * UTF-8 string free text representation of the End User's full name.
	 */
	private String fullname;

	/**
	 * The End User's date of birth as YYYY-MM-DD. Any values whose
	 * representation uses fewer than the specified number of digits should be
	 * zero-padded. The length of this value MUST always be 10. If the End User
	 * user does not want to reveal any particular component of this value, it
	 * MUST be set to zero. For instance, if a End User wants to specify that
	 * his date of birth is in 1980, but not the month or day, the value
	 * returned SHALL be "1980-00-00".
	 */
	private String dob;

	/**
	 * The End User's gender, "M" for male, "F" for female.
	 */
	private String gender;

	/**
	 * UTF-8 string free text that SHOULD conform to the End User's country's
	 * postal system.
	 */
	private String postcode;

	/**
	 * The End User's country of residence as specified by
	 * <a href="http://www.iso.org/iso/en/prods-services/iso3166ma/index.html">
	 * ISO3166</a>.
	 */
	private String country;

	/**
	 * End User's preferred language as specified by
	 * <a href="http://www.w3.org/WAI/ER/IG/ert/iso639.htm">ISO639</a>.
	 */
	private String language;

	/**
	 * ASCII string from
	 * <a href="http://www.twinsun.com/tz/tz-link.htm">TimeZone database</a>.
	 * For example, "Europe/Paris" or "America/Los_Angeles".
	 */
	private String timezone;

	/**
	 * Attributes.
	 * 
	 * @see <a
	 *      href="http://openid.net/specs/openid-attribute-exchange-1_0.html">
	 *      OpenID Attribute Exchange 1.0</a>
	 */
	private Set<Attribute> attributes;

	/**
	 * Construct a default persona.
	 */
	public Persona() {
		this.attributes = new LinkedHashSet<Attribute>();
	}

	/**
	 * Construct a persona for the user.
	 * 
	 * @param user
	 *            the owner of the persona
	 */
	public Persona(final User user) {
		this();
		this.user = user;
	}

	/**
	 * Get the user.
	 * 
	 * @return user the owner
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Set the owner.
	 * 
	 * @param user
	 *            the owner to set
	 */
	public void setUser(final User user) {
		this.user = user;
	}

	/**
	 * Get the country.
	 * 
	 * @return country the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Set the country.
	 * 
	 * @param country
	 *            the country to set
	 */
	public void setCountry(final String country) {
		this.country = country;
	}

	/**
	 * Set the date of birth.
	 * 
	 * @return dob the date of birth
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * Set the date of birth.
	 * 
	 * @param dob
	 *            the date of birth to set
	 */
	public void setDob(final String dob) {
		this.dob = dob;
	}

	/**
	 * Get the email address.
	 * 
	 * @return email the email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the email address.
	 * 
	 * @param email
	 *            the email address to set
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * Get the full name.
	 * 
	 * @return fullname the full name
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * Set the full name.
	 * 
	 * @param fullname
	 *            the full name to set
	 */
	public void setFullname(final String fullname) {
		this.fullname = fullname;
	}

	/**
	 * Get the gender.
	 * 
	 * @return gender the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Set the gender.
	 * 
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(final String gender) {
		this.gender = gender;
	}

	/**
	 * Get the language.
	 * 
	 * @return language the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Set the preferred language.
	 * 
	 * @param language
	 *            the preferred language to set
	 */
	public void setLanguage(final String language) {
		this.language = language;
	}

	/**
	 * Get the display name of the persona.
	 * 
	 * @return name the display name of the persona
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the display name of the persona.
	 * 
	 * @param name
	 *            the display name of the persona to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Get the nickname.
	 * 
	 * @return nickname the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Set the nickname.
	 * 
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(final String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Get the post code.
	 * 
	 * @return postcode the post code
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * Set the post code.
	 * 
	 * @param postcode
	 *            the post code to set
	 */
	public void setPostcode(final String postcode) {
		this.postcode = postcode;
	}

	/**
	 * Get the timezone.
	 * 
	 * @return timezone the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * Set the timezone.
	 * 
	 * @param timezone
	 *            the timezone to set
	 */
	public void setTimezone(final String timezone) {
		this.timezone = timezone;
	}

	/**
	 * Get attributes.
	 * 
	 * @return the attributes
	 */
	public Set<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * Set the attributes.
	 * 
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(final Set<Attribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * Add attribute.
	 * 
	 * @param attribute
	 *            the attribute to add
	 */
	public void addAttribute(final Attribute attribute) {
		this.attributes.add(attribute);
	}

	/**
	 * Clear all attributes.
	 */
	public void clearAttributes() {
		this.attributes.clear();
	}

	/**
	 * Set the locale.
	 * 
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(final Locale locale) {
		this.country = locale.getCountry();
		this.language = locale.getLanguage();
	}

	/**
	 * Get the locale.
	 * 
	 * @return the locale
	 */
	public Locale getLocale() {
		return new Locale(this.language, this.country);
	}

	/**
	 * Convert the persona as a map.
	 * 
	 * @return the map contains all properties of the persona.
	 */
	public Map<String, String> toMap() {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("nickname", this.getNickname());
		ret.put("email", this.getEmail());
		ret.put("fullname", this.getFullname());
		ret.put("dob", this.getDob());
		ret.put("gender", this.getGender());
		ret.put("postcode", this.getPostcode());
		ret.put("country", this.getCountry());
		ret.put("language", this.getLanguage());
		ret.put("timezone", this.getTimezone());
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result = 1;
		result = PRIME * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Persona)) {
			return false;
		}
		final Persona other = (Persona) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		setId((String) in.readObject());
		setCreationDate((Date) in.readObject());
		setUser((User) in.readObject());
		setName((String) in.readObject());
		setNickname((String) in.readObject());
		setEmail((String) in.readObject());
		setFullname((String) in.readObject());
		setDob((String) in.readObject());
		setGender((String) in.readObject());
		setPostcode((String) in.readObject());
		setCountry((String) in.readObject());
		setLanguage((String) in.readObject());
		setTimezone((String) in.readObject());
		Attribute[] attributeArray = (Attribute[]) in.readObject();
		Set<Attribute> attributes = new LinkedHashSet<Attribute>();
		for (Attribute attribute : attributeArray) {
			attributes.add(attribute);
		}
		setAttributes(attributes);
	}

	/**
	 * {@inheritDoc}
	 */
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(getId());
		out.writeObject(getCreationDate());
		out.writeObject(getUser());
		out.writeObject(getName());
		out.writeObject(getNickname());
		out.writeObject(getEmail());
		out.writeObject(getFullname());
		out.writeObject(getDob());
		out.writeObject(getGender());
		out.writeObject(getPostcode());
		out.writeObject(getCountry());
		out.writeObject(getLanguage());
		out.writeObject(getTimezone());
		Set<Attribute> attributes = getAttributes();
		Attribute[] attributeArray = attributes
				.toArray(new Attribute[attributes.size()]);
		out.writeObject(attributeArray);
	}
}
