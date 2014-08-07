/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.jdmp.core.dataset.wrappers;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.factory.DenseDoubleMatrix2DFactory;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.Wrapper;

public class DataSetTargetMatrixWrapper extends AbstractDenseDoubleMatrix2D implements
		Wrapper<DataSet> {
	private static final long serialVersionUID = 5906451226992160036L;

	private DataSet dataSet = null;

	public DataSetTargetMatrixWrapper(DataSet ds) {
		super(0, 0);
		this.dataSet = ds;
	}

	public long[] getSize() {
		if (dataSet.getSampleMap().isEmpty()) {
			return Coordinates.ZERO2D;
		}
		Sample p = dataSet.getSampleMap().getElementAt(0);
		if (p == null) {
			return Coordinates.ZERO2D;
		}
		Matrix m = p.getMatrix(Sample.TARGET);
		if (m == null) {
			return Coordinates.ZERO2D;
		}
		return new long[] { dataSet.getSampleMap().getSize(), m.getColumnCount() };
	}

	public double getDouble(long row, long column) {
		return getDouble((int) row, (int) column);
	}

	public void setDouble(double value, long row, long column) {
		setDouble(value, (int) row, (int) column);
	}

	public double getDouble(int row, int column) {
		Sample p = dataSet.getSampleMap().getElementAt(row);
		if (p != null) {
			return p.getMatrix(Sample.TARGET).getAsDouble(0, column);
		} else {
			return 0.0;
		}
	}

	public void setDouble(double value, int row, int column) {
		Sample p = dataSet.getSampleMap().getElementAt(row);
		if (p != null) {
			p.getMatrix(Sample.TARGET).setAsDouble(value, 0, column);
		}
	}

	public DataSet getWrappedObject() {
		return dataSet;
	}

	public void setWrappedObject(DataSet object) {
		this.dataSet = object;
	}

	public DenseDoubleMatrix2DFactory<? extends DenseDoubleMatrix2D> getFactory() {
		throw new RuntimeException("not implemented");
	}

}
