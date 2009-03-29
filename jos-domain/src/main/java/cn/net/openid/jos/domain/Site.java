/**
 * Created on 2008-3-5 22:12:26
 */
package cn.net.openid.jos.domain;

import java.util.Date;

/**
 * @author Sutra Zhou
 * 
 */
public class Site extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4706047050803190505L;

	private static final int PRIME = 31;

	private User user = new User();
	private Realm realm = new Realm();
	private boolean alwaysApprove;
	private Persona persona = new Persona();
	private Date lastAttempt = new Date();
	private int approvals;

	/**
	 * @return alwaysApprove
	 */
	public boolean isAlwaysApprove() {
		return alwaysApprove;
	}

	/**
	 * @param alwaysApprove
	 *            要设置的 alwaysApprove
	 */
	public void setAlwaysApprove(boolean alwaysApprove) {
		this.alwaysApprove = alwaysApprove;
	}

	/**
	 * @return approvals
	 */
	public int getApprovals() {
		return approvals;
	}

	/**
	 * @param approvals
	 *            要设置的 approvals
	 */
	public void setApprovals(int approvals) {
		this.approvals = approvals;
	}

	/**
	 * @return lastAttempt
	 */
	public Date getLastAttempt() {
		return cloneDate(lastAttempt);
	}

	/**
	 * @param lastAttempt
	 *            要设置的 lastAttempt
	 */
	public void setLastAttempt(Date lastAttempt) {
		this.lastAttempt = cloneDate(lastAttempt);
	}

	/**
	 * @return persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * @param persona
	 *            要设置的 persona
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * @return realm
	 */
	public Realm getRealm() {
		return realm;
	}

	/**
	 * @param realm
	 *            要设置的 realm
	 */
	public void setRealm(Realm realm) {
		this.realm = realm;
	}

	/**
	 * @return user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            要设置的 user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 1;
		result = PRIME * result + ((getRealm() == null) ? 0 : getRealm().hashCode());
		result = PRIME * result + ((getUser() == null) ? 0 : getUser().hashCode());
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
		if (!(obj instanceof Site))
			return false;
		final Site other = (Site) obj;
		if (getRealm() == null) {
			if (other.getRealm() != null)
				return false;
		} else if (!getRealm().equals(other.getRealm()))
			return false;
		if (getUser() == null) {
			if (other.getUser() != null)
				return false;
		} else if (!getUser().equals(other.getUser()))
			return false;
		return true;
	}

}
