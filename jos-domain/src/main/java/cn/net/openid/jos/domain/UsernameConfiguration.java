/**
 * Created on 2008-05-18 06:09:04
 */
package cn.net.openid.jos.domain;

import java.util.regex.Pattern;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Sutra Zhou
 * 
 */
public class UsernameConfiguration extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -422179045234771173L;

	private Domain domain;

	/* Username patterns */
	private String regex;
	private String reservedRegex;
	private String unallowableRegex;

	private Pattern pattern;
	private Pattern reservedPattern;
	private Pattern unallowablePattern;

	public UsernameConfiguration() {
		String regex = "[a-z]{1,16}";
		this.setRegex(regex);
		regex = "root|toor|wheel|staff|admin|administrator";
		this.setReservedRegex(regex);
		regex = "w+|home|server|approve.*|approving|register|login|logout|email.*|password.*|persona.*|site.*|attribute.*|hl|member|news|jos|mail|smtp|pop3|pop|.*fuck.*";
		this.setUnallowableRegex(regex);
	}

	/**
	 * @return the domain
	 */
	public Domain getDomain() {
		return domain;
	}

	/**
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	/**
	 * @return the usernameRegex
	 */
	public String getRegex() {
		return regex;
	}

	/**
	 * @param regex
	 *            the regex to set
	 */
	public void setRegex(String regex) {
		this.regex = regex;
		this.pattern = regex != null ? Pattern.compile(regex) : null;
	}

	/**
	 * @return the reservedRegex
	 */
	public String getReservedRegex() {
		return reservedRegex;
	}

	/**
	 * @param reservedRegex
	 *            the reservedRegex to set
	 */
	public void setReservedRegex(String reservedRegex) {
		this.reservedRegex = reservedRegex;
		this.reservedPattern = reservedRegex != null ? Pattern.compile(
				reservedRegex, Pattern.CASE_INSENSITIVE) : null;
	}

	/**
	 * @return the unallowableRegex
	 */
	public String getUnallowableRegex() {
		return unallowableRegex;
	}

	/**
	 * @param unallowableRegex
	 *            the unallowableRegex to set
	 */
	public void setUnallowableRegex(String unallowableRegex) {
		this.unallowableRegex = unallowableRegex;
		this.unallowablePattern = unallowableRegex != null ? Pattern.compile(
				unallowableRegex, Pattern.CASE_INSENSITIVE) : null;
	}

	/**
	 * @return the pattern
	 */
	public Pattern getPattern() {
		return pattern;
	}

	/**
	 * @return the reservedPattern
	 */
	public Pattern getReservedPattern() {
		return reservedPattern;
	}

	/**
	 * @return the unallowablePattern
	 */
	public Pattern getUnallowablePattern() {
		return unallowablePattern;
	}

	/**
	 * Determines if the username is permissible as the username in this domain
	 * username configuration.
	 * 
	 * @param username
	 * @return true if the username is permissible as the username in this
	 *         domain username configuration.
	 */
	public boolean isUsername(String username) {
		return this.getPattern() != null ? this.getPattern().matcher(username)
				.matches() : false;
	}

	/**
	 * Determines if the username is reserved.
	 * 
	 * @param username
	 *            the username
	 * @return true if reserved, otherwise false.
	 */
	public boolean isReserved(String username) {
		return this.getReservedPattern() != null ? this.getReservedPattern()
				.matcher(username).matches() : false;
	}

	/**
	 * Determines if the username is unallowable.
	 * 
	 * @param username
	 *            the username
	 * @return true if unallowable, otherwise false.
	 */
	public boolean isUnallowable(String username) {
		return this.getUnallowablePattern() != null ? this
				.getUnallowablePattern().matcher(username).matches() : false;
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
