/**
 * Created on 2008-8-17 23:58:43
 */
package cn.net.openid.jos.domain;

/**
 * This interface represents an entity that has an identity that is represented
 * by a string.
 * 
 * @author Sutra Zhou
 * 
 */
public interface Identifiable {
	/**
	 * Set the identity of this entity.
	 * 
	 * @param id
	 */
	void setId(String id);

	/**
	 * Return the identity of this entity.
	 * 
	 * @return
	 */
	String getId();
}
