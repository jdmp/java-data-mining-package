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

import org.jdmp.core.dataset.wrappers.DataSetPredictedMatrixWrapper;
import org.jdmp.core.dataset.wrappers.DataSetTargetMatrixWrapper;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;

public class RegressionDataSet extends DefaultDataSet {
	private static final long serialVersionUID = -3243395577983195632L;

	public static final String RMSE = "RMSE";

	public static final String PREDICTED = "Predicted";

	public static final String TARGET = "Target";

	public RegressionDataSet() {
		Matrix targetMatrix = new DataSetTargetMatrixWrapper(this);
		Variable target = VariableFactory.labeledVariable("Target");
		target.addInnerMatrix(targetMatrix);
		getVariableMap().put(TARGET, target);
		Matrix predictedMatrix = new DataSetPredictedMatrixWrapper(this);
		Variable predicted = VariableFactory.labeledVariable("Predicted");
		predicted.addInnerMatrix(predictedMatrix);
		getVariableMap().put(PREDICTED, predicted);
	}

	public Variable getRMSEVariable() {
		return getVariableMap().get(RMSE);
	}

	public Variable getTargetVariable() {
		return getVariableMap().get(TARGET);
	}

	public final long getTargetFeatureCount() {
		Matrix m = getTargetMatrix();
		if (m != null) {
			return m.getColumnCount();
		} else {
			return 0;
		}
	}

	public void appendRMSEMatrix(Matrix m) {
		getRMSEVariable().addInnerMatrix(m);
	}

	public Matrix getTargetMatrix() {
		return getTargetVariable().getLatestMatrix();
	}

	public double getEarlyStoppingRMSE(int numberOfSteps) {
		int index = getEarlyStoppingIndex(numberOfSteps);
		if (index >= 0) {
			return getRMSEVariable().getInnerMatrix(index).getEuklideanValue();
		}
		return -1;
	}

	public boolean isEarlyStoppingReached(int numberOfSteps) {
		return getEarlyStoppingIndex(numberOfSteps) >= 0;
	}

	public int getEarlyStoppingIndex(int numberOfSteps) {
		Variable v = getRMSEVariable();
		if (v.getInnerMatrixCount() <= numberOfSteps) {
			return -1;
		}

		double minRMSE = Double.MAX_VALUE;
		int position = -1;
		for (int i = 0; i < v.getInnerMatrixCount(); i++) {
			double e = v.getInnerMatrix(i).getEuklideanValue();
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
		return getVariableMap().get(PREDICTED);
	}

	public Matrix getPredictedMatrix() {
		return getPredictedVariable().getLatestMatrix();
	}

	public double getRMSE() {
		Matrix m = getRMSEMatrix();
		if (m == null) {
			return Double.NaN;
		} else {
			return m.getEuklideanValue();
		}
	}

	public Matrix getRMSEMatrix() {
		return getRMSEVariable().getLatestMatrix();
	}

}
