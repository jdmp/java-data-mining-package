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

package org.jdmp.matrix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.coordinates.Coordinates;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.stubs.AbstractDoubleMatrix;
import org.jdmp.matrix.util.SerializationUtil;

public abstract class AbstractMatrixTest extends TestCase {

	public abstract Matrix createMatrix(long... size) throws Exception;

	public abstract Matrix createMatrix(Matrix source) throws Exception;

	public String getLabel() {
		return this.getClass().getSimpleName();
	}

	public Matrix getTestMatrix() throws Exception {
		Matrix m = createMatrix(3, 3);
		m.setDouble(1.0, 0, 0);
		m.setDouble(3.0, 1, 0);
		m.setDouble(4.0, 1, 1);
		m.setDouble(2.0, 2, 1);
		m.setDouble(-2.0, 1, 2);
		return m;
	}

	// Test interface CoordinateFunctions

	public void testCoordinateIterator2D() throws Exception {
		Matrix m = createMatrix(3, 3);
		Iterator<long[]> ci = m.allCoordinates().iterator();
		long[] c1 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c1, new long[] { 0, 0 }));
		long[] c2 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c2, new long[] { 0, 1 }));
		long[] c3 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c3, new long[] { 0, 2 }));
		long[] c4 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c4, new long[] { 1, 0 }));
		long[] c5 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c5, new long[] { 1, 1 }));
		long[] c6 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c6, new long[] { 1, 2 }));
		long[] c7 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c7, new long[] { 2, 0 }));
		long[] c8 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c8, new long[] { 2, 1 }));
		long[] c9 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c9, new long[] { 2, 2 }));
		assertFalse(getLabel(), ci.hasNext());
	}

	public void testAvailableCoordinateIterator2D() throws Exception {
		Matrix m = getTestMatrix();

		List<Coordinates> clist = new ArrayList<Coordinates>();

		for (long[] c : m.availableCoordinates()) {
			clist.add(new Coordinates(c));
		}

		if (m.isSparse()) {
			assertEquals(getLabel(), 5, clist.size());
			assertTrue(getLabel(), clist.contains(new Coordinates(0, 0)));
			assertTrue(getLabel(), clist.contains(new Coordinates(1, 0)));
			assertTrue(getLabel(), clist.contains(new Coordinates(1, 1)));
			assertTrue(getLabel(), clist.contains(new Coordinates(2, 1)));
			assertTrue(getLabel(), clist.contains(new Coordinates(1, 2)));
		} else {
			assertEquals(getLabel(), 9, clist.size());
			assertTrue(getLabel(), clist.contains(new Coordinates(0, 0)));
			assertTrue(getLabel(), clist.contains(new Coordinates(0, 1)));
			assertTrue(getLabel(), clist.contains(new Coordinates(0, 2)));
			assertTrue(getLabel(), clist.contains(new Coordinates(1, 0)));
			assertTrue(getLabel(), clist.contains(new Coordinates(1, 1)));
			assertTrue(getLabel(), clist.contains(new Coordinates(1, 2)));
			assertTrue(getLabel(), clist.contains(new Coordinates(2, 0)));
			assertTrue(getLabel(), clist.contains(new Coordinates(2, 1)));
			assertTrue(getLabel(), clist.contains(new Coordinates(2, 2)));
		}

	}

	public void testSelectedCoordinatesString() throws Exception {
		Matrix m = getTestMatrix();

		Matrix mTest = createMatrix(2, 3);
		mTest.setObject(mTest.getObject(0, 0), 0, 0);
		mTest.setObject(mTest.getObject(0, 1), 0, 1);
		mTest.setObject(mTest.getObject(0, 2), 0, 2);
		mTest.setObject(mTest.getObject(1, 0), 1, 0);
		mTest.setObject(mTest.getObject(1, 1), 1, 1);
		mTest.setObject(mTest.getObject(1, 2), 1, 2);

		List<Coordinates> clist = new ArrayList<Coordinates>();

		for (long[] c : m.selectedCoordinates("1:2;:")) {
			clist.add(new Coordinates(c));
		}

		assertEquals(getLabel(), 6, clist.size());
		assertTrue(getLabel(), clist.contains(new Coordinates(0, 0)));
		assertTrue(getLabel(), clist.contains(new Coordinates(0, 1)));
		assertTrue(getLabel(), clist.contains(new Coordinates(0, 2)));
		assertTrue(getLabel(), clist.contains(new Coordinates(1, 0)));
		assertTrue(getLabel(), clist.contains(new Coordinates(1, 1)));
		assertTrue(getLabel(), clist.contains(new Coordinates(1, 2)));

	}

	public void testSelectedCoordinates() throws Exception {
		Matrix m = getTestMatrix();

		Matrix mTest = createMatrix(2, 3);
		mTest.setObject(mTest.getObject(0, 0), 0, 0);
		mTest.setObject(mTest.getObject(0, 1), 0, 1);
		mTest.setObject(mTest.getObject(0, 2), 0, 2);
		mTest.setObject(mTest.getObject(1, 0), 1, 0);
		mTest.setObject(mTest.getObject(1, 1), 1, 1);
		mTest.setObject(mTest.getObject(1, 2), 1, 2);

		Iterator<long[]> ci = m.selectedCoordinates(new long[] { 0, 1 }, new long[] { 0, 1, 2 }).iterator();
		long[] c = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { 0, 0 }));
		c = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { 0, 1 }));
		c = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { 0, 2 }));
		c = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { 1, 0 }));
		c = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { 1, 1 }));
		c = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { 1, 2 }));
		assertFalse(getLabel(), ci.hasNext());
	}

	public void testGetCoordinatesOfMaximum() throws Exception {
		Matrix m = getTestMatrix();
		long[] c = m.getCoordinatesOfMaximum();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { 1, 1 }));

		m = createMatrix(2, 2);
		m.setDouble(Double.NaN, 0, 0);
		m.setDouble(Double.NaN, 0, 1);
		m.setDouble(Double.NaN, 1, 0);
		m.setDouble(Double.NaN, 1, 1);
		c = m.getCoordinatesOfMaximum();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { -1, -1 }));
	}

	public void testGetCoordinatesOfMininim() throws Exception {
		Matrix m = getTestMatrix();
		long[] c = m.getCoordinatesOfMinimum();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { 1, 2 }));

		m = createMatrix(2, 2);
		m.setDouble(Double.NaN, 0, 0);
		m.setDouble(Double.NaN, 0, 1);
		m.setDouble(Double.NaN, 1, 0);
		m.setDouble(Double.NaN, 1, 1);
		c = m.getCoordinatesOfMaximum();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { -1, -1 }));
	}

	public void testContains() throws Exception {
		Matrix m = getTestMatrix();
		assertTrue(m.contains(0, 0));
		if (m.isSparse())
			assertFalse(m.contains(0, 1));
		else
			assertTrue(m.contains(0, 1));
		if (m.isSparse())
			assertFalse(m.contains(0, 2));
		else
			assertTrue(m.contains(0, 2));
		assertTrue(m.contains(1, 0));
		assertTrue(m.contains(1, 1));
		if (m.isSparse())
			assertFalse(m.contains(0, 1));
		else
			assertTrue(m.contains(1, 2));
		if (m.isSparse())
			assertFalse(m.contains(0, 1));
		else
			assertTrue(m.contains(2, 0));
		assertTrue(m.contains(2, 1));
		if (m.isSparse())
			assertFalse(m.contains(0, 1));
		else
			assertTrue(m.contains(2, 2));
		assertFalse(m.contains(7, 7));
	}

	public void testSize() throws Exception {
		Matrix m = createMatrix(20, 10);
		assertEquals(getLabel(), 20, m.getRowCount());
		assertEquals(getLabel(), 10, m.getColumnCount());
	}

	public void testClone() throws Exception {
		Matrix m = createMatrix(2, 2);
		m.setDouble(1.0, 0, 0);
		m.setDouble(2.0, 0, 1);
		m.setDouble(3.0, 1, 0);
		m.setDouble(4.0, 1, 1);
		m.setMatrixAnnotation("annotation");
		m.setAxisAnnotation(Matrix.ROW, "row");
		m.setAxisAnnotation(Matrix.COLUMN, "column");
		m.setAxisAnnotation(Matrix.ROW, 0, "row0");
		m.setAxisAnnotation(Matrix.ROW, 1, "row1");
		m.setAxisAnnotation(Matrix.COLUMN, 0, "column0");
		m.setAxisAnnotation(Matrix.COLUMN, 1, "column1");
		Matrix m2 = m.clone();
		assertTrue(getLabel(), m.equalsContent(m2));
		assertTrue(getLabel(), m.equalsAnnotation(m2));
	}

	public void testAnnotation() throws Exception {
		Matrix m = createMatrix(2, 2);
		m.setDouble(1.0, 0, 0);
		m.setDouble(2.0, 0, 1);
		m.setDouble(3.0, 1, 0);
		m.setDouble(4.0, 1, 1);
		m.setMatrixAnnotation("annotation");
		m.setAxisAnnotation(Matrix.ROW, "row");
		m.setAxisAnnotation(Matrix.COLUMN, "column");
		m.setAxisAnnotation(Matrix.ROW, 0, "row0");
		m.setAxisAnnotation(Matrix.ROW, 1, "row1");
		m.setAxisAnnotation(Matrix.COLUMN, 0, "column0");
		m.setAxisAnnotation(Matrix.COLUMN, 1, "column1");
		assertEquals(getLabel(), "annotation", m.getMatrixAnnotation());
		assertEquals(getLabel(), "row", m.getAxisAnnotation(Matrix.ROW));
		assertEquals(getLabel(), "column", m.getAxisAnnotation(Matrix.COLUMN));
		assertEquals(getLabel(), "row0", m.getAxisAnnotation(Matrix.ROW, 0));
		assertEquals(getLabel(), "row1", m.getAxisAnnotation(Matrix.ROW, 1));
		assertEquals(getLabel(), "column0", m.getAxisAnnotation(Matrix.COLUMN, 0));
		assertEquals(getLabel(), "column1", m.getAxisAnnotation(Matrix.COLUMN, 1));
	}

	public void testCountMissingValues() throws Exception {
		Matrix m = createMatrix(4, 4);
		m = m.zeros();
		m.setDouble(Double.NaN, 1, 0);
		m.setDouble(Double.NaN, 3, 0);
		m.setDouble(Double.NaN, 2, 1);
		m.setDouble(Double.NaN, 1, 1);
		m.setDouble(Double.NaN, 3, 1);
		m.setDouble(Double.NaN, 1, 2);

		Matrix m1 = m.countMissing(Ret.NEW, Matrix.ROW);
		Matrix m2 = m.countMissing(Ret.NEW, Matrix.COLUMN);
		Matrix m3 = m.countMissing(Ret.NEW, Matrix.ALL);

		assertEquals(getLabel(), 2.0, m1.getDouble(0, 0));
		assertEquals(getLabel(), 3.0, m1.getDouble(0, 1));
		assertEquals(getLabel(), 1.0, m1.getDouble(0, 2));
		assertEquals(getLabel(), 0.0, m1.getDouble(0, 3));

		assertEquals(getLabel(), 0.0, m2.getDouble(0, 0));
		assertEquals(getLabel(), 3.0, m2.getDouble(1, 0));
		assertEquals(getLabel(), 1.0, m2.getDouble(2, 0));
		assertEquals(getLabel(), 2.0, m2.getDouble(3, 0));

		assertEquals(getLabel(), 6.0, m3.getDouble(0, 0));
	}

	public void testLink() throws Exception {
		Matrix m = createMatrix(2, 2);
		m.setDouble(1.0, 0, 0);
		m.setDouble(2.0, 0, 1);
		m.setDouble(3.0, 1, 0);
		m.setDouble(4.0, 1, 1);
		Matrix m2 = m.link();
		assertEquals(getLabel(), m, m2);

		m.setDouble(5.0, 0, 0);
		m.setDouble(6.0, 0, 1);
		m.setDouble(3.0, 1, 0);
		m.setDouble(2.0, 1, 1);

		assertEquals(getLabel(), m, m2);

		m2.setDouble(3.0, 0, 0);
		m2.setDouble(2.0, 0, 1);
		m2.setDouble(1.0, 1, 0);
		m2.setDouble(-9.0, 1, 1);

		assertEquals(getLabel(), m, m2);
	}

	public void testSerialize() throws Exception {
		Matrix m = createMatrix(2, 2);
		m.setDouble(1.0, 0, 0);
		m.setDouble(2.0, 0, 1);
		m.setDouble(3.0, 1, 0);
		m.setDouble(4.0, 1, 1);
		byte[] data = SerializationUtil.serialize(m);
		Matrix m2 = (Matrix) SerializationUtil.deserialize(data);
		if (m2.isTransient()) {
			Matrix m0 = MatrixFactory.zeros(2, 2);
			assertEquals(getLabel(), m0, m2);
		} else {
			assertEquals(getLabel(), m, m2);
		}
	}

	public void testToDoubleArray() throws Exception {
		Matrix m = createMatrix(2, 2);
		m.setDouble(1.0, 0, 0);
		m.setDouble(2.0, 0, 1);
		m.setDouble(3.0, 1, 0);
		m.setDouble(4.0, 1, 1);
		double[][] values = m.toDoubleArray();
		assertEquals(getLabel(), 1.0, values[0][0]);
		assertEquals(getLabel(), 2.0, values[0][1]);
		assertEquals(getLabel(), 3.0, values[1][0]);
		assertEquals(getLabel(), 4.0, values[1][1]);
	}

	public void testSetAndGet() throws Exception {
		Matrix m = createMatrix(2, 2);
		m.setDouble(1.0, 0, 0);
		m.setDouble(2.0, 0, 1);
		m.setDouble(3.0, 1, 0);
		m.setDouble(4.0, 1, 1);
		assertEquals(getLabel(), 1.0, m.getDouble(0, 0));
		assertEquals(getLabel(), 2.0, m.getDouble(0, 1));
		assertEquals(getLabel(), 3.0, m.getDouble(1, 0));
		assertEquals(getLabel(), 4.0, m.getDouble(1, 1));
	}

	public void testPlus() throws Exception {
		Matrix m = createMatrix(2, 2);
		m.setDouble(1.0, 0, 0);
		m.setDouble(2.0, 0, 1);
		m.setDouble(3.0, 1, 0);
		m.setDouble(4.0, 1, 1);
		m = m.plus(1.0);
		assertEquals(getLabel(), 2.0, m.getDouble(0, 0));
		assertEquals(getLabel(), 3.0, m.getDouble(0, 1));
		assertEquals(getLabel(), 4.0, m.getDouble(1, 0));
		assertEquals(getLabel(), 5.0, m.getDouble(1, 1));
	}

	public void testTranspose() throws Exception {
		Matrix m = createMatrix(2, 2);
		m.setDouble(1.0, 0, 0);
		m.setDouble(2.0, 0, 1);
		m.setDouble(3.0, 1, 0);
		m.setDouble(4.0, 1, 1);
		m = m.transpose();
		assertEquals(getLabel(), 1.0, m.getDouble(0, 0));
		assertEquals(getLabel(), 3.0, m.getDouble(0, 1));
		assertEquals(getLabel(), 2.0, m.getDouble(1, 0));
		assertEquals(getLabel(), 4.0, m.getDouble(1, 1));
	}

	public void testEmpty() throws Exception {
		Matrix m = createMatrix(2, 2);
		if (m instanceof AbstractDoubleMatrix) {
			assertEquals(getLabel(), 0.0, m.getDouble(0, 0));
			assertEquals(getLabel(), 0.0, m.getDouble(0, 1));
			assertEquals(getLabel(), 0.0, m.getDouble(1, 0));
			assertEquals(getLabel(), 0.0, m.getDouble(1, 1));
		} else {
			assertEquals(getLabel(), null, m.getObject(0, 0));
			assertEquals(getLabel(), null, m.getObject(0, 1));
			assertEquals(getLabel(), null, m.getObject(1, 0));
			assertEquals(getLabel(), null, m.getObject(1, 1));
		}
	}

	public void testMinus() throws Exception {
		Matrix m = createMatrix(2, 2);
		m.setDouble(1.0, 0, 0);
		m.setDouble(2.0, 0, 1);
		m.setDouble(3.0, 1, 0);
		m.setDouble(4.0, 1, 1);
		m = m.minus(1.0);
		assertEquals(getLabel(), 0.0, m.getDouble(0, 0));
		assertEquals(getLabel(), 1.0, m.getDouble(0, 1));
		assertEquals(getLabel(), 2.0, m.getDouble(1, 0));
		assertEquals(getLabel(), 3.0, m.getDouble(1, 1));
	}

	public void testMultiply() throws Exception {
		Matrix m1 = createMatrix(2, 2);
		m1.setDouble(1.0, 0, 0);
		m1.setDouble(2.0, 0, 1);
		m1.setDouble(3.0, 1, 0);
		m1.setDouble(4.0, 1, 1);
		Matrix m2 = createMatrix(2, 2);
		m2.setDouble(1.0, 0, 0);
		m2.setDouble(2.0, 0, 1);
		m2.setDouble(3.0, 1, 0);
		m2.setDouble(4.0, 1, 1);
		Matrix m3 = m1.mtimes(m2);
		assertEquals(getLabel(), 7.0, m3.getDouble(0, 0));
		assertEquals(getLabel(), 10.0, m3.getDouble(0, 1));
		assertEquals(getLabel(), 15.0, m3.getDouble(1, 0));
		assertEquals(getLabel(), 22.0, m3.getDouble(1, 1));
	}

	public void testInverse() throws Exception {
		Matrix m1 = createMatrix(3, 3);
		m1.setDouble(1.0, 0, 0);
		m1.setDouble(2.0, 1, 0);
		m1.setDouble(3.0, 2, 0);
		m1.setDouble(4.0, 0, 1);
		m1.setDouble(1.0, 1, 1);
		m1.setDouble(2.0, 2, 1);
		m1.setDouble(3.0, 0, 2);
		m1.setDouble(7.0, 1, 2);
		m1.setDouble(1.0, 2, 2);

		Matrix m2 = m1.inv();

		assertEquals(getLabel(), -0.1970, m2.getDouble(0, 0), 0.001);
		assertEquals(getLabel(), 0.2879, m2.getDouble(1, 0), 0.001);
		assertEquals(getLabel(), 0.0152, m2.getDouble(2, 0), 0.001);
		assertEquals(getLabel(), 0.0303, m2.getDouble(0, 1), 0.001);
		assertEquals(getLabel(), -0.1212, m2.getDouble(1, 1), 0.001);
		assertEquals(getLabel(), 0.1515, m2.getDouble(2, 1), 0.001);
		assertEquals(getLabel(), 0.3788, m2.getDouble(0, 2), 0.001);
		assertEquals(getLabel(), -0.0152, m2.getDouble(1, 2), 0.001);
		assertEquals(getLabel(), -0.1061, m2.getDouble(2, 2), 0.001);
	}

	public static void main(String[] args) throws MatrixException {
		Matrix m = MatrixFactory.randn(5, 5);
		m.inv();
	}

}