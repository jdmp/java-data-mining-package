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

package org.jdmp.core.dataset;

import org.jdmp.core.matrix.MatrixList;
import org.jdmp.core.matrix.wrappers.DataSetInputMatrixWrapper;
import org.jdmp.core.sample.DefaultSample;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public class BasicDataSet extends AbstractDataSet {
	private static final long serialVersionUID = -2887879051530049677L;

	protected Matrix inputMatrix = null;

	protected MatrixList matrixList = null;

	public BasicDataSet(Variable v) {
		this(v.getLabel());
		for (Matrix m : v.getMatrixList()) {
			DefaultSample s = new DefaultSample(m);
			getSampleList().add(s);
		}
	}

	public BasicDataSet(String label) {
		super(label);
	}

	public BasicDataSet() {
		super();
	}

	public final void standardize(int dimension) throws MatrixException {
		getInputMatrix().standardize(Ret.ORIG, dimension, true);
	}

	public final void center(int dimension) throws MatrixException {
		getInputMatrix().center(Ret.ORIG, dimension, true);
	}

	public final Matrix getInputMatrix() {
		if (inputMatrix == null) {
			inputMatrix = new DataSetInputMatrixWrapper(this);
		}
		return inputMatrix;
	}

	public MatrixList getMatrixList() {
		if (matrixList == null) {
			matrixList = new MatrixList();
			matrixList.add(getInputMatrix());
		}
		return matrixList;
	}

}
