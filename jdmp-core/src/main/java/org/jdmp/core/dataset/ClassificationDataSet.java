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

package org.jdmp.core.dataset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.MathUtil;

public class ClassificationDataSet extends RegressionDataSet {
	private static final long serialVersionUID = 3969274321783319184L;

	public static final String ACCURACY = Variable.ACCURACY;

	public static final String CONFUSION = Variable.CONFUSION;

	public static final String ERRORCOUNT = Variable.ERRORCOUNT;

	public Matrix getClassDistribution() {
		Matrix m = Matrix.Factory.zeros(getClassCount(), 1);

		Map<Integer, Double> map = new HashMap<Integer, Double>();

		for (Sample s : getSampleMap()) {
			int c = s.getTargetClass();
			Double d = map.get(c);
			if (d == null) {
				d = 0.0;
			}
			d++;
			map.put(c, d);
		}

		for (int i = 0; i < getClassCount(); i++) {
			Double d = map.get(i);
			if (d == null) {
				d = 0.0;
			}
			m.setAsDouble(d / getSampleMap().getSize(), i, 0);
		}
		return m;
	}

	public double getEarlyStoppingErrorCount(int numberOfSteps) {
		int index = getEarlyStoppingIndex(numberOfSteps);
		if (index >= 0) {
			return getErrorCountVariable().getInnerMatrix(index).doubleValue();
		}
		return -1;
	}

	public ClassificationDataSet() {
		super();
	}

	public ClassificationDataSet clone() {
		ClassificationDataSet ds = new ClassificationDataSet();
		for (Sample s : getSampleMap()) {
			ds.getSampleMap().add(s.clone());
		}
		return ds;
	}

	public int getClassCount() {
		return (int) getSampleMap().getElementAt(0).getVariableMap().getMatrix(Sample.TARGET)
				.getColumnCount();
	}

	public Variable getConfusionVariable() {
		return getVariableMap().get(CONFUSION);
	}

	public Variable getErrorCountVariable() {
		return getVariableMap().get(ERRORCOUNT);
	}

	public int getErrorCount() {
		return (int) getErrorCountVariable().getLatestMatrix().doubleValue();
	}

	public Variable getAccuracyVariable() {
		return getVariableMap().get(ACCURACY);
	}

	public void appendConfusionMatrix(Matrix m) {
		getConfusionVariable().addInnerMatrix(m);
	}

	public void appendAccuracyMatrix(Matrix m) {
		getAccuracyVariable().addInnerMatrix(m);
	}

	public void appendErrorCountMatrix(Matrix m) {
		getErrorCountVariable().addInnerMatrix(m);
	}

	public double getAccuracy() {
		return getAccuracyVariable().getLatestMatrix().doubleValue();
	}

	public double getMaxAccuracy() {
		return getAccuracyVariable().getAsListMatrix().getMaxValue();
	}

	public List<ClassificationDataSet> splitByClass() {
		List<ClassificationDataSet> returnDataSets = new ArrayList<ClassificationDataSet>();

		for (int i = 0; i < getClassCount(); i++) {
			ClassificationDataSet ds = DataSetFactory.classificationDataSet("Class " + i);
			for (Sample s : getSampleMap()) {
				if (s.getTargetClass() == i) {
					ds.getSampleMap().add(s.clone());
				}
			}
			returnDataSets.add(ds);
		}

		return returnDataSets;
	}

	public List<ClassificationDataSet> splitForStratifiedCV(int numberOfCVSets, int idOfCVSet,
			long randomSeed) {
		List<ClassificationDataSet> returnDataSets = new ArrayList<ClassificationDataSet>();
		Random rand = new Random(randomSeed);
		int classCount = getClassCount();

		// create empty lists
		List<List<Sample>> sortedSamples = new ArrayList<List<Sample>>();
		for (int i = 0; i < classCount; i++) {
			sortedSamples.add(new ArrayList<Sample>());
		}

		// add samples to lists according to class
		for (Sample s : getSampleMap()) {
			int targetClass = s.getTargetClass();
			sortedSamples.get(targetClass).add(s);
		}

		// shuffle lists
		Collections.shuffle(sortedSamples, rand);
		for (int i = 0; i < classCount; i++) {
			Collections.shuffle(sortedSamples.get(i), rand);
		}

		// create sample lists
		List<List<Sample>> cvSets = new ArrayList<List<Sample>>();
		for (int cvSet = 0; cvSet < numberOfCVSets; cvSet++) {
			List<Sample> samples = new ArrayList<Sample>();
			cvSets.add(samples);
		}

		// distribute the samples
		int fromPointer = 0;
		int toPointer = 0;
		while (!allEmpty(sortedSamples)) {

			for (toPointer = 0; toPointer < cvSets.size(); toPointer++) {

				List<Sample> to = cvSets.get(toPointer);

				while (to.size() < (double) getSampleMap().getSize() / numberOfCVSets
						&& fromPointer < sortedSamples.size()) {
					List<Sample> from = sortedSamples.get(fromPointer);

					if (!from.isEmpty()) {
						to.add(from.remove(0));
					}

					fromPointer++;

				}

				fromPointer = 0;

			}

		}

		// create the data sets
		ClassificationDataSet train = DataSetFactory.classificationDataSet("TrainingSet "
				+ idOfCVSet + "/" + numberOfCVSets + "(" + randomSeed + ")");
		ClassificationDataSet test = DataSetFactory.classificationDataSet("TestSet " + idOfCVSet
				+ "/" + numberOfCVSets + "(" + randomSeed + ")");

		test.getSampleMap().addAll(cvSets.remove(idOfCVSet));

		for (List<Sample> list : cvSets) {
			train.getSampleMap().addAll(list);
		}

		returnDataSets.add(train);
		returnDataSets.add(test);

		return returnDataSets;
	}

	private static boolean allEmpty(List<List<Sample>> lists) {
		for (List<Sample> list : lists) {
			if (!list.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public double getEarlyStoppingAccuracy(int numberOfSteps) {
		int index = getEarlyStoppingIndex(numberOfSteps);
		if (index >= 0) {
			return getAccuracyVariable().getInnerMatrix(index).doubleValue();
		}
		return -1;
	}

	public ClassificationDataSet bootstrap(int numberOfSamples) {
		ClassificationDataSet ds = DataSetFactory.classificationDataSet("Bootstrap of "
				+ getLabel());
		ObservableMap<Sample> sampleList = getSampleMap();
		for (int i = 0; i < numberOfSamples; i++) {
			int rand = MathUtil.nextInteger(0, sampleList.getSize() - 1);
			ds.getSampleMap().add(sampleList.getElementAt(rand).clone());
		}
		return ds;
	}

}
