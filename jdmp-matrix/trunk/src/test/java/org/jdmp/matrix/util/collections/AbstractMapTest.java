/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.jdmp.matrix.util.collections;

import java.io.Serializable;
import java.util.Map;

import junit.framework.TestCase;

import org.jdmp.matrix.util.SerializationUtil;

public abstract class AbstractMapTest extends TestCase {

	public abstract Map createMap() throws Exception;

	public String getLabel() {
		return this.getClass().getSimpleName();
	}

	public void testPutAndGet() throws Exception {
		Map m = createMap();
		m.put("a", "test1");
		m.put("b", "test2");
		assertEquals(getLabel(), "test1", m.get("a"));
		assertEquals(getLabel(), "test2", m.get("b"));
	}

	public void testSerialize() throws Exception {
		Map m = createMap();
		m.put("a", "test1");
		m.put("b", "test2");
		if (m instanceof Serializable) {
			byte[] data = SerializationUtil.serialize((Serializable) m);
			Map m2 = (Map) SerializationUtil.deserialize(data);
			assertEquals(getLabel(), m, m2);
		}
	}

}
