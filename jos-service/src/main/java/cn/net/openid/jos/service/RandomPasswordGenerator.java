/**
 * Created on 2008-9-4 03:09:02
 */
package cn.net.openid.jos.service;

import org.apache.commons.lang.math.RandomUtils;

/**
 * @author Sutra Zhou
 * 
 */
public class RandomPasswordGenerator implements PasswordGenerator {
	private static final char[] SOURCE = "`~!@#$%^&*()_+,./<>?;':\"[]\\{}|ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
			.toCharArray();
	private static final int SOURCE_COUNT = SOURCE.length;

	public char[] generate(int minimumLength, int maximumLength) {
		double d = RandomUtils.nextDouble();
		int len = minimumLength
				+ (int) (d * (double) (maximumLength - minimumLength));
		char[] ret = new char[len];
		for (int i = 0; i < len; i++) {
			ret[i] = SOURCE[RandomUtils.nextInt(SOURCE_COUNT)];
		}
		return ret;
	}
}
