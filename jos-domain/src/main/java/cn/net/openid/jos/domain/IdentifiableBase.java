/**
 * Created on 2008-8-18 12:16:49
 */
package cn.net.openid.jos.domain;

/**
 * @author Sutra Zhou
 * 
 */
public abstract class IdentifiableBase implements Identifiable {
	private String id;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.Identifiable#getId()
	 */
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.jos.Identifiable#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}

}
