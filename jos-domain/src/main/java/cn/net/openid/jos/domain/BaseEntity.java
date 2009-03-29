/**
 * Created on 2008-8-18 12:30:45
 */
package cn.net.openid.jos.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Sutra Zhou
 * 
 */
public abstract class BaseEntity extends IdentifiableBase implements
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2802905382443023069L;

	private Date creationDate = new Date();

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return cloneDate(creationDate);
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = cloneDate(creationDate);
	}

	/**
	 * Clone date null safely.
	 * 
	 * @param date
	 *            the date to clone
	 * @return null if the <code>date</code> is null, otherwise the cloned date.
	 */
	protected static final Date cloneDate(Date date) {
		if (date == null) {
			return null;
		} else {
			return (Date) date.clone();
		}
	}
}
