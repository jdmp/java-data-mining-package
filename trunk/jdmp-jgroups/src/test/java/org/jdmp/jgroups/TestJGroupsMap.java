/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.jdmp.jgroups;

import java.util.Map;

import org.ujmp.core.collections.AbstractStringMapTest;

public class TestJGroupsMap extends AbstractStringMapTest {

	public Map<String, String> createMap() throws Exception {
		return new JGroupsMap<String, String>();
	}

	public void testTwoMaps() throws Exception {
		JGroupsMap<String, String> map1 = new JGroupsMap<String, String>("map");
		JGroupsMap<String, String> map2 = new JGroupsMap<String, String>("map");

		map1.put("test1", "test1");

		for (int i = 0; i < 10000; i++) {
			System.out.println((i * 100) + ": " + map2.get("test1"));
			Thread.sleep(1000);
		}

	}

	public static void main(String[] args) throws Exception {
		new TestJGroupsMap().testTwoMaps();
	}

}
