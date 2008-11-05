/**
 * Created on 2006-10-15 17:11:24
 */
package cn.net.openid.jos.domain;


/**
 * @author Sutra Zhou
 * 
 */
public class User extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6219139356897428716L;

	private String username;
	private Domain domain = new Domain();

	public User() {
	}

	public User(Domain domain, String username) {
		this.domain = domain;
		this.username = username;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * Get the identifier.
	 * 
	 * @return the identifier
	 */
	public String getIdentifier() {
		return String.format("%1$s%2$s%3$s", getDomain().getIdentifierPrefix(),
				getUsername(), getDomain().getIdentifierSuffix());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getDomain() == null) ? 0 : getDomain().hashCode());
		result = prime * result
				+ ((getUsername() == null) ? 0 : getUsername().hashCode());
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
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (getDomain() == null) {
			if (other.getDomain() != null)
				return false;
		} else if (!getDomain().equals(other.getDomain()))
			return false;
		if (getUsername() == null) {
			if (other.getUsername() != null)
				return false;
		} else if (!getUsername().equals(other.getUsername()))
			return false;
		return true;
	}

}
