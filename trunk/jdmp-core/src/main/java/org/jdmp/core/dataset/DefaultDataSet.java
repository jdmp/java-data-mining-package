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

package org.jdmp.core.dataset;

import org.jdmp.core.dataset.wrappers.DataSetInputMatrixWrapper;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public class DefaultDataSet extends AbstractDataSet {
	private static final long serialVersionUID = -2887879051530049677L;

	

	public DefaultDataSet() {
		super();
		Matrix inputMatrix = new DataSetInputMatrixWrapper(this);
		Variable input = VariableFactory.labeledVariable("Input");
		input.add(inputMatrix);
		getVariableMap().put(INPUT, input);
	}

	public final long getInputFeatureCount() {
		Matrix m = getInputMatrix();
		if (m != null) {
			return m.getColumnCount();
		} else {
			return 0;
		}
	}

	public final void standardize(int dimension) {
		getInputVariable().getLast().standardize(Ret.ORIG, dimension);
	}

	public final void center(int dimension) {
		getInputVariable().getLast().center(Ret.ORIG, dimension, true);
	}

	public void addMissingValues(int dimension, double percentMissing) {
		getInputVariable().getLast().addMissing(Ret.ORIG, dimension, percentMissing);
	}

	public int getFeatureCount() {
		return (int) getSampleMap().values().iterator().next().getMatrix(INPUT)
				.toRowVector(Ret.NEW).getRowCount();
	}

	public DefaultDataSet clone() {
		DefaultDataSet ds = null;
		try {
			ds = this.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Sample s : getSampleMap()) {
			ds.getSampleMap().add(s.clone());
		}
		return ds;
	}

}
