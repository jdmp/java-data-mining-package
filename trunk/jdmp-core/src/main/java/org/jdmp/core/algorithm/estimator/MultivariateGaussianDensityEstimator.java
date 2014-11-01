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

package org.jdmp.core.algorithm.estimator;

import org.jdmp.core.algorithm.regression.AbstractRegressor;
import org.jdmp.core.algorithm.regression.Regressor;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public class MultivariateGaussianDensityEstimator extends AbstractRegressor {
	private static final long serialVersionUID = -8923432381344117225L;

	private Matrix covarianceMatrix = null;
	private Matrix meanMatrix = null;
	private Matrix inverse = null;
	private double determinant = 0.0;
	private double factor = 0.0;
	private int dimensions = 0;

	public void trainAll(ListDataSet dataSet) {
		int featureCount = getFeatureCount(dataSet);
		int classCount = getClassCount(dataSet);
		dimensions = featureCount + classCount;
		Matrix x = Matrix.Factory.zeros(dataSet.size(), dimensions);

		int i = 0;
		for (Sample s : dataSet) {
			Matrix input = s.getMatrix(getInputLabel()).toColumnVector(Ret.LINK);
			for (int c = 0; c < featureCount; c++) {
				x.setAsDouble(input.getAsDouble(0, c), i, c);
			}
			Matrix target = s.getMatrix(getTargetLabel()).toColumnVector(Ret.LINK);
			for (int c = 0; c < classCount; c++) {
				x.setAsDouble(target.getAsDouble(0, c), i, c + featureCount);
			}
			i++;
		}

		meanMatrix = x.mean(Ret.NEW, Matrix.ROW, true);

		covarianceMatrix = x.cov(Ret.NEW, true, true);

		determinant = covarianceMatrix.det();
		if (determinant <= 0) {
			throw new RuntimeException("determinant <= 0");
		}

		inverse = covarianceMatrix.inv();

		factor = 1.0 / (Math.pow(2.0 * Math.PI, dimensions / 2.0) * Math.sqrt(determinant));
	}

	public void reset() {
		covarianceMatrix = null;
	}

	public double getDensity(Matrix input) {
		Matrix xmean = input.minus(meanMatrix);
		Matrix matrix = xmean.mtimes(inverse).mtimes(xmean.transpose());
		return factor * Math.exp(-0.5 * matrix.doubleValue());
	}

	public Matrix predictOne(Matrix input) {
		// TODO Auto-generated method stub
		return null;
	}

	public Regressor emptyCopy() {
		MultivariateGaussianDensityEstimator mvgd = new MultivariateGaussianDensityEstimator();
		mvgd.setInputLabel(getInputLabel());
		mvgd.setTargetLabel(getTargetLabel());
		return mvgd;
	}

}
