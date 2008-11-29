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

import org.jdmp.core.matrix.wrappers.DataSetPredictedMatrixWrapper;
import org.jdmp.core.matrix.wrappers.DataSetTargetMatrixWrapper;
import org.jdmp.core.sample.DefaultSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.DefaultVariable;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public class RegressionDataSet extends BasicDataSet {
	private static final long serialVersionUID = -3243395577983195632L;

	public static final String RMSE = "RMSE";

	public static final String PREDICTED = "Predicted";

	public static final String TARGET = "Target";

	public RegressionDataSet(String label) {
		super(label);
		getVariables().put(RMSE, new DefaultVariable("RMSE", 10000));
		Matrix targetMatrix = new DataSetTargetMatrixWrapper(this);
		Variable target = VariableFactory.labeledVariable("Target");
		target.addMatrix(targetMatrix);
		getVariables().put(TARGET, target);
		Matrix predictedMatrix = new DataSetPredictedMatrixWrapper(this);
		Variable predicted = VariableFactory.labeledVariable("Predicted");
		predicted.addMatrix(predictedMatrix);
		getVariables().put(PREDICTED, predicted);
	}

	public RegressionDataSet() {
		this(null);
	}

	public static RegressionDataSet copyFromMatrix(Matrix input, Matrix target)
			throws MatrixException {
		RegressionDataSet ds = new RegressionDataSet();
		for (int i = 0; i < input.getRowCount(); i++) {
			Sample s = new DefaultSample();
			Matrix in = input.subMatrix(Ret.NEW, i, 0, i, input.getColumnCount() - 1);
			Matrix out = target.subMatrix(Ret.NEW, i, 0, i, target.getColumnCount() - 1);
			s.setMatrix(Sample.INPUT, in);
			s.setMatrix(Sample.TARGET, out);
			ds.getSamples().add(s);
		}
		return ds;
	}

	public RegressionDataSet(String label, Matrix input, Matrix target) {
		this(label);
		long count = input.getRowCount();
		for (int i = 0; i < count; i++) {
			Sample s = new DefaultSample();

			Matrix si = input.subMatrix(Ret.LINK, i, 0, i, input.getColumnCount() - 1);
			Matrix so = target.subMatrix(Ret.LINK, i, 0, i, target.getColumnCount() - 1);

			s.setMatrix(Sample.INPUT, si);
			s.setMatrix(Sample.TARGET, so);

			getSamples().add(s);
		}
	}

	public RegressionDataSet(Matrix input, Matrix targetOutput) {
		this(null, input, targetOutput);
	}

	public static final RegressionDataSet create(String label, Matrix input, Matrix targetOutput) {
		return new RegressionDataSet(label, input, targetOutput);
	}

	public static final RegressionDataSet create(Matrix input, Matrix targetOutput) {
		return new RegressionDataSet(null, input, targetOutput);
	}

	public Variable getRMSEVariable() {
		return getVariables().get(RMSE);
	}

	public Variable getTargetVariable() {
		return getVariables().get(TARGET);
	}

	public void appendRMSEMatrix(Matrix m) {
		getRMSEVariable().addMatrix(m);
	}

	public Matrix getTargetMatrix() {
		return getTargetVariable().getMatrix();
	}

	public double getEarlyStoppingRMSE(int numberOfSteps) {
		int index = getEarlyStoppingIndex(numberOfSteps);
		if (index >= 0) {
			return getRMSEVariable().getMatrix(index).getEuklideanValue();
		}
		return -1;
	}

	public boolean isEarlyStoppingReached(int numberOfSteps) {
		return getEarlyStoppingIndex(numberOfSteps) >= 0;
	}

	public int getEarlyStoppingIndex(int numberOfSteps) {
		Variable v = getRMSEVariable();
		if (v.getMatrixCount() <= numberOfSteps) {
			return -1;
		}

		double minRMSE = Double.MAX_VALUE;
		int position = -1;
		for (int i = 0; i < v.getMatrixCount(); i++) {
			double e = v.getMatrix(i).getEuklideanValue();
			if (e < minRMSE) {
				minRMSE = e;
				position = i;
			}
			if (i == position + numberOfSteps) {
				return position;
			}
		}
		return -1;
	}

	public Variable getPredictedVariable() {
		return getVariables().get(PREDICTED);
	}

	public Matrix getPredictedMatrix() {
		return getPredictedVariable().getMatrix();
	}

	public double getRMSE() throws MatrixException {
		Matrix m = getRMSEMatrix();
		if (m == null) {
			return Double.NaN;
		} else {
			return m.getEuklideanValue();
		}
	}

	public Matrix getRMSEMatrix() {
		return getRMSEVariable().getMatrix();
	}

}
