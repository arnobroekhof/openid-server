/**
 * Created on 2008-3-5 22:12:26
 */
package cn.net.openid.jos.domain;

import java.util.Date;

/**
 * @author Sutra Zhou
 */
public class Site extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4706047050803190505L;

	/**
	 * The prime value for hash code calculating.
	 */
	private static final int PRIME = 31;

	/**
	 * The owner of the site.
	 */
	private User user = new User();

	/**
	 * The realm of the site.
	 */
	private Realm realm = new Realm();

	/**
	 * Indicate always approve the authentication requests from the site.
	 */
	private boolean alwaysApprove;

	/**
	 * The persona that send to the site.
	 */
	private Persona persona = new Persona();

	/**
	 * Last date that attempt to the site.
	 */
	private Date lastAttempt = new Date();

	/**
	 * Total approval count of the site.
	 */
	private int approvals;

	/**
	 * Get if always approve.
	 * 
	 * @return alwaysApprove true if always approve, false otherwise
	 */
	public boolean isAlwaysApprove() {
		return alwaysApprove;
	}

	/**
	 * Set if always approve.
	 * 
	 * @param alwaysApprove
	 *            the alwaysApprove to set
	 */
	public void setAlwaysApprove(boolean alwaysApprove) {
		this.alwaysApprove = alwaysApprove;
	}

	/**
	 * Get total approval count.
	 * 
	 * @return approvals the approval count
	 */
	public int getApprovals() {
		return approvals;
	}

	/**
	 * Set the approval count.
	 * 
	 * @param approvals
	 *            the approvals to set
	 */
	public void setApprovals(int approvals) {
		this.approvals = approvals;
	}

	/**
	 * Get the last attempt date.
	 * 
	 * @return lastAttempt the last attempt date
	 */
	public Date getLastAttempt() {
		return cloneDate(lastAttempt);
	}

	/**
	 * Set the last attempt date.
	 * 
	 * @param lastAttempt
	 *            the last attempt date to set
	 */
	public void setLastAttempt(Date lastAttempt) {
		this.lastAttempt = cloneDate(lastAttempt);
	}

	/**
	 * Get the persona.
	 * 
	 * @return persona the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * Set the persona.
	 * 
	 * @param persona
	 *            the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * Get the realm.
	 * 
	 * @return realm the realm
	 */
	public Realm getRealm() {
		return realm;
	}

	/**
	 * Set the realm
	 * 
	 * @param realm
	 *            the realm to set
	 */
	public void setRealm(Realm realm) {
		this.realm = realm;
	}

	/**
	 * Get the owner.
	 * 
	 * @return user the owner
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Set the owner.
	 * 
	 * @param user
	 *            the owner to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result = 1;
		result = PRIME * result + ((getRealm() == null) ? 0 : getRealm().hashCode());
		result = PRIME * result + ((getUser() == null) ? 0 : getUser().hashCode());
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
		if (!(obj instanceof Site)) {
			return false;
		}
		final Site other = (Site) obj;
		if (getRealm() == null) {
			if (other.getRealm() != null) {
				return false;
			}
		} else if (!getRealm().equals(other.getRealm())) {
			return false;
		}
		if (getUser() == null) {
			if (other.getUser() != null) {
				return false;
			}
		} else if (!getUser().equals(other.getUser())) {
			return false;
		}
		return true;
	}

}
