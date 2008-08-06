/**
 * Created on 2008-8-5 下午11:01:40
 */
package cn.net.openid.jos.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Virtual domain model.
 * <ol>
 * <li>type = 1: <code>http(s)://&lt;username&gt;.&lt;your-domain&gt;/</code>,
 * for example <code>http://username.openid.org.cn/</code>.</li>
 * <li>type = 2: <code>http(s)://&lt;your-domain&gt;/&lt;username&gt;</code>,
 * for example <code>http://www.openid.org.cn/username</code>.</li>
 * </ol>
 * 
 * @author Sutra Zhou
 * 
 */
public class Domain extends JosConfiguration implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7341781651134648946L;

	/**
	 * Username as a subdomain.
	 * <code>http(s)://&lt;username&gt;.&lt;your-domain&gt;/</code>
	 */
	public static final int TYPE_SUBDOMAIN = 1;
	/**
	 * Username as a subdirectory.
	 * <code>http(s)://&lt;your-domain&gt;/&lt;username&gt;</code>
	 */
	public static final int TYPE_SUBDIRECTORY = 2;

	private String id;
	/**
	 * e.g. openid.org.cn
	 */
	private String name;
	private int type;
	private String suffix;
	private boolean openRegistration;
	private Date creationDate = new Date();

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * @param suffix
	 *            the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * @return the openRegistration
	 */
	public boolean isOpenRegistration() {
		return openRegistration;
	}

	/**
	 * @param openRegistration
	 *            the openRegistration to set
	 */
	public void setOpenRegistration(boolean openRegistration) {
		this.openRegistration = openRegistration;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
				+ ((getName() == null) ? 0 : getName().hashCode());
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
		if (!(obj instanceof Domain))
			return false;
		Domain other = (Domain) obj;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}

}
