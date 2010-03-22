/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.jdmp.core.algorithm.classification.meta;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public class SemiSupervisedEM extends AbstractClassifier implements SemiSupervisedClassifier {
	private static final long serialVersionUID = 7362798845466035645L;

	private int iterations = 10;

	private Classifier classifier = null;

	private boolean useRawPrediction = false;

	public SemiSupervisedEM(Classifier singleClassClassifier, boolean useRawPrediction) {
		super();
		setLabel("SemiSupervisedEM [" + singleClassClassifier.toString() + "]");
		this.classifier = singleClassClassifier;
		this.useRawPrediction = useRawPrediction;
	}

	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		return classifier.predict(input, sampleWeight);
	}

	public void reset() throws Exception {
		classifier.reset();
	}

	public void train(RegressionDataSet dataSet) throws Exception {
		throw new Exception("use train(labeledData,unlabeledData)");
	}

	public void train(RegressionDataSet labeledData, RegressionDataSet unlabeledData)
			throws Exception {
		int classCount = ((ClassificationDataSet) labeledData).getClassCount();
		System.out.println("Step 0");
		classifier.reset();
		classifier.train(labeledData);
		classifier.predict(unlabeledData);
		for (Sample s : unlabeledData.getSamples()) {
			Matrix predicted = s.getVariables().getMatrix(Sample.PREDICTED);
			if (useRawPrediction) {
				s.getVariables().setMatrix(Sample.TARGET, predicted);
			} else {
				int max = (int) predicted.indexOfMax(Ret.NEW, Matrix.COLUMN).getAsDouble(0, 0);
				Matrix target = Matrix.factory.create(1, classCount);
				target.setAsDouble(1.0, 0, max);
				s.getVariables().setMatrix(Sample.TARGET, target);
			}
		}
		ClassificationDataSet completeData = new ClassificationDataSet();
		completeData.getSamples().addAll(labeledData.getSamples().toCollection());
		completeData.getSamples().addAll(unlabeledData.getSamples().toCollection());
		classifier.reset();
		classifier.train(completeData);

		for (int i = 0; i < iterations; i++) {
			System.out.println("Step " + (i + 1));
			classifier.predict(unlabeledData);
			for (Sample s : unlabeledData.getSamples()) {
				Matrix predicted = s.getVariables().getMatrix(Sample.PREDICTED);
				int max = (int) predicted.indexOfMax(Ret.NEW, Matrix.COLUMN).getAsDouble(0, 0);
				Matrix target = Matrix.factory.create(1, classCount);
				target.setAsDouble(1.0, 0, max);
				s.getVariables().setMatrix(Sample.TARGET, target);
			}
			completeData = new ClassificationDataSet();
			completeData.getSamples().addAll(labeledData.getSamples().toCollection());
			completeData.getSamples().addAll(unlabeledData.getSamples().toCollection());
			classifier.reset();
			classifier.train(completeData);
		}
	}

	public Classifier emptyCopy() throws Exception {
		return new SemiSupervisedEM(classifier.emptyCopy(), useRawPrediction);
	}

}
