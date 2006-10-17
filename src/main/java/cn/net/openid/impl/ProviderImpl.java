/**
 * Created on 2006-10-6 下午06:36:13
 */
package cn.net.openid.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.net.openid.Credential;
import cn.net.openid.Provider;
import cn.net.openid.User;
import cn.net.openid.dao.DaoFacade;
import cn.net.openid.utils.OpenIDNVFormat;
import cn.net.openid.utils.OpenIDUtils;
import cn.net.openid.web.LoginForm;

import com.redv.bloggerapi.client.Blog;
import com.redv.bloggerapi.client.Blogger;
import com.redv.bloggerapi.client.BloggerImpl;
import com.redv.bloggerapi.client.Fault;

/**
 * @author Shutra
 * 
 */
public class ProviderImpl implements Provider {
	private static final Log log = LogFactory.getLog(ProviderImpl.class);

	private DaoFacade daoFacade;

	/**
	 * @param daoFacade
	 *            the daoFacade to set
	 */
	public void setDaoFacade(DaoFacade daoFacade) {
		this.daoFacade = daoFacade;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.Provider#checkCredential(cn.net.openid.web.LoginForm)
	 */
	public boolean checkCredential(LoginForm lf) {
		String openid = clean(lf.getOpenidUrl());
		User user = this.daoFacade.getUserByOpenid(openid);
		if (user == null) {
			return false;
		}
		List<Credential> credentials = this.daoFacade.getCredentials(user
				.getId());
		if (credentials.size() == 0) {
			return false;
		}
		Credential c = (Credential) credentials.get(0);
		if (new String(c.getInfo()).equals(lf.getPassword())) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.Provider#checkIdSetup(java.util.Map,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void checkIdSetupResponse(String verifiedIdentifier,
			Map<String, String[]> parameterMap, HttpServletResponse resp)
			throws IOException {
		String mode = parameterMap.get("openid.mode")[0];
		// String assocHandle = req.getParameter("openid.assoc_handle");
		String returnTo = parameterMap.get("openid.return_to")[0];
		// String trustRoot = req.getParameter("openid.trust_root");

		HashMap<String, String> verificationStringValueMap = new LinkedHashMap<String, String>();
		verificationStringValueMap.put("mode", mode);
		verificationStringValueMap.put("identity", verifiedIdentifier);
		verificationStringValueMap.put("return_to", returnTo);
		byte[] key = this.generateMACKey();
		String sig = this.sign(verificationStringValueMap, key);

		String redirectUrl = new String(returnTo);

		HashMap<String, String> m = new HashMap<String, String>();
		m.put("mode", "id_res");
		m.put("identity", verifiedIdentifier);
		m.put("assoc_handle", new String(Base64.encodeBase64(key))); // TODO
		m.put("return_to", returnTo);
		m.put("signed", "mode,identity,return_to");
		m.put("sig", sig);
		// url.append("&openid.invalidate_handle=");
		redirectUrl = OpenIDUtils.appendValueMapToURL("openid", redirectUrl
				.toString(), m, "UTF-8");
		log.debug("redirectUrl: " + redirectUrl);
		resp.sendRedirect(redirectUrl.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.net.openid.Provider#checkPassword(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean checkPassword(LoginForm lf) {
		return false;
		/*
		Blogger blogger = null;
		try {
			blogger = new BloggerImpl("http://xpert.cn/xmlrpc");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		Blog[] blogs = null;
		try {
			blogs = blogger.getUsersBlogs("", lf.getUsername(), lf
					.getPassword());
		} catch (Fault e) {
			throw new RuntimeException(e);
		}
		String inputBlogId = lf.getOpenidUrl().substring(7,
				lf.getOpenidUrl().indexOf("."));
		log.debug("inputBlogId: " + inputBlogId);
		for (Blog blog : blogs) {
			if (inputBlogId.equalsIgnoreCase(blog.getBlogid())) {
				return true;
			}
		}
		return false;
		*/
	}

	public boolean checkSignature(
			Map<String, String> verificationStringValueMap, String signature,
			byte[] macKey) {
		String messageInput = OpenIDNVFormat
				.encodeToString(verificationStringValueMap);
		BigInteger n = generateMAC(messageInput, macKey);
		BigInteger input = new BigInteger(Base64.decodeBase64(signature
				.getBytes()));
		return n.equals(input);
	}

	private BigInteger generateMAC(String messageInput, byte[] macKey) {
		if (log.isDebugEnabled()) {
			log.debug("Generating MAC for '" + messageInput + "'");
		}
		try {
			Mac macAlg = Mac.getInstance("HmacSHA1");
			macAlg.init(new SecretKeySpec(macKey, "HmacSHA1"));
			byte[] digest = macAlg.doFinal(messageInput.getBytes());
			if (log.isDebugEnabled()) {
				log.debug("Generated MAC '"
						+ new String(Base64.encodeBase64(digest)) + "'");
			}
			return new BigInteger(digest);
		} catch (Exception e) {
			log.error("Exception generating signature", e);
			return null;
		}
	}

	public byte[] generateMACKey() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] newMACKey = new byte[20];
		secureRandom.nextBytes(newMACKey);
		return newMACKey;
	}

	public String sign(Map<String, String> verificationStringValueMap,
			byte[] macKey) {
		return this.sign(OpenIDNVFormat
				.encodeToString(verificationStringValueMap), macKey);
	}

	private String sign(String inputString, byte[] macKey) {
		return new String(Base64.encodeBase64(generateMAC(inputString, macKey)
				.toByteArray()));
	}

	private String clean(String url) {
		if (url.startsWith("http://")) {
			return url.substring("http://".length());
		} else if (url.startsWith("https://")) {
			return url.substring("https://".length());
		} else {
			return url;
		}
	}

}
