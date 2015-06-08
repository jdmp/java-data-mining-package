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
	private double factor = 0.0;
	private int dimensions = 0;
	private int featureCount = 0;
	private int classCount = 0;

	public void trainAll(ListDataSet dataSet) {
		featureCount = getFeatureCount(dataSet);
		classCount = getClassCount(dataSet);
		dimensions = featureCount + classCount;
		Matrix x = Matrix.Factory.zeros(dataSet.size(), dimensions);

		int i = 0;
		for (Sample s : dataSet) {
			Matrix input = s.getAsMatrix(getInputLabel()).toColumnVector(Ret.LINK);
			for (int c = 0; c < featureCount; c++) {
				x.setAsDouble(input.getAsDouble(0, c), i, c);
			}
			Matrix target = s.getAsMatrix(getTargetLabel()).toColumnVector(Ret.LINK);
			for (int c = 0; c < classCount; c++) {
				x.setAsDouble(target.getAsDouble(0, c), i, c + featureCount);
			}
			i++;
		}

		meanMatrix = x.mean(Ret.NEW, Matrix.ROW, true);
		covarianceMatrix = x.cov(Ret.NEW, true, true);
		try {
			inverse = covarianceMatrix.inv();
			factor = 1.0 / Math.sqrt(covarianceMatrix.det() * Math.pow(2.0 * Math.PI, dimensions));
		} catch (Exception e) {
			inverse = covarianceMatrix.pinv();
			factor = 1.0;
		}
	}

	public void reset() {
		covarianceMatrix = null;
	}

	public double getDensity(Matrix input) {
		Matrix xmean = input.minus(meanMatrix);
		Matrix matrix = xmean.mtimes(inverse).mtimes(xmean.transpose());
		return factor * Math.exp(-0.5 * matrix.doubleValue());
	}

	public double getDensityUnscaled(Matrix input) {
		Matrix xmean = input.minus(meanMatrix);
		Matrix matrix = xmean.mtimes(inverse).mtimes(xmean.transpose());
		return Math.exp(-0.5 * matrix.doubleValue());
	}

	public Matrix predictOne(Matrix input) {
		input = input.toColumnVector(Ret.NEW);
		Matrix x = Matrix.Factory.zeros(1, dimensions);
		for (int i = 0; i < featureCount; i++) {
			x.setAsDouble(input.getAsDouble(0, i), 0, i);
		}
		Matrix result = Matrix.Factory.zeros(1, classCount);
		double sum = 0;
		for (int i = 0; i < classCount; i++) {
			if (i > 0) {
				x.setAsDouble(0, 0, featureCount + i - 1);
			}
			x.setAsDouble(1, 0, featureCount + i);
			double density = getDensity(x);
			result.setAsDouble(density, 0, i);
			sum += density;
		}
		for (int i = 0; i < classCount; i++) {
			result.setAsDouble(result.getAsDouble(0, i) / sum, 0, i);
		}
		return result;
	}

	public Regressor emptyCopy() {
		MultivariateGaussianDensityEstimator mvgd = new MultivariateGaussianDensityEstimator();
		mvgd.setInputLabel(getInputLabel());
		mvgd.setTargetLabel(getTargetLabel());
		return mvgd;
	}

	public static double getDensity(Matrix x, Matrix mean, Matrix covariance) {
		Matrix xmean = x.minus(mean);
		Matrix inverse = covariance.inv();
		double f = 1.0 / Math.sqrt(covariance.det() * Math.pow(2.0 * Math.PI, x.getColumnCount()));
		Matrix matrix = xmean.mtimes(inverse).mtimes(xmean.transpose());
		return f * Math.exp(-0.5 * matrix.doubleValue());
	}

	public static double getDensityUnscaled(Matrix x, Matrix mean, Matrix covariance) {
		Matrix xmean = x.minus(mean);
		Matrix inverse = covariance.inv();
		Matrix matrix = xmean.mtimes(inverse).mtimes(xmean.transpose());
		return Math.exp(-0.5 * matrix.doubleValue());
	}

}
