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

package org.jdmp.core.algorithm.classification.bayes;

import java.util.HashSet;
import java.util.Set;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.algorithm.estimator.DensityEstimator;
import org.jdmp.core.algorithm.estimator.DiscreteDensityEstimator;
import org.jdmp.core.dataset.RegressionDataSet;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public class NaiveBayesClassifierTest extends AbstractClassifier {

	private static final long serialVersionUID = -6511197233753905836L;

	private DensityEstimator[][] dists = null;

	private DensityEstimator classDists = null;

	private int labelCount = 0;

	// private double[][][] conditionals = null;

	// private double[] classDist = null;

	public NaiveBayesClassifierTest() {
		super();
	}

	@Override
	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		double[] logs = new double[labelCount];
		long featureCount = input.getColumnCount();
		// for all classes
		for (int labelId = 0; labelId < labelCount; labelId++) {
			// for all features
			logs[labelId] = Math.log(classDists.getProbability(labelId));
			for (int featureId = 0; featureId < featureCount; featureId++) {
				int val = (int) input.getAsDouble(0, featureId);
				// logs[labelId] +=
				// Math.log(dists[featureId][labelId].getProbability(val));
				logs[labelId] += dists[featureId][labelId].getProbability(val);
			}
		}
		double[] probs = logToProbs(logs);
		Matrix m = MatrixFactory.zeros(1, 2);
		m.setAsDouble(probs[0], 0, 1);
		m.setAsDouble(probs[1], 0, 0);
		return m;
		// Matrix r=MatrixFactory.fromArray(probs).transpose();
	}

	/**
	 * @todo Put in common class!
	 * @param logs
	 * @return
	 */
	public static double[] logToProbs(double[] logs) {
		double[] probs = new double[logs.length];
		double sum = 0.0;
		for (int i = 0; i < probs.length; i++) {
			probs[i] = Math.exp(logs[i]);

			sum += probs[i];
		}
		for (int i = 0; i < probs.length; i++) {
			probs[i] = probs[i] / sum;
		}
		return probs;
	}

	@Override
	public void reset() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void train(RegressionDataSet dataSet) throws Exception {
		// labelCount = ((ClassificationDataSet) dataSet).getClassCount();
		// TODO: hack!!!
		labelCount = 2;
		int sampleCount = dataSet.getSampleList().getSize();
		int featureCount = dataSet.getFeatureCount();
		Matrix featureMatrix = dataSet.getInputMatrix();
		Matrix labelMatrix = dataSet.getTargetMatrix();
		Matrix maxMatrix = MatrixFactory.zeros(1, featureCount);

		for (int featureId = 0; featureId < featureCount; featureId++) {
			Matrix feature = featureMatrix.selectColumns(Ret.LINK, featureId);
			double max = 0;
			for (long[] c : feature.availableCoordinates()) {
				double v = feature.getAsDouble(c);
				if (v > max) {
					max = v;
				}
			}
			maxMatrix.setAsDouble(max, 0, featureId);
		}

		this.dists = new DensityEstimator[featureCount][labelCount];
		this.classDists = new DiscreteDensityEstimator(labelCount, true);

		for (int featureId = 0; featureId < featureCount; featureId++) {
			for (int labelId = 0; labelId < labelCount; labelId++) {
				dists[featureId][labelId] = new DiscreteDensityEstimator((int) maxMatrix
						.getAsDouble(0, featureId) + 1, true);
			}
		}

		// go over all samples and count

		// for (int sampleId = 0; sampleId < sampleCount; sampleId++) {
		// if (sampleId % 1000 == 0) {
		// System.out.println("Training on Sample " + sampleId);
		// }
		// int outputVal = (int) labelMatrix.getAsDouble(sampleId, 0);
		// for (int featureId = 0; featureId < featureCount; featureId++) {
		// int inputVal = (int) featureMatrix.getAsDouble(sampleId, featureId);
		// dists[featureId][outputVal].addValue(inputVal);
		// }
		// classDists.addValue(outputVal);
		// }

		for (int sampleId = 0; sampleId < sampleCount; sampleId++) {
			int outputVal = (int) labelMatrix.getAsDouble(sampleId, 0);
			classDists.addValue(outputVal);
		}

		for (int featureId = 0; featureId < featureCount; featureId++) {
			if (featureId % 1000 == 0) {
				System.out.println("Training on Feature " + featureId);
			}

			Matrix feature = featureMatrix.selectColumns(Ret.LINK, featureId);
			Set<Integer> nonZeros = new HashSet<Integer>();
			for (long[] c : feature.availableCoordinates()) {
				nonZeros.add((int) c[ROW]);
			}
			for (long[] c : labelMatrix.availableCoordinates()) {
				nonZeros.add((int) c[ROW]);
			}

			// for (int sampleId = 0; sampleId < sampleCount; sampleId++) {
			// int outputVal = (int) labelMatrix.getAsDouble(sampleId, 0);
			// int inputVal = (int) featureMatrix.getAsDouble(sampleId,
			// featureId);
			// dists[featureId][outputVal].addValue(inputVal);
			// }

			for (int sampleId : nonZeros) {
				int outputVal = (int) labelMatrix.getAsDouble(sampleId, 0);
				int inputVal = (int) featureMatrix.getAsDouble(sampleId, featureId);
				dists[featureId][outputVal].addValue(inputVal);
			}
			dists[featureId][0].addValue(0, sampleCount - nonZeros.size());

		}

	}

	@Override
	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) throws Exception {
		throw new MatrixException("sample by sample learning is not supported");
	}

	public Classifier emptyCopy() {
		return new NaiveBayesClassifierTest();
	}

}
