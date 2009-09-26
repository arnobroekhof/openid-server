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

	/**
	 * The domain.
	 */
	private Domain domain;

	/* Username patterns */
	/**
	 * The regular expression of the username.
	 */
	private String regex;

	/**
	 * The reserved regular expression of the username.
	 */
	private String reservedRegex;

	/**
	 * The unallowedable regular expression of the username.
	 */
	private String unallowableRegex;

	/**
	 * The compiled pattern.
	 */
	private Pattern pattern;

	/**
	 * The compiled reserved pattern.
	 */
	private Pattern reservedPattern;

	/**
	 * The compiled unallowable pattern.
	 */
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
	 * Get the domain.
	 * 
	 * @return the domain
	 */
	public Domain getDomain() {
		return domain;
	}

	/**
	 * Set the domain.
	 * 
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	/**
	 * Get the regular expression of the username.
	 * 
	 * @return the regular expression of the username
	 */
	public String getRegex() {
		return regex;
	}

	/**
	 * Set the regular expression of the username.
	 * 
	 * @param regex
	 *            the regular expression of the username
	 */
	public void setRegex(String regex) {
		this.regex = regex;
		this.pattern = regex != null ? Pattern.compile(regex) : null;
	}

	/**
	 * Get the regular expression of the reserved username.
	 * 
	 * @return the regular expression of the reserved username
	 */
	public String getReservedRegex() {
		return reservedRegex;
	}

	/**
	 * Set the regular expression of the reserved username.
	 * 
	 * @param reservedRegex
	 *            the regular expression of the reserved username
	 */
	public void setReservedRegex(String reservedRegex) {
		this.reservedRegex = reservedRegex;
		this.reservedPattern = reservedRegex != null ? Pattern.compile(
				reservedRegex, Pattern.CASE_INSENSITIVE) : null;
	}

	/**
	 * Get the regular expression of the unallowable username.
	 * 
	 * @return the regular expression of the unabllowable username
	 */
	public String getUnallowableRegex() {
		return unallowableRegex;
	}

	/**
	 * Set the regular expression of the unallowable username.
	 * 
	 * @param unallowableRegex
	 *            the regular expression of the unallowable username
	 */
	public void setUnallowableRegex(String unallowableRegex) {
		this.unallowableRegex = unallowableRegex;
		this.unallowablePattern = unallowableRegex != null ? Pattern.compile(
				unallowableRegex, Pattern.CASE_INSENSITIVE) : null;
	}

	/**
	 * Get the compiled pattern of the username.
	 * 
	 * @return the compiled pattern
	 */
	public Pattern getPattern() {
		return pattern;
	}

	/**
	 * Get the compiled pattern of the reserved username.
	 * 
	 * @return the compiled pattern of the reserved username
	 */
	public Pattern getReservedPattern() {
		return reservedPattern;
	}

	/**
	 * Get the compiled pattern of the unallowable username.
	 * 
	 * @return the compiled pattern of the unallowable username
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
