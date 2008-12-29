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

import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.module.Module;
import org.jdmp.core.module.ModuleFactory;
import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.script.Result;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;

public class TestScript extends TestCase {

	public void testPlus() throws Exception {
		Module m = ModuleFactory.emptyModule();
		m.execute("a=[1,2,3;4,5,6]");
		m.execute("b=[4,5,6;1,2,3]");
		Result r = m.execute("a+b");
		m.execute("c=a+b");

		Matrix rm = (Matrix) r.getObject();
		Matrix vm = m.getVariables().get("c").getMatrix();
		Matrix expected = MatrixFactory.linkToArray(new double[][] { { 5, 7, 9 }, { 5, 7, 9 } });

		assertEquals(expected, rm);
		assertEquals(expected, vm);
	}

	public void testIris() throws Exception {
		Result r = execute("iris");
		Object o = r.getObject();
		assertEquals(ClassificationDataSet.class, o.getClass());
		ClassificationDataSet ds = (ClassificationDataSet) o;
		assertEquals(150, ds.getSamples().getSize());
	}

	public void testHenon() throws Exception {
		Result r = execute("henon(100,10,5)");
		Object o = r.getObject();
		assertEquals(RegressionDataSet.class, o.getClass());
		RegressionDataSet ds = (RegressionDataSet) o;
		assertEquals(100, ds.getSamples().getSize());
		Sample s = ds.getSamples().getElementAt(0);
		long input = s.getVariables().get(ClassificationSample.INPUT).getColumnCount();
		long target = s.getVariables().get(ClassificationSample.TARGET).getColumnCount();
		assertEquals(10, input);
		assertEquals(5, target);
	}

	private Result execute(String s) throws Exception {
		Module m = ModuleFactory.emptyModule();
		return m.execute(s);
	}
}
