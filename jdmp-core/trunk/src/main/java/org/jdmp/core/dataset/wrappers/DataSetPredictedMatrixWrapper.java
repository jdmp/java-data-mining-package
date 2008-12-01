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

package org.jdmp.core.dataset.wrappers;

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class DataSetPredictedMatrixWrapper extends AbstractDenseDoubleMatrix2D implements
		Wrapper<RegressionDataSet> {
	private static final long serialVersionUID = 5197402551973462998L;

	private RegressionDataSet dataSet = null;

	public DataSetPredictedMatrixWrapper(RegressionDataSet ds) {
		this.dataSet = ds;
	}

	public long[] getSize() {
		if (dataSet.getSamples().isEmpty()) {
			return Coordinates.ZERO2D;
		}
		Sample p = dataSet.getSamples().getElementAt(0);
		if (p == null) {
			return Coordinates.ZERO2D;
		}
		Matrix output = p.getMatrix(Sample.PREDICTED);
		if (output != null) {
			return new long[] { dataSet.getSamples().getSize(), output.getValueCount() };
		} else {
			return Coordinates.ZERO2D;
		}

	}

	public double getDouble(long row, long column) throws MatrixException {
		Sample p = dataSet.getSamples().getElementAt((int) row);
		if (p != null) {
			if (p.getMatrix(Sample.PREDICTED) != null) {
				long r = column / p.getMatrix(Sample.PREDICTED).getColumnCount();
				long c = column % p.getMatrix(Sample.PREDICTED).getColumnCount();
				return p.getMatrix(Sample.PREDICTED).getAsDouble(r, c);
			}
		}
		return 0.0;
	}

	public void setDouble(double value, long row, long column) throws MatrixException {
		Sample p = dataSet.getSamples().getElementAt((int) row);
		if (p != null) {
			if (p.getMatrix(Sample.PREDICTED) != null) {
				long r = column / p.getMatrix(Sample.PREDICTED).getColumnCount();
				long c = column % p.getMatrix(Sample.PREDICTED).getColumnCount();
				p.getMatrix(Sample.PREDICTED).setAsDouble(value, r, c);
			}
		}
	}

	public RegressionDataSet getWrappedObject() {
		return dataSet;
	}

	public void setWrappedObject(RegressionDataSet object) {
		this.dataSet = object;
	}

}
