/**
 * Created on 2008-05-18 09:49:13
 */
package cn.net.openid.jos.domain;


/**
 * @author Sutra Zhou
 */
public class Realm extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2093184402739527161L;

	/**
	 * The prime value for hash code calculating.
	 */
	private static final int PRIME = 31;

	/**
	 * The url of the realm.
	 */
	private String url;

	/**
	 * Get the url.
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set the url.
	 * 
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result = 1;
		result = PRIME * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
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
		if (!(obj instanceof Realm)) {
			return false;
		}
		final Realm other = (Realm) obj;
		if (getUrl() == null) {
			if (other.getUrl() != null) {
				return false;
			}
		} else if (!getUrl().equals(other.getUrl())) {
			return false;
		}
		return true;
	}

}
