/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.DefaultObservableList;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class ClassificationDataSet extends RegressionDataSet {
	private static final long serialVersionUID = 3969274321783319184L;

	public static final String ACCURACY = "Accuracy";

	public static final String CONFUSION = "Confusion";

	public static final String ERRORCOUNT = "ErrorCount";

	public Matrix getClassDistribution() {
		Matrix m = MatrixFactory.zeros(getClassCount(), 1);

		Map<Integer, Double> map = new HashMap<Integer, Double>();

		for (Sample s : getSamples()) {
			int c = ((ClassificationSample) s).getTargetClass();
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
			m.setAsDouble(d / getSamples().getSize(), i, 0);
		}
		return m;
	}

	public double getEarlyStoppingErrorCount(int numberOfSteps) {
		int index = getEarlyStoppingIndex(numberOfSteps);
		if (index >= 0) {
			return getErrorCountVariable().getMatrix(index).doubleValue();
		}
		return -1;
	}

	public ClassificationDataSet() {
		super();
		getVariables().put(ACCURACY, VariableFactory.labeledVariable("Accuracy", 10000));
		getVariables().put(CONFUSION, VariableFactory.labeledVariable("Confusion", 1000));
		getVariables().put(ERRORCOUNT, VariableFactory.labeledVariable("Error Count", 10000));
	}

	@Override
	public ClassificationDataSet clone() {
		ClassificationDataSet ds = new ClassificationDataSet();
		for (Sample s : getSamples()) {
			ds.getSamples().add(s.clone());
		}
		return ds;
	}

	public int getClassCount() {
		return (int) getSamples().getElementAt(0).getMatrix(Sample.TARGET).getColumnCount();
	}

	public Variable getConfusionVariable() {
		return getVariables().get(CONFUSION);
	}

	public Variable getErrorCountVariable() {
		return getVariables().get(ERRORCOUNT);
	}

	public int getErrorCount() throws MatrixException {
		return (int) getErrorCountVariable().getMatrix().doubleValue();
	}

	public Variable getAccuracyVariable() {
		return getVariables().get(ACCURACY);
	}

	public void appendConfusionMatrix(Matrix m) {
		getConfusionVariable().addMatrix(m);
	}

	public void appendAccuracyMatrix(Matrix m) {
		getAccuracyVariable().addMatrix(m);
	}

	public void appendErrorCountMatrix(Matrix m) {
		getErrorCountVariable().addMatrix(m);
	}

	public double getAccuracy() throws MatrixException {
		return getAccuracyVariable().getMatrix().doubleValue();
	}

	public double getMaxAccuracy() throws MatrixException {
		return getAccuracyVariable().getMaxValue();
	}

	public List<ClassificationDataSet> splitByClass() {
		List<ClassificationDataSet> returnDataSets = new ArrayList<ClassificationDataSet>();

		for (int i = 0; i < getClassCount(); i++) {
			ClassificationDataSet ds = DataSetFactory.classificationDataSet("Class " + i);
			for (Sample s : getSamples()) {
				if (((ClassificationSample) s).getTargetClass() == i) {
					ds.getSamples().add(s.clone());
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
		for (Sample s : getSamples()) {
			int targetClass = ((ClassificationSample) s).getTargetClass();
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

				while (to.size() < (double) getSamples().getSize() / numberOfCVSets
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

		test.getSamples().addAll(cvSets.remove(idOfCVSet));

		for (List<Sample> list : cvSets) {
			train.getSamples().addAll(list);
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
			return getAccuracyVariable().getMatrix(index).doubleValue();
		}
		return -1;
	}

	public ClassificationDataSet bootstrap(int numberOfSamples) {
		ClassificationDataSet ds = DataSetFactory.classificationDataSet("Bootstrap of "
				+ getLabel());
		DefaultObservableList<Sample> sampleList = getSamples();
		for (int i = 0; i < numberOfSamples; i++) {
			int rand = MathUtil.nextInteger(0, sampleList.getSize() - 1);
			ds.getSamples().add(sampleList.getElementAt(rand).clone());
		}
		return ds;
	}

}
