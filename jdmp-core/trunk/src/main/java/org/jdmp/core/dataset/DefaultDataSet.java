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
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.DefaultObservableList;
import org.jdmp.core.util.DefaultObservableMap;
import org.jdmp.core.util.ObservableList;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public class DefaultDataSet extends AbstractDataSet {
	private static final long serialVersionUID = -2887879051530049677L;

	public static final String INPUT = "Input";

	private ObservableList<Sample> sampleList = null;

	private ObservableMap<Variable> variableList = null;

	private ObservableList<DataSet> dataSetList = null;

	public DefaultDataSet() {
		super();
		sampleList = new DefaultObservableList<Sample>();
		variableList = new DefaultObservableMap<Variable>();
		dataSetList = new DefaultObservableList<DataSet>();
		Matrix inputMatrix = new DataSetInputMatrixWrapper(this);
		Variable input = VariableFactory.labeledVariable("Input");
		input.addMatrix(inputMatrix);
		getVariables().put(INPUT, input);
	}

	public ObservableList<Sample> getSamples() {
		return sampleList;
	}

	public final ObservableMap<Variable> getVariables() {
		return variableList;
	}

	public final ObservableList<DataSet> getDataSets() {
		return dataSetList;
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

	@Override
	public DefaultDataSet clone() {
		DefaultDataSet ds = null;
		try {
			ds = this.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Sample s : getSamples()) {
			ds.getSamples().add(s.clone());
		}
		return ds;
	}

	@Override
	public void setSamples(ObservableList<Sample> samples) {
		this.sampleList = samples;
	}

	public void setVariables(ObservableMap<Variable> variables) {
		this.variableList = variables;
	}

	public void setDataSets(ObservableList<DataSet> dataSets) {
		this.dataSetList = dataSets;
	}

}
