/**
 * Created on 2008-8-17 17:35:09
 */
package cn.net.openid.jos.service;

import java.text.DecimalFormat;

/**
 * @author Sutra Zhou
 * 
 */
public class TimeZoneOffsetFormat {
	private static final DecimalFormat hoursNumberFormat = new DecimalFormat(
			"00");
	private static final DecimalFormat minutesNumberFormat = new DecimalFormat(
			"00");
	static {
		hoursNumberFormat.setPositivePrefix("+");
		minutesNumberFormat.setNegativePrefix("");
	}

	public String format(int offset) {
		if (offset == 0) {
			return "";
		}

		int minutes = offset / (1000 * 60);
		int hours = minutes / 60;

		StringBuilder sb = new StringBuilder();

		sb.append(hoursNumberFormat.format(hours));

		sb.append(":");

		minutes = minutes - hours * 60;
		sb.append(minutesNumberFormat.format(minutes));

		return sb.toString();
	}

}
