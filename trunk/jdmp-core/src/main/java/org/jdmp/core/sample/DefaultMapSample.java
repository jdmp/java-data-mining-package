/*
 * Copyright (C) 2008-2011 by Holger Arndt
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.core.sample;

import java.util.HashMap;
import java.util.Map;

public class DefaultMapSample extends AbstractMapSample {
	private static final long serialVersionUID = -7381008972833175285L;

	private Map<String, Object> map = null;

	public DefaultMapSample() {
		map = new HashMap<String, Object>(2);
	}

	@SuppressWarnings("unchecked")
	public DefaultMapSample(Map<? extends String, ? extends Object> map) {
		this.map = (Map<String, Object>) map;
	}

	public Map<String, Object> getWrappedObject() {
		return map;
	}

	public void setWrappedObject(Map<String, Object> object) {
		this.map = object;
	}
}
