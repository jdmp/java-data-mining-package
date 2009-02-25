/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.jdmp.core.dataset;

import org.jdmp.core.dataset.wrappers.DataSetInputMatrixWrapper;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public class BasicDataSet extends AbstractDataSet {
	private static final long serialVersionUID = -2887879051530049677L;

	public static final String INPUT = "Input";

	public BasicDataSet() {
		super();
		Matrix inputMatrix = new DataSetInputMatrixWrapper(this);
		Variable input = VariableFactory.labeledVariable("Input");
		input.addMatrix(inputMatrix);
		getVariables().put(INPUT, input);
	}

	public final Variable getInputVariable() {
		return getVariables().get(INPUT);
	}

	public final void standardize(int dimension) throws MatrixException {
		getInputVariable().getMatrix().standardize(Ret.ORIG, dimension, true);
	}

	public final void center(int dimension) throws MatrixException {
		getInputVariable().getMatrix().center(Ret.ORIG, dimension, true);
	}

	public void addMissingValues(int dimension, double percentMissing) throws MatrixException {
		getInputVariable().getMatrix().addMissing(Ret.ORIG, dimension, percentMissing);
	}

	public Matrix getInputMatrix() {
		return getInputVariable().getMatrix();
	}

	public int getFeatureCount() {
		Matrix m = getInputVariable().getMatrix();
		if (m == null) {
			return 0;
		} else {
			return (int) m.getColumnCount();
		}
	}

}
