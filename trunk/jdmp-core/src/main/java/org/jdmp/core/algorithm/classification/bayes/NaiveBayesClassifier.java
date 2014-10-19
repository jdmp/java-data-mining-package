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

package org.jdmp.core.algorithm.classification.bayes;

import java.util.List;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.algorithm.estimator.DensityEstimator;
import org.jdmp.core.algorithm.estimator.DiscreteDensityEstimator;
import org.jdmp.core.algorithm.estimator.GaussianDensityEstimator;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.collections.list.FastArrayList;
import org.ujmp.core.util.MathUtil;

public class NaiveBayesClassifier extends AbstractClassifier {
	private static final long serialVersionUID = -4962565315819543623L;

	private DensityEstimator[][] dists = null;

	private DensityEstimator classDists = null;

	private int classCount = -1;

	public NaiveBayesClassifier() {
	}

	public NaiveBayesClassifier(String inputLabel) {
		super(inputLabel);
	}

	public Matrix predict(Matrix input, Matrix sampleWeight) {
		input = input.toColumnVector(Ret.NEW);
		final double[] probs = new double[classCount];
		final double[] logs = new double[classCount];

		for (int i = 0; i < classCount; i++) {
			logs[i] += Math.log(classDists.getProbability(i));
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
		return m;
	}

	public void reset() {
		dists = null;
		classDists = null;
	}

	public void train(DataSet dataSet) {
		System.out.println("training started");
		int featureCount = (int) dataSet.getSampleMap().getElementAt(0).getMatrix(getInputLabel())
				.getValueCount();
		boolean discrete = isDiscrete(dataSet);
		classCount = getClassCount(dataSet);

		// todo: improve
		Matrix max = null;
		if (discrete) {
			List<Matrix> inputs = new FastArrayList<Matrix>();
			for (Sample s : dataSet.getSampleMap().values()) {
				inputs.add(s.getMatrix(getInputLabel()).toColumnVector(Ret.NEW));
			}
			Matrix dataSetInput = Matrix.Factory.vertCat(inputs);
			max = dataSetInput.max(Ret.NEW, Matrix.ROW);
		}

		this.dists = new DensityEstimator[featureCount][classCount];
		this.classDists = new DiscreteDensityEstimator(classCount);

		for (int i = 0; i < featureCount; i++) {
			for (int j = 0; j < classCount; j++) {
				if (discrete) {
					dists[i][j] = new DiscreteDensityEstimator(max.getAsInt(0, i) + 1);
				} else {
					dists[i][j] = new GaussianDensityEstimator();
				}
			}
		}

		System.out.println("density estimators created");

		// go over all samples and count
		for (Sample s : dataSet.getSampleMap()) {
			Matrix sampleInput = s.getMatrix(getInputLabel()).toColumnVector(Ret.LINK);
			Matrix sampleWeight = s.getMatrix(getWeightLabel());

			double weight = 1.0;

			if (sampleWeight != null) {
				weight = sampleWeight.doubleValue();
			}

			int outputClass = s.getTargetClass();
			for (int i = 0; i < sampleInput.getColumnCount(); i++) {
				double inputValue = sampleInput.getAsDouble(0, i);
				dists[i][outputClass].addValue(inputValue, weight);
			}
			classDists.addValue(outputClass, weight);
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
