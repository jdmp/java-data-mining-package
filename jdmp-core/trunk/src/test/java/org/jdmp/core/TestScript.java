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

package org.jdmp.core;

import junit.framework.TestCase;

import org.jdmp.core.module.Module;
import org.jdmp.core.module.ModuleFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;

public class TestScript extends TestCase {

	public void testScript() throws Exception {
		Module m = ModuleFactory.emptyModule();
		m.execute("a=[1,2,3;4,5,6];");
		m.execute("b=[4,5,6;1,2,3];");
		m.execute("c=a+b;");

		Matrix actual = m.getVariables().get("c").getMatrix();
		Matrix expected = MatrixFactory.linkToArray(new double[][] { { 5, 7, 9 }, { 5, 7, 9 } });

		assertEquals(expected, actual);
	}
}
