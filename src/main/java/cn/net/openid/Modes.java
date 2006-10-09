/**
 * Created on 2006-10-5 下午10:23:43
 */
package cn.net.openid;

import java.util.Map;


/**
 * @author Shutra
 * @see <a
 *      href="http://openid.net/specs/openid-authentication-1_1.html#anchor12">Modes</a>
 */
public class Modes {
	/**
	 * Establish a shared secret between Consumer and Identity Provider.
	 */
	public static final String ASSOCIATE = "associate";

	/**
	 * Ask an Identity Provider if a End User owns the Claimed Identifier,
	 * getting back an immediate "yes" or "can't say" answer.
	 */
	public static final String CHECKID_IMMEDIATE = "checkid_immediate";

	/**
	 * Ask an Identity Provider if a End User owns the Claimed Identifier, but
	 * be willing to wait for the reply. The Consumer will pass the User-Agent
	 * to the Identity Provider for a short period of time which will return
	 * either a "yes" or "cancel" answer.
	 */
	public static final String CHECKID_SETUP = "checkid_setup";

	/**
	 * Ask an Identity Provider if a message is valid. For dumb, stateless
	 * Consumers or when verifying an invalidate_handle response.
	 */
	public static final String CHECK_AUTHENTICATION = "check_authentication";

	private Map<String, Service> getModes;

	private Map<String, Service> postModes;

	public void setGetModes(Map<String, Service> map) {
		this.getModes = map;
	}

	public Map<String, Service> getGetModes() {
		return this.getModes;
	}

	public void setPostModes(Map<String, Service> map) {
		this.postModes = map;
	}

	public Map<String, Service> getPostModes() {
		return this.postModes;
	}
}
