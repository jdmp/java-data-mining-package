/* ----------------------------------------------------------------------------
 * File:    AlgorithmClassifierNaiveBayes.java
 * Project: jdmp-core
 * Package: org.jdmp.core.algorithm.classification.bayes
 * ID:      $Id$
 *
 * ----------------------------------------------------------------------------
 * 
 * << short description of class >>
 *
 * ----------------------------------------------------------------------------
 *
 * Author: Andreas Naegele
 * Date:   03.05.2008
 * --------------------------------------------------------------------------*/

package org.jdmp.core.algorithm.classification.bayes;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.algorithm.estimator.DensityEstimator;
import org.jdmp.core.algorithm.estimator.DiscreteDensityEstimator;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.util.MathUtil;

public class NaiveBayesClassifier extends AbstractClassifier {

	private static final long serialVersionUID = -6511197233753905836L;

	private DensityEstimator[][] dists = null;

	private DensityEstimator classDists = null;

	private int classCount = 0;

	public NaiveBayesClassifier() {
		super("Naive Bayes");
	}

	@Override
	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		double[] logs = new double[classCount];
		// for all classes
		for (int i = 0; i < classCount; i++) {
			// for all features
			logs[i] = Math.log(classDists.getProbability(i));
			for (int j = 0; j < input.getColumnCount(); j++) {
				int val = (int) input.getDouble(0, j);
				logs[i] += Math.log(dists[j][i].getProbability((double) val));
			}
		}
		double[] probs = MathUtil.logToProbs(logs);
		Matrix m = MatrixFactory.zeros(1, 2);
		m.setDouble(probs[0], 0, 1);
		m.setDouble(probs[1], 0, 0);
		return m;
		// Matrix r=MatrixFactory.fromArray(probs).transpose();
	}

	@Override
	public void reset() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void train(RegressionDataSet dataSet) throws Exception {
		classCount = ((ClassificationDataSet) dataSet).getClassCount();
		int featureCount = dataSet.getFeatureCount();

		Matrix dataSetInput = dataSet.getInputMatrix();
		Matrix dataSetTarget = dataSet.getTargetMatrix();
		Matrix max = dataSetInput.max(Ret.NEW, Matrix.ROW);

		this.dists = new DensityEstimator[featureCount][classCount];
		this.classDists = new DiscreteDensityEstimator(classCount, true);

		for (int i = 0; i < featureCount; i++) {
			for (int j = 0; j < classCount; j++) {
				dists[i][j] = new DiscreteDensityEstimator((int) max.getDouble(0, i) + 1, true);
			}
		}

		// go over all samples and count
		for (int i = 0; i < dataSet.getSampleCount(); i++) {

			if (i % 1000 == 0) {
				System.out.println("Training on Sample " + i);
			}

			ClassificationSample s = (ClassificationSample) dataSet.getSample(i);
			Matrix sampleInput = s.getMatrix(INPUT);
			Matrix sampleTarget = s.getMatrix(TARGET);

			double weight = 1.0;

			int outputVal = (int) sampleTarget.getDouble(0, 0);

			// if (sampleInput.isSparse()) {
			// long count = 0;
			// for (long[] c : sampleInput.availableCoordinates()) {
			// count++;
			// int inputVal = (int) sampleInput.getDouble(c);
			// dists[(int) c[COLUMN]][outputVal].addValue((double) inputVal,
			// weight);
			// }
			// long diff = sampleInput.getColumnCount() - count;
			// dists[0][outputVal].addValue(diff, weight * diff);
			// } else {
			for (int j = 0; j < sampleInput.getColumnCount(); j++) {
				int inputVal = (int) sampleInput.getDouble(0, j);
				dists[j][outputVal].addValue((double) inputVal, weight);
			}
			// }

			classDists.addValue(outputVal, weight);
		}

		double weight = 1.0;
		for (int classId = 0; classId < dataSetTarget.getColumnCount(); classId++) {
			Matrix oneClass = dataSetTarget.selectColumns(Ret.LINK, classId);
			for (long[] co : oneClass.availableCoordinates()) {
				classDists.addValue(classId, weight);
			}
		}

		for (int featureId = 0; featureId < dataSetInput.getColumnCount(); featureId++) {

			if (featureId % 1000 == 0) {
				System.out.println("Training on Feature " + featureId);
			}

			weight = 1.0;
			Matrix feature = dataSetInput.selectColumns(Ret.LINK, featureId);

			long count = 0;
			for (long[] sampleId : feature.availableCoordinates()) {
				count++;
				int inputVal = (int) feature.getDouble(sampleId[ROW], featureId);

				for (int classId = 0; classId < classCount; classId++) {
					int outputVal = (int) dataSetTarget.getDouble(sampleId[ROW], classId);
					dists[featureId][outputVal].addValue((double) inputVal, weight);
				}

			}

			long diff = feature.getRowCount() - count;
			dists[0][outputVal].addValue(diff, weight * diff);
		}

	}

	@Override
	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) throws Exception {
		throw new MatrixException("sample by sample learning is not supported");
	}

	public Classifier emptyCopy() {
		return new NaiveBayesClassifier();
	}

}
