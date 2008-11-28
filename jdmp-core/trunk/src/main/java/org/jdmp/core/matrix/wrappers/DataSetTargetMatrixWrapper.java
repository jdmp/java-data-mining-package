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

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class DataSetTargetMatrixWrapper extends AbstractDenseDoubleMatrix2D implements
		Wrapper<RegressionDataSet> {
	private static final long serialVersionUID = 5906451226992160036L;

	private RegressionDataSet dataSet = null;

	public DataSetTargetMatrixWrapper(RegressionDataSet ds) {
		this.dataSet = ds;
	}

	public long[] getSize() {
		if (dataSet.getSampleList().isEmpty()) {
			return Coordinates.ZERO2D;
		}
		Sample p = dataSet.getSampleList().getElementAt(0);
		if (p == null) {
			return Coordinates.ZERO2D;
		}
		return new long[] { dataSet.getSampleList().getSize(),
				p.getMatrix(Sample.TARGET).getColumnCount() };
	}

	public double getDouble(long row, long column) throws MatrixException {
		Sample p = dataSet.getSampleList().getElementAt((int) row);
		if (p != null) {
			return p.getMatrix(Sample.TARGET).getAsDouble(0, column);
		} else {
			return 0.0;
		}
	}

	public void setDouble(double value, long row, long column) throws MatrixException {
		Sample p = dataSet.getSampleList().getElementAt((int) row);
		if (p != null) {
			p.getMatrix(Sample.TARGET).setAsDouble(value, 0, column);
		}
	}

	public RegressionDataSet getWrappedObject() {
		return dataSet;
	}

	public void setWrappedObject(RegressionDataSet object) {
		this.dataSet = object;
	}

}
