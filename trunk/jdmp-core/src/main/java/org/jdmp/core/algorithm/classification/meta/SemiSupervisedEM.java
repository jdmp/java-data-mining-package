/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import org.jdmp.core.algorithm.regression.AbstractRegressor;
import org.jdmp.core.algorithm.regression.Regressor;
import org.jdmp.core.dataset.DefaultListDataSet;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public class SemiSupervisedEM extends AbstractRegressor {
	private static final long serialVersionUID = 7362798845466035645L;

	private final int iterations;
	private final Regressor algorithm;
	private final ListDataSet unlabeledData;
	private final boolean useRawPrediction;

	public SemiSupervisedEM(Regressor algorithm, ListDataSet unlabeledData, int iterations,
			boolean useRawPrediction) {
		super();
		setLabel("SemiSupervisedEM [" + algorithm.toString() + "]");
		this.algorithm = algorithm;
		this.unlabeledData = unlabeledData;
		this.useRawPrediction = useRawPrediction;
		this.iterations = iterations;
	}

	public Matrix predictOne(Matrix input) {
		return algorithm.predictOne(input);
	}

	public void reset() {
		algorithm.reset();
	}

	public void trainAll(ListDataSet labeledData) {
		int classCount = getClassCount(labeledData);
		System.out.println("Step 0");
		algorithm.reset();
		algorithm.trainAll(labeledData);
		algorithm.predictAll(unlabeledData);
		for (Sample s : unlabeledData) {
			Matrix predicted = s.getAsMatrix(Sample.PREDICTED);
			if (useRawPrediction) {
				s.put(Sample.TARGET, predicted);
			} else {
				int max = (int) predicted.indexOfMax(Ret.NEW, Matrix.COLUMN).getAsDouble(0, 0);
				Matrix target = Matrix.Factory.zeros(1, classCount);
				target.setAsDouble(1.0, 0, max);
				s.put(Sample.TARGET, target);
			}
		}

		for (int i = 0; i < iterations; i++) {
			System.out.println("Step " + (i + 1));
			ListDataSet completeData = new DefaultListDataSet();
			completeData.addAll(labeledData);
			completeData.addAll(unlabeledData);
			algorithm.reset();
			algorithm.trainAll(completeData);
			algorithm.predictAll(unlabeledData);
			for (Sample s : unlabeledData) {
				Matrix predicted = s.getAsMatrix(Sample.PREDICTED);
				if (useRawPrediction) {
					s.put(Sample.TARGET, predicted);
				} else {
					int max = (int) predicted.indexOfMax(Ret.NEW, Matrix.COLUMN).getAsDouble(0, 0);
					Matrix target = Matrix.Factory.zeros(1, classCount);
					target.setAsDouble(1.0, 0, max);
					s.put(Sample.TARGET, target);
				}
			}
		}
	}

	public Regressor emptyCopy() {
		return new SemiSupervisedEM(algorithm.emptyCopy(), unlabeledData, iterations,
				useRawPrediction);
	}

}
