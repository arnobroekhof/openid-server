/**
 * Created on 2008-3-5 下午10:12:26
 */
package cn.net.openid.jos.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sutra Zhou
 * 
 */
public class Site implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4706047050803190505L;

	private User user;
	private Realm realm;
	private boolean alwaysApprove;
	private Persona persona;
	private Date lastAttempt;
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
		return lastAttempt;
	}

	/**
	 * @param lastAttempt
	 *            要设置的 lastAttempt
	 */
	public void setLastAttempt(Date lastAttempt) {
		this.lastAttempt = lastAttempt;
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

}
