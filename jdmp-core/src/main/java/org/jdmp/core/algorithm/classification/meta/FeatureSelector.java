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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdmp.core.algorithm.regression.AbstractRegressor;
import org.jdmp.core.algorithm.regression.Regressor;
import org.jdmp.core.dataset.DefaultListDataSet;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.DefaultSample;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.collections.list.FastArrayList;
import org.ujmp.core.util.MathUtil;

public class FeatureSelector extends AbstractRegressor {
	private static final long serialVersionUID = -4290061842734681590L;

	public enum SelectionType {
		Random, MutualInformation, Covariance
	};

	private final Regressor learningAlgorithm;
	private final int selectedFeatureCount;
	private final List<Integer> selectedFeatures = new FastArrayList<Integer>();
	private final SelectionType selectionType;

	public FeatureSelector(SelectionType selectionType, Regressor learningAlgorithm,
			int featureCount) {
		this.selectionType = selectionType;
		this.learningAlgorithm = learningAlgorithm;
		this.selectedFeatureCount = featureCount;
	}

	private void selectFeatures(ListDataSet dataSet) {
		if (selectionType == SelectionType.Random) {
			selectedFeatures.addAll(MathUtil.sequenceListInt(0, getFeatureCount(dataSet)));
			while (selectedFeatures.size() > selectedFeatureCount) {
				selectedFeatures.remove(MathUtil.nextInteger(selectedFeatures.size()));
			}
			return;
		}

		final int featureCount = getFeatureCount(dataSet);
		final int targetCount = getClassCount(dataSet);

		Matrix m = createCompleteMatrix(dataSet);
		Matrix comp;
		if (selectionType == SelectionType.Covariance) {
			comp = m.cov(Ret.NEW, true, true);
		} else if (selectionType == SelectionType.MutualInformation) {
			comp = m.mutualInf(Ret.NEW);
		} else {
			comp = m.cov(Ret.NEW, true, true);
		}

		Set<Integer> featureSet = new HashSet<Integer>();

		while (featureSet.size() < selectedFeatureCount) {
			for (int t = 0; t < targetCount && featureSet.size() < selectedFeatureCount; t++) {
				double bestValue = 0;
				int bestFeature = -1;
				for (int f = 0; f < featureCount; f++) {
					double val = Math.abs(comp.getAsDouble(f, featureCount + t));
					if (val > bestValue && !featureSet.contains(f)) {
						bestValue = val;
						bestFeature = f;
					}
				}
				featureSet.add(bestFeature);
			}
		}
		selectedFeatures.addAll(featureSet);
		Collections.sort(selectedFeatures);
		System.out.println(selectedFeatures);
	}

	private Matrix createCompleteMatrix(ListDataSet dataSet) {
		final int sampleCount = dataSet.size();
		final int featureCount = getFeatureCount(dataSet);
		final int targetCount = getClassCount(dataSet);
		Matrix m = Matrix.Factory.zeros(sampleCount, featureCount + targetCount);
		for (int r = 0; r < sampleCount; r++) {
			Sample s = dataSet.get(r);
			Matrix input = s.getAsMatrix(getInputLabel()).toColumnVector(Ret.NEW);
			Matrix target = s.getAsMatrix(getTargetLabel()).toColumnVector(Ret.NEW);
			for (int c = 0; c < featureCount; c++) {
				m.setAsDouble(input.getAsDouble(0, c), r, c);
			}
			for (int c = 0; c < targetCount; c++) {
				m.setAsDouble(target.getAsDouble(0, c), r, c + featureCount);
			}
		}
		return m;
	}

	public void trainAll(ListDataSet dataSet) {
		selectFeatures(dataSet);

		ListDataSet newDataSet = new DefaultListDataSet();

		for (Sample s1 : dataSet) {
			Sample s2 = new DefaultSample();
			Matrix input1 = s1.getAsMatrix(getInputLabel()).toColumnVector(Ret.NEW);
			Matrix input2 = Matrix.Factory.zeros(1, selectedFeatures.size());
			for (int i = 0; i < selectedFeatures.size(); i++) {
				input2.setAsDouble(input1.getAsDouble(0, selectedFeatures.get(i)), 0, i);
			}
			s2.put(getInputLabel(), input2);
			s2.put(getTargetLabel(), s1.getAsMatrix(getTargetLabel()));
			newDataSet.add(s2);
		}

		learningAlgorithm.trainAll(newDataSet);
	}

	public void reset() {
		learningAlgorithm.reset();
		selectedFeatures.clear();
	}

	public Matrix predictOne(Matrix input) {
		input = input.toColumnVector(Ret.NEW);
		Matrix input2 = Matrix.Factory.zeros(1, selectedFeatures.size());
		for (int i = 0; i < selectedFeatures.size(); i++) {
			input2.setAsDouble(input.getAsDouble(0, selectedFeatures.get(i)), 0, i);
		}
		return learningAlgorithm.predictOne(input2);
	}

	public Regressor emptyCopy() {
		FeatureSelector r = new FeatureSelector(selectionType, learningAlgorithm.emptyCopy(),
				selectedFeatureCount);
		r.setInputLabel(getInputLabel());
		r.setTargetLabel(getTargetLabel());
		return r;
	}

}
