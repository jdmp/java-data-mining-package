/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

import org.jdmp.core.dataset.DefaultDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class DataSetInputMatrixWrapper extends AbstractDenseDoubleMatrix2D implements
		Wrapper<DefaultDataSet> {
	private static final long serialVersionUID = -817570097594349208L;

	public static final String INPUT = Variable.INPUT;

	private DefaultDataSet dataSet = null;

	public DataSetInputMatrixWrapper(DefaultDataSet ds) {
		this.dataSet = ds;
	}

	public long[] getSize() {
		try {
			if (dataSet.getSamples().isEmpty()) {
				return Coordinates.ZERO2D;
			}
			Sample p = dataSet.getSamples().getElementAt(0);
			if (p == null) {
				return Coordinates.ZERO2D;
			}
			Matrix input = p.getVariables().getMatrix(INPUT);
			if (input != null) {
				return new long[] { dataSet.getSamples().getSize(), input.getValueCount() };
			} else {
				return Coordinates.ZERO2D;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Coordinates.ZERO2D;
		}

	}

	public double getDouble(long row, long column) throws MatrixException {
		return getDouble((int) row, (int) column);
	}

	public void setDouble(double value, long row, long column) throws MatrixException {
		setDouble(value, (int) row, (int) column);
	}

	public double getDouble(int row, int column) throws MatrixException {
		try {
			Sample p = dataSet.getSamples().getElementAt(row);
			if (p != null) {
				Matrix m = p.getVariables().getMatrix(INPUT);
				if (m != null && Coordinates.product(m.getSize()) != 0) {
					long r = column / m.getColumnCount();
					long c = column % m.getColumnCount();
					return m.getAsDouble(r, c);
				}
			}
			return 0.0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}

	public void setDouble(double value, int row, int column) throws MatrixException {
		Sample p = dataSet.getSamples().getElementAt(row);
		if (p != null) {
			if (p.getVariables().getMatrix(INPUT) != null) {
				long r = column / p.getVariables().getMatrix(INPUT).getColumnCount();
				long c = column % p.getVariables().getMatrix(INPUT).getColumnCount();
				p.getVariables().getMatrix(INPUT).setAsDouble(value, r, c);
			}
		}
	}

	public DefaultDataSet getWrappedObject() {
		return dataSet;
	}

	public void setWrappedObject(DefaultDataSet object) {
		this.dataSet = object;
	}

}
