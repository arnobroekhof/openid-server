/**
 * Created on 2008-8-18 12:16:49
 */
package cn.net.openid.jos.domain;

/**
 * @author Sutra Zhou
 * 
 */
public abstract class IdentifiableBase implements Identifiable {
	/**
	 * The ID.
	 */
	private String id;

	/**
	 * {@inheritDoc}
	 */
	public String getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setId(final String id) {
		this.id = id;
	}

}
