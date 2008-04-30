/* ----------------------------------------------------------------------------
 * File:    CalculationStringTest.java
 * Project: jdmp-matrix
 * Package: org.jdmp.test.matrix.calculation.string
 * ID:      $Id$
 *
 * ----------------------------------------------------------------------------
 * 
 * << short description of class >>
 *
 * ----------------------------------------------------------------------------
 *
 * Author: Andreas Naegele
 * Date:   26.02.2008
 * --------------------------------------------------------------------------*/

package org.jdmp.test.matrix.calculation.string;

import java.util.regex.Pattern;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.calculation.entrywise.replace.ReplaceRegex;

public class TestReplaceRegex extends TestCase {

	private static Matrix getTestMatrix() throws MatrixException {
		Matrix m = Matrix.zeros(EntryType.STRING, 2, 2);
		m.setString("aabbcabd", 0, 0);
		m.setString(null, 0, 1);
		m.setString("ad", 1, 0);
		m.setString("aab", 1, 1);
		return m;
	}

	private static Matrix getResultMatrix() throws MatrixException {
		Matrix m = Matrix.zeros(EntryType.STRING, 2, 2);
		m.setString("afgrbcfgrd", 0, 0);
		m.setString(null, 0, 1);
		m.setString("ad", 1, 0);
		m.setString("afgr", 1, 1);
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
