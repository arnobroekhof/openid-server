/**
 * Created on 2008-05-18 09:49:13
 */
package cn.net.openid.jos.domain;

import java.io.Serializable;

/**
 * @author Sutra Zhou
 * 
 */
public class Realm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2093184402739527161L;

	private String id;
	private String url;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

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
}
