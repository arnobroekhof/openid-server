/**
 * Created on 2008-3-25 00:01:30
 */
package cn.net.openid.jos.domain;

import java.io.Serializable;

/**
 * @author Sutra Zhou
 * 
 */
public class AttributeValue implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1487911933605732662L;
	private User user;
	private Attribute attribute;
	private int index;
	private String value;

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the attribute
	 */
	public Attribute getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute
	 *            the attribute to set
	 */
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
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
				+ ((getAttribute() == null) ? 0 : getAttribute().hashCode());
		result = prime * result + getIndex();
		result = prime * result + ((getUser() == null) ? 0 : getUser().hashCode());
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
		if (!(obj instanceof AttributeValue))
			return false;
		final AttributeValue other = (AttributeValue) obj;
		if (getAttribute() == null) {
			if (other.getAttribute() != null)
				return false;
		} else if (!getAttribute().equals(other.getAttribute()))
			return false;
		if (getIndex() != other.getIndex())
			return false;
		if (getUser() == null) {
			if (other.getUser() != null)
				return false;
		} else if (!getUser().equals(other.getUser()))
			return false;
		return true;
	}

}
