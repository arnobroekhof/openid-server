/**
 * Created on 2006-10-15 下午05:11:24
 */
package cn.net.openid.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Shutra
 * 
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6219139356897428716L;

	private String id;

	private String username;

	/**
	 * Any UTF-8 string that the End User wants to use as a nickname.
	 */
	private String nickname;

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
	private Date dob;

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
	 * The End User's country of residence as specified by <a
	 * href="http://www.iso.org/iso/en/prods-services/iso3166ma/index.html">ISO3166</a>.
	 */
	private String country;

	/**
	 * End User's preferred language as specified by <a
	 * href="http://www.w3.org/WAI/ER/IG/ert/iso639.htm">ISO639</a>.
	 */
	private String language;

	/**
	 * ASCII string from <a
	 * href="http://www.twinsun.com/tz/tz-link.htm">TimeZone database</a> For
	 * example, "Europe/Paris" or "America/Los_Angeles".
	 */
	private String timezone;

	private Date registerTime = new Date();

	/**
	 * @return the country
	 * @deprecated {@link Persona}
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return the dob
	 * @deprecated {@link Persona}
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @return the email
	 * @deprecated {@link Persona}
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the fullname
	 * @deprecated {@link Persona}
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * @return the gender
	 * @deprecated {@link Persona}
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the language
	 * @deprecated {@link Persona}
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @return the nickname
	 * @deprecated {@link Persona}
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @return the postcode
	 * @deprecated {@link Persona}
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @return the registerTime
	 * @deprecated {@link Persona}
	 */
	public Date getRegisterTime() {
		return registerTime;
	}

	/**
	 * @return the timezone
	 * @deprecated {@link Persona}
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param country
	 *            the country to set
	 * @deprecated {@link Persona}
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @param dob
	 *            the dob to set
	 * @deprecated {@link Persona}
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @param email
	 *            the email to set
	 * @deprecated {@link Persona}
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param fullname
	 *            the fullname to set
	 * @deprecated {@link Persona}
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * @param gender
	 *            the gender to set
	 * @deprecated {@link Persona}
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param language
	 *            the language to set
	 * @deprecated {@link Persona}
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @param nickname
	 *            the nickname to set
	 * @deprecated {@link Persona}
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @param postcode
	 *            the postcode to set
	 * @deprecated {@link Persona}
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * @param registerTime
	 *            the registerTime to set
	 * @deprecated {@link Persona}
	 */
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	/**
	 * @param timezone
	 *            the timezone to set
	 * @deprecated {@link Persona}
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
