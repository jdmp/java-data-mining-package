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

import java.util.HashSet;
import java.util.Set;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.algorithm.estimator.DensityEstimator;
import org.jdmp.core.algorithm.estimator.DiscreteDensityEstimator;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.RegressionDataSet;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.doublecalculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class MultiLabelNaiveBayesClassifier extends AbstractClassifier {

	private static final long serialVersionUID = -6511197233753905836L;

	private DensityEstimator[][][] dists = null;

	private DensityEstimator[] labelDists = null;

	private int labelCount = 0;

	private int classCount = 2;

	public MultiLabelNaiveBayesClassifier() {
		super("Naive Bayes");
	}

	@Override
	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		double[][] logs = new double[labelCount][classCount];
		// for all labels
		for (int labelId = 0; labelId < labelCount; labelId++) {
			// for all classes (0 and 1)
			for (int classId = 0; classId < classCount; classId++) {
				// for all features
				logs[labelId][classId] = Math.log(labelDists[labelId].getProbability(classId));
				for (int featureId = 0; featureId < input.getColumnCount(); featureId++) {
					double val = input.getAsDouble(0, featureId);
					//logs[labelId][classId] += Math.log(dists[featureId][labelId][classId]
					//		.getProbability((double) val));
					logs[labelId][classId] += dists[featureId][labelId][classId]
							.getProbability((double) val);
				}
			}
		}

		double[][] probs = new double[labelCount][];
		for (int labelId = 0; labelId < labelCount; labelId++) {
			probs[labelId] = MathUtil.logToProbs(logs[labelId]);
		}
		Matrix m = MatrixFactory.zeros(1, labelCount);
		for (int labelId = 0; labelId < labelCount; labelId++) {
			m.setAsDouble(probs[labelId][1], 0, labelId);
		}
		return m;
	}

	@Override
	public void reset() throws Exception {
		dists = null;
		labelDists = null;
	}

	@Override
	public void train(RegressionDataSet dataSet) throws Exception {
		labelCount = ((ClassificationDataSet) dataSet).getClassCount();
		int featureCount = dataSet.getFeatureCount();
		int sampleCount = dataSet.getSampleCount();

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

		dists = new DensityEstimator[featureCount][labelCount][classCount];
		labelDists = new DensityEstimator[labelCount];

		for (int i = 0; i < featureCount; i++) {
			for (int j = 0; j < labelCount; j++) {
				for (int k = 0; k < classCount; k++) {
					dists[i][j][k] = new DiscreteDensityEstimator(
							(int) maxMatrix.getAsDouble(0, i) + 1, true);
				}
			}
		}
		for (int j = 0; j < labelCount; j++) {
			labelDists[j] = new DiscreteDensityEstimator(classCount, true);
		}

		double weight = 1.0;
		for (int labelId = 0; labelId < labelMatrix.getColumnCount(); labelId++) {
			Matrix oneLabel = labelMatrix.selectColumns(Ret.LINK, labelId);
			long count = 0;
			for (long[] co : oneLabel.availableCoordinates()) {
				labelDists[labelId].addValue(1, weight);
				count++;
			}
			long diff = sampleCount - count;
			labelDists[labelId].addValue(0, diff * weight);
		}

		for (int featureId = 0; featureId < featureMatrix.getColumnCount(); featureId++) {

			if (featureId % 1000 == 0) {
				System.out.println("Training on Feature " + featureId);
			}

			weight = 1.0;
			Matrix feature = featureMatrix.selectColumns(Ret.LINK, featureId);

			for (int labelId = 0; labelId < labelCount; labelId++) {

				for (int classId = 0; classId < classCount; classId++) {

					Set<Integer> nonzeros = new HashSet<Integer>();
					for (long[] sampleId : feature.availableCoordinates()) {
						nonzeros.add((int) sampleId[ROW]);
					}
					for (long[] sampleId : labelMatrix.availableCoordinates()) {
						nonzeros.add((int) sampleId[ROW]);
					}
					for (int sampleId : nonzeros) {
						double val = feature.getAsDouble(sampleId, 0);
						dists[featureId][labelId][classId].addValue(val, weight);
					}
					long diff = sampleCount - nonzeros.size();
					dists[featureId][labelId][classId].addValue(0, diff * weight);

				}

			}

		}

	}

	@Override
	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) throws Exception {
		throw new MatrixException("sample by sample learning is not supported");
	}

	public Classifier emptyCopy() {
		return new MultiLabelNaiveBayesClassifier();
	}

}
