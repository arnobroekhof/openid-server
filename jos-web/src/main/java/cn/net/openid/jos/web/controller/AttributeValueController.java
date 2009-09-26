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
 * Created on 2007-3-26 23:18:11
 */
package cn.net.openid.jos.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import cn.net.openid.jos.domain.Attribute;
import cn.net.openid.jos.domain.AttributeValue;
import cn.net.openid.jos.web.AbstractJosSimpleFormController;
import cn.net.openid.jos.web.UserSession;

/**
 * @author Sutra Zhou
 * 
 */
public class AttributeValueController extends AbstractJosSimpleFormController {
	private Map<String, String> buildMap(
			Collection<AttributeValue> attributeValues) {
		Map<String, String> ret = new HashMap<String, String>();
		for (AttributeValue attributeValue : attributeValues) {
			ret.put(attributeValue.getAttribute().getId(), attributeValue
					.getValue());
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject
	 * (javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		UserSession userSession = getUserSession(request);
		List<AttributeValue> attributeValues = new ArrayList<AttributeValue>();
		Collection<Attribute> attributes = this.getJosService().getAttributes();
		Map<String, String> userAttributeValues = this.buildMap(this
				.getJosService().getUserAttributeValues(userSession.getUser()));
		for (Attribute attribute : attributes) {
			AttributeValue attributeValue = new AttributeValue();
			attributeValue.setAttribute(attribute);
			attributeValue.setValue(userAttributeValues.get(attributeValue
					.getAttribute().getId()));
			attributeValues.add(attributeValue);
		}
		return attributeValues;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		UserSession userSession = getUserSession(request);
		List<AttributeValue> attributeValues = (List<AttributeValue>) command;
		for (AttributeValue attributeValue : attributeValues) {
			attributeValue.setUser(this.getJosService().getUser(
					userSession.getUser().getId()));
			String value = request.getParameter(attributeValue.getAttribute()
					.getId());
			attributeValue.setValue(value);
		}
		this.getJosService().saveAttributeValues(userSession.getUser(),
				attributeValues);
		return super.onSubmit(request, response, command, errors);
	}

}
