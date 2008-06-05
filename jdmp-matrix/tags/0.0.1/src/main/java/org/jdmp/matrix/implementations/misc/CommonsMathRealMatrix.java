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

package org.jdmp.matrix.implementations.misc;

import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealMatrixImpl;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class CommonsMathRealMatrix extends AbstractDenseDoubleMatrix2D implements Wrapper<RealMatrixImpl> {
	private static final long serialVersionUID = -1161807620507675926L;

	private RealMatrixImpl matrix = null;

	public CommonsMathRealMatrix(long... size) {
		matrix = new RealMatrixImpl((int) size[ROW], (int) size[COLUMN]);
	}

	public CommonsMathRealMatrix(org.jdmp.matrix.Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getDouble(c), c);
		}
	}

	public CommonsMathRealMatrix(RealMatrix matrix) {
		if (matrix instanceof RealMatrixImpl) {
			this.matrix = (RealMatrixImpl) matrix;
		} else {
			throw new MatrixException("Can oly use RealMatrixImpl");
		}
	}

	public RealMatrixImpl getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(RealMatrixImpl object) {
		this.matrix = object;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return matrix.getEntry((int) coordinates[ROW], (int) coordinates[COLUMN]);
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		matrix.getDataRef()[(int) coordinates[ROW]][(int) coordinates[COLUMN]] = value;
	}

	public long[] getSize() {
		return new long[] { matrix.getRowDimension(), matrix.getColumnDimension() };
	}

	public Matrix inv() {
		return new CommonsMathRealMatrix(matrix.inverse());
	}

}