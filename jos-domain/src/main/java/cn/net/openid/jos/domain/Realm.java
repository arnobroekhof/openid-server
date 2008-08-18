/**
 * Created on 2008-05-18 09:49:13
 */
package cn.net.openid.jos.domain;


/**
 * @author Sutra Zhou
 * 
 */
public class Realm extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2093184402739527161L;

	private String url;

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
		result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
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
		if (!(obj instanceof Realm))
			return false;
		final Realm other = (Realm) obj;
		if (getUrl() == null) {
			if (other.getUrl() != null)
				return false;
		} else if (!getUrl().equals(other.getUrl()))
			return false;
		return true;
	}

}
