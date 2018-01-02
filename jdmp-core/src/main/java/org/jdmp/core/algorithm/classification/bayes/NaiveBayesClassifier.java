/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.algorithm.estimator.DensityEstimator;
import org.jdmp.core.algorithm.estimator.GaussianDensityEstimator;
import org.jdmp.core.algorithm.estimator.GeneralDensityEstimator;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.util.MathUtil;

public class NaiveBayesClassifier extends AbstractClassifier {
	private static final long serialVersionUID = -4962565315819543623L;

	private DensityEstimator[][] dists = null;

	private DensityEstimator[] classDists = null;

	private int classCount = -1;

	public NaiveBayesClassifier() {
	}

	public NaiveBayesClassifier(String inputLabel) {
		super(inputLabel);
	}

	public void predictOne(Sample sample) {
		Matrix input = sample.getAsMatrix(INPUT);
		input = input.toColumnVector(Ret.LINK);
		final double[] probs = new double[classCount];
		final double[] logs = new double[classCount];

		for (int j = 0; j < classCount; j++) {
			logs[j] += Math.log(classDists[j].getProbability(1.0));
		}

		// for all features
		for (int j = 0; j < input.getColumnCount(); j++) {
			// for all classes
			double probSum = 0;
			for (int i = 0; i < classCount; i++) {
				double value = input.getAsDouble(0, j);
				double probability = dists[j][i].getProbability(value);
				probs[i] = probability;
				probSum += probability;
			}
			for (int i = 0; i < classCount; i++) {
				logs[i] += Math.log(probs[i] / probSum);
			}
		}

		final double[] finalProbs = MathUtil.logToProbs(logs);
		Matrix m = Matrix.Factory.linkToArray(finalProbs).transpose();
		sample.put(PREDICTED, m);
	}

	public void reset() {
		dists = null;
		classDists = null;
	}

	public void trainAll(ListDataSet dataSet) {
		System.out.println("training started");
		int featureCount = (int) dataSet.get(0).getAsMatrix(getInputLabel()).getValueCount();
		boolean discrete = isDiscrete(dataSet);
		classCount = getClassCount(dataSet);

		this.dists = new DensityEstimator[featureCount][classCount];
		this.classDists = new DensityEstimator[classCount];

		for (int j = 0; j < classCount; j++) {
			classDists[j] = new GeneralDensityEstimator();
			for (int i = 0; i < featureCount; i++) {
				if (discrete) {
					dists[i][j] = new GeneralDensityEstimator();
				} else {
					dists[i][j] = new GaussianDensityEstimator();
				}
			}
		}

		System.out.println("density estimators created");

		int count = 0;
		for (Sample s : dataSet) {

			final Matrix sampleInput = s.getAsMatrix(getInputLabel()).toColumnVector(Ret.LINK);
			final Matrix sampleTarget = s.getAsMatrix(getTargetLabel()).toColumnVector(Ret.LINK);
			final double weight = s.getWeight();

			for (int j = 0; j < classCount; j++) {
				double classValue = sampleTarget.getAsDouble(0, j);
				if (classValue == 0.0) {
					classDists[j].addValue(0.0, weight);
				} else {
					classDists[j].addValue(1.0, weight);
					for (int i = 0; i < sampleInput.getColumnCount(); i++) {
						double inputValue = sampleInput.getAsDouble(0, i);
						dists[i][j].addValue(inputValue, weight);
					}
				}
			}

			count++;
			if (count % 10000 == 0) {
				System.out.println(count);
			}
		}

		System.out.println("training finished");
	}

	public Classifier emptyCopy() {
		NaiveBayesClassifier nb = new NaiveBayesClassifier();
		nb.setInputLabel(getInputLabel());
		nb.setTargetLabel(getTargetLabel());
		return nb;
	}
}
