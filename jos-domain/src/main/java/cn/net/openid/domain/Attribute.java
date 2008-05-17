/**
 * Created on 2008-3-24 23:13:38
 */
package cn.net.openid.domain;

import java.io.Serializable;

/**
 * @author Sutra Zhou
 * 
 */
public class Attribute implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2109982803475487846L;
	private String id;
	private String alias;

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
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 *            the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

}
