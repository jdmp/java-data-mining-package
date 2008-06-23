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

package org.jdmp.matrix.calculation.string;

import java.util.regex.Pattern;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.calculation.entrywise.replace.ReplaceRegex;
import org.jdmp.matrix.exceptions.MatrixException;

public class TestReplaceRegex extends TestCase {

	private static Matrix getTestMatrix() throws MatrixException {
		Matrix m = MatrixFactory.zeros(EntryType.STRING, 2, 2);
		m.setAsString("aabbcabd", 0, 0);
		m.setAsString(null, 0, 1);
		m.setAsString("ad", 1, 0);
		m.setAsString("aab", 1, 1);
		return m;
	}

	private static Matrix getResultMatrix() throws MatrixException {
		Matrix m = MatrixFactory.zeros(EntryType.STRING, 2, 2);
		m.setAsString("afgrbcfgrd", 0, 0);
		m.setAsString(null, 0, 1);
		m.setAsString("ad", 1, 0);
		m.setAsString("afgr", 1, 1);
		return m;
	}

	public void testConstructor1() throws MatrixException {
		Matrix matrix = getTestMatrix();
		ReplaceRegex ra = new ReplaceRegex(matrix, "ab", "fgr");
		Matrix resultMatrix = ra.calc(Ret.NEW);
		Assert.assertEquals(getResultMatrix(), resultMatrix);
	}

	public void testConstructor2() throws MatrixException {
		Matrix matrix = getTestMatrix();
		ReplaceRegex ra = new ReplaceRegex(matrix, Pattern.compile("ab"), "fgr");
		Matrix resultMatrix = ra.calc(Ret.NEW);
		Assert.assertEquals(getResultMatrix(), resultMatrix);
	}

	public void testCalc1() throws MatrixException {
		Matrix matrix = getTestMatrix();
		Matrix resultMatrix = ReplaceRegex.calc(matrix, "ab", "fgr");
		Assert.assertEquals(getResultMatrix(), resultMatrix);
	}

	public void testCalc2() throws MatrixException {
		Matrix matrix = getTestMatrix();
		Matrix resultMatrix = ReplaceRegex.calc(matrix, Pattern.compile("ab"), "fgr");
		Assert.assertEquals(getResultMatrix(), resultMatrix);
	}

	public static void main(String[] args) throws MatrixException {
		TestReplaceRegex test = new TestReplaceRegex();
		test.testConstructor1();
	}

}
