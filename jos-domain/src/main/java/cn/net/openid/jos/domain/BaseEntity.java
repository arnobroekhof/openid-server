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
 * Created on 2008-8-18 12:30:45
 */
package cn.net.openid.jos.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Base entity is an abstract class that contains a {@link #creationDate} field.
 * 
 * @author Sutra Zhou
 */
/* package */abstract class BaseEntity extends IdentifiableBase implements
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2802905382443023069L;

	/**
	 * The creation date of this entity.
	 */
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
	public void setCreationDate(final Date creationDate) {
		this.creationDate = cloneDate(creationDate);
	}

	/**
	 * Clone date null safely.
	 * 
	 * @param date
	 *            the date to clone
	 * @return null if the <code>date</code> is null, otherwise the cloned date.
	 */
	protected static final Date cloneDate(final Date date) {
		if (date == null) {
			return null;
		} else {
			return (Date) date.clone();
		}
	}
}
