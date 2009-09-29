/**
 * Copyright (c) 2006-2009, Redv.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Redv.com nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * Created on 2008-8-17 17:35:09
 */
package cn.net.openid.jos.service;

import java.text.DecimalFormat;

/**
 * Time zone offset formatter.
 * 
 * @author Sutra Zhou
 */
public class TimeZoneOffsetFormat {
	/**
	 * The minutes of one hour.
	 */
	private static final int MINUTES_OF_ONE_HOUR = 60;

	/**
	 * The seconds of one minute.
	 */
	private static final int SECONDS_OF_ONE_MINUTE = 60;

	/**
	 * The milliseconds of one second.
	 */
	private static final int MILLISECONDS_OF_ONE_SECOND = 1000;

	/**
	 * The milliseconds of one minute.
	 */
	private static final int MILLISECONDS_OF_ONE_MINUTE =
		MILLISECONDS_OF_ONE_SECOND * SECONDS_OF_ONE_MINUTE;

	/**
	 * The formatter to format hours.
	 */
	private static final DecimalFormat HOURS_NUMBER_FORMAT =
		new DecimalFormat("00");

	/**
	 * The formatter to format minutes.
	 */
	private static final DecimalFormat MINUTES_NUMBER_FORMAT =
		new DecimalFormat("00");

	static {
		HOURS_NUMBER_FORMAT.setPositivePrefix("+");
		MINUTES_NUMBER_FORMAT.setNegativePrefix("");
	}

	/**
	 * Format the offset.
	 * 
	 * @param offset
	 *            the time zone offset in milliseconds
	 * @return the formatted string
	 */
	public String format(final int offset) {
		if (offset == 0) {
			return "";
		}

		int minutes = offset / MILLISECONDS_OF_ONE_MINUTE;
		int hours = minutes / MINUTES_OF_ONE_HOUR;

		StringBuilder sb = new StringBuilder();

		sb.append(HOURS_NUMBER_FORMAT.format(hours));

		sb.append(":");

		minutes = minutes - hours * MINUTES_OF_ONE_HOUR;
		sb.append(MINUTES_NUMBER_FORMAT.format(minutes));

		return sb.toString();
	}

}
