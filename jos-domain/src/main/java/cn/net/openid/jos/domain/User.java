/**
 * Created on 2006-10-15 17:11:24
 */
package cn.net.openid.jos.domain;


/**
 * @author Sutra Zhou
 */
public class User extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6219139356897428716L;

	/**
	 * The prime value for hash code calculating.
	 */
	private static final int PRIME = 31;

	/**
	 * The username.
	 */
	private String username;

	/**
	 * The domain.
	 */
	private Domain domain = new Domain();

	/**
	 * Construct a default user.
	 */
	public User() {
	}

	/**
	 * Construct a user of the specified domain and usename.
	 * 
	 * @param domain
	 *            the domain
	 * @param username
	 *            the username
	 */
	public User(Domain domain, String username) {
		this.domain = domain;
		this.username = username;
	}

	/**
	 * Get the username.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the username.
	 * 
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * Get the identifier.
	 * 
	 * @return the identifier
	 */
	public String getIdentifier() {
		return String.format("%1$s%2$s%3$s", getDomain().getIdentifierPrefix(),
				getUsername(), getDomain().getIdentifierSuffix());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result = 1;
		result = PRIME * result
				+ ((getDomain() == null) ? 0 : getDomain().hashCode());
		result = PRIME * result
				+ ((getUsername() == null) ? 0 : getUsername().hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (getDomain() == null) {
			if (other.getDomain() != null) {
				return false;
			}
		} else if (!getDomain().equals(other.getDomain())) {
			return false;
		}
		if (getUsername() == null) {
			if (other.getUsername() != null) {
				return false;
			}
		} else if (!getUsername().equals(other.getUsername())) {
			return false;
		}
		return true;
	}

}
