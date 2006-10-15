/**
 * Created on 2006-10-15 下午05:11:24
 */
package cn.net.openid;

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

	private String openid;

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
	private char gender;

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

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob
	 *            the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the fullname
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * @param fullname
	 *            the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * @return the gender
	 */
	public char getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(char gender) {
		this.gender = gender;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @param openid
	 *            the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * @return the postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @param postcode
	 *            the postcode to set
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone
	 *            the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

}
