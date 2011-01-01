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

package org.jdmp.complete;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.jdmp.core.plugin.JDMPPluginsMatrix;
import org.junit.Test;
import org.ujmp.core.Matrix;

public class TestPlugins {

	@Test
	public void testPlugins() {
		Matrix m = new JDMPPluginsMatrix();
		for (int r = 0; r < m.getRowCount(); r++) {
			String name = m.getAsString(r, 0);
			String status = m.getAsString(r, 4);

			// SST, JDBC, PDFBox, jexcelapi and Hadoop require Java 1.6, they
			// cannot be tested with 1.5
			if ("ujmp-sst".equals(name)
					&& "1.5".equals(System
							.getProperty("java.specification.version"))) {
				assertNotSame(name, "ok", status);
			} else if ("ujmp-hadoop".equals(name)
					&& "1.5".equals(System
							.getProperty("java.specification.version"))) {
				assertNotSame(name, "ok", status);
			} else if ("ujmp-jdbc".equals(name)
					&& "1.5".equals(System
							.getProperty("java.specification.version"))) {
				assertNotSame(name, "ok", status);
			} else if ("ujmp-jexcelapi".equals(name)
					&& "1.5".equals(System
							.getProperty("java.specification.version"))) {
				assertNotSame(name, "ok", status);
			} else if ("ujmp-pdfbox".equals(name)
					&& "1.5".equals(System
							.getProperty("java.specification.version"))) {
				assertNotSame(name, "ok", status);
			} else if ("ujmp-complete".equals(name)) {
				// not needed
			} else {
				assertEquals(name, "ok", status);
			}
		}
	}
}
