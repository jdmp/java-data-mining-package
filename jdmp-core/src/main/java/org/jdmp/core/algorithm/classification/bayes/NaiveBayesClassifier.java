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

import java.util.LinkedList;
import java.util.List;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.algorithm.estimator.DensityEstimator;
import org.jdmp.core.algorithm.estimator.DiscreteDensityEstimator;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.DataSetFactory;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.sample.SampleFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.util.MathUtil;

public class NaiveBayesClassifier extends AbstractClassifier {
	private static final long serialVersionUID = -4962565315819543623L;

	private int classCount = 2;

	private List<Classifier> classifiers = null;

	@Override
	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		Matrix result = Matrix.Factory.zeros(1, classCount);
		for (int cc = 0; cc < classCount; cc++) {
			Classifier classifier = classifiers.get(cc);
			Matrix prediction = classifier.predict(input, sampleWeight);
			result.setAsDouble(prediction.getAsDouble(0, 0), 0, cc);
		}
		return result;
	}

	@Override
	public void reset() throws Exception {
		classifiers = null;
	}

	@Override
	public void train(RegressionDataSet dataSet) throws Exception {
		classifiers = new LinkedList<Classifier>();
		classCount = ((ClassificationDataSet) dataSet).getClassCount();
		for (int cc = 0; cc < classCount; cc++) {
			Classifier classifier = new NaiveBayesClassifier2Classes();
			ClassificationDataSet ds2 = DataSetFactory.classificationDataSet();
			for (Sample s1 : dataSet.getSampleMap()) {
				Sample s2 = SampleFactory.emptySample();
				Matrix i2 = Matrix.Factory.zeros(2, 1);
				double c = s1.getVariableMap().getMatrix(TARGET).getAsDouble(0, cc);
				i2.setAsBoolean(c == 0, 0, 0);
				i2.setAsBoolean(c == 1, 0, 0);
				s2.getVariableMap().setMatrix(INPUT, s1.getVariableMap().getMatrix(INPUT));
				Matrix weight = s1.getVariableMap().getMatrix(WEIGHT);
				if (weight != null) {
					s2.getVariableMap().setMatrix(WEIGHT, weight);
				}
				s2.getVariableMap().setMatrix(TARGET, i2);
				ds2.getSampleMap().add(s2);
			}
			classifier.train(ds2);
			classifiers.add(classifier);
		}
	}

	public Classifier emptyCopy() throws Exception {
		return new NaiveBayesClassifier();
	}

}

class NaiveBayesClassifier2Classes extends AbstractClassifier {
	private static final long serialVersionUID = -6511197233753905836L;

	private DensityEstimator[][] dists = null;

	private DensityEstimator classDists = null;

	private static final int CLASSCOUNT = 2;

	public NaiveBayesClassifier2Classes() {
		super();
	}

	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		double[] logs = new double[CLASSCOUNT];
		// for all classes
		for (int i = 0; i < CLASSCOUNT; i++) {
			// for all features
			logs[i] = Math.log(classDists.getProbability(i));
			for (int j = 0; j < input.getColumnCount(); j++) {
				int val = (int) input.getAsDouble(0, j);
				logs[i] += Math.log(dists[j][i].getProbability(val));
			}
		}
		double[] probs = MathUtil.logToProbs(logs);
		Matrix m = Matrix.Factory.zeros(1, 2);
		m.setAsDouble(probs[0], 0, 1);
		m.setAsDouble(probs[1], 0, 0);
		return m;
	}

	public void reset() throws Exception {
		dists = null;
		classDists = null;
	}

	public void train(RegressionDataSet dataSet) throws Exception {
		int featureCount = dataSet.getFeatureCount();
		Matrix dataSetInput = dataSet.getInputMatrix();
		Matrix max = dataSetInput.max(Ret.NEW, Matrix.ROW);

		this.dists = new DensityEstimator[featureCount][CLASSCOUNT];
		this.classDists = new DiscreteDensityEstimator(CLASSCOUNT, true);

		for (int i = 0; i < featureCount; i++) {
			for (int j = 0; j < CLASSCOUNT; j++) {
				dists[i][j] = new DiscreteDensityEstimator((int) max.getAsDouble(0, i) + 1, true);
			}
		}

		// go over all samples and count
		for (Sample s : dataSet.getSampleMap()) {
			Matrix sampleInput = s.getVariableMap().getMatrix(INPUT);
			Matrix sampleTarget = s.getVariableMap().getMatrix(TARGET);
			Matrix sampleWeight = s.getVariableMap().getMatrix(WEIGHT);

			double weight = 1.0;

			if (sampleWeight != null) {
				weight = sampleWeight.doubleValue();
			}

			int outputVal = (int) sampleTarget.getAsDouble(0, 0);
			for (int j = 0; j < sampleInput.getColumnCount(); j++) {
				int inputVal = (int) sampleInput.getAsDouble(0, j);
				dists[j][outputVal].addValue(inputVal, weight);
			}
			classDists.addValue(outputVal, weight);
		}
	}

	public Classifier emptyCopy() {
		return new NaiveBayesClassifier2Classes();
	}

}
