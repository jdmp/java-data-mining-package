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

package org.jdmp.core.matrix.wrappers;

import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.doublematrix.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class SampleInputOutputMatrix extends AbstractDenseDoubleMatrix2D implements Wrapper<Sample> {
	private static final long serialVersionUID = -8005076916609420357L;

	private Sample sample = null;

	public SampleInputOutputMatrix(Sample sample) {
		this.sample = sample;
	}

	public Matrix getInputMatrix() {
		Matrix m = sample.getMatrix(Sample.INPUT);
		return (m == null) ? MatrixFactory.linkToValue(0) : m;
	}

	public Matrix getTargetMatrix() {
		Matrix m = sample.getMatrix(Sample.TARGET);
		return (m == null) ? MatrixFactory.linkToValue(0) : m;
	}

	public long[] getSize() {
		return new long[] {
				Math.max(getInputMatrix().getRowCount(), getTargetMatrix().getRowCount()),
				getInputMatrix().getColumnCount() + getTargetMatrix().getColumnCount() };
	}

	public double getDouble(long row, long column) throws MatrixException {
		if (column < getInputMatrix().getColumnCount()) {
			return getInputMatrix().getAsDouble(row, column);
		} else {
			column -= getInputMatrix().getColumnCount();
			return getTargetMatrix().getAsDouble(row, column);
		}
	}

	public void setDouble(double value, long row, long column) throws MatrixException {
		if (column < getInputMatrix().getColumnCount()) {
			getInputMatrix().setAsDouble(value, row, column);
		} else {
			column -= getInputMatrix().getColumnCount();
			getTargetMatrix().setAsDouble(value, row, column);
		}
	}

	public Sample getWrappedObject() {
		return sample;
	}

	public void setWrappedObject(Sample object) {
		this.sample = object;
	}

}
