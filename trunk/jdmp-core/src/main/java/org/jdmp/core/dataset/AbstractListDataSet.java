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

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.collections.list.FastArrayList;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.listmatrix.AbstractListMatrix;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractListDataSet extends AbstractListMatrix<Sample> implements ListDataSet {
	private static final long serialVersionUID = -4168834188998259018L;

	public AbstractListDataSet() {
		super();
		setId("DataSet" + getCoreObjectId());
	}

	public final Matrix getInputMatrix() {
		return getMatrix(INPUT);
	}

	public final List<ListDataSet> splitByCount(boolean shuffle, int... count) {
		List<ListDataSet> dataSets = new ArrayList<ListDataSet>();
		List<Sample> all = new FastArrayList<Sample>();
		all.addAll(this);

		for (int i = 0; i < count.length; i++) {
			ListDataSet ds = DataSet.Factory.labeledDataSet("DataSet" + i);
			for (int c = 0; c < count[i]; c++) {
				if (shuffle) {
					ds.add(all.remove(MathUtil.nextInteger(all.size())));
				} else {
					ds.add(all.remove(0));
				}
			}
			dataSets.add(ds);
		}
		ListDataSet ds = DataSet.Factory.labeledDataSet("DataSet" + count.length);
		ds.addAll(all);
		dataSets.add(ds);

		return dataSets;
	}

	public final List<ListDataSet> splitForCV(int numberOfCVSets, int idOfCVSet, long randomSeed) {
		List<ListDataSet> returnDataSets = new ArrayList<ListDataSet>();
		List<List<Sample>> tempSampleLists = new ArrayList<List<Sample>>();
		List<Sample> allSamples = new ArrayList<Sample>(this);
		Collections.shuffle(allSamples, new Random(randomSeed));

		for (int set = 0; set < numberOfCVSets; set++) {
			List<Sample> partSamples = new ArrayList<Sample>();
			tempSampleLists.add(partSamples);
		}

		while (!allSamples.isEmpty()) {
			for (int set = 0; set < numberOfCVSets; set++) {
				if (!allSamples.isEmpty()) {
					tempSampleLists.get(set).add(allSamples.remove(0));
				}
			}
		}

		ListDataSet testSet = DataSet.Factory.labeledDataSet("TestSet" + randomSeed + "-"
				+ idOfCVSet);
		testSet.addAll(tempSampleLists.get(idOfCVSet));
		ListDataSet trainingSet = DataSet.Factory.labeledDataSet("TrainingSet" + randomSeed + "-"
				+ idOfCVSet);
		for (int i = 0; i < numberOfCVSets; i++) {
			if (i != idOfCVSet) {
				trainingSet.addAll(tempSampleLists.get(i));
			}
		}

		returnDataSets.add(trainingSet);
		returnDataSets.add(testSet);

		return returnDataSets;
	}

	public final List<ListDataSet> splitByPercent(boolean shuffle, double... percent) {
		int[] counts = new int[percent.length];
		int sampleCount = size();
		for (int i = 0; i < percent.length; i++) {
			counts[i] = (int) Math.round(percent[i] * sampleCount);
		}
		return splitByCount(shuffle, counts);
	}

	public final String toString() {
		if (getLabel() == null) {
			return getClass().getSimpleName();
		} else {
			return getClass().getSimpleName() + " [" + getLabel() + "]";
		}
	}

	public final GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Constructor<?> con = null;
				Class<?> c = Class.forName("org.jdmp.gui.dataset.DataSetGUIObject");
				con = c.getConstructor(new Class<?>[] { ListDataSet.class });
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return guiObject;
	}

	public abstract ListDataSet clone();

	public final Matrix getMatrix(Object key) {
		return getMetaDataMatrix(key);
	}

	public final Set<String> keySet() {
		return getMetaData().keySet();
	}

	public final void setMatrix(String key, Matrix matrix) {
		setMetaData(key, matrix);
	}

	public final double getAsDouble(String key) {
		return getMetaDataDouble(key);
	}

	public final ListDataSet bootstrap(int numberOfSamples) {
		ListDataSet ds = DataSet.Factory.labeledDataSet("Bootstrap of " + getLabel());
		for (int i = 0; i < numberOfSamples; i++) {
			int rand = MathUtil.nextInteger(0, size());
			ds.add(get(rand));
		}
		return ds;
	}

	public final boolean isDiscrete() {
		for (Sample s : this) {
			Matrix input = s.getAsMatrix(INPUT);
			for (long[] c : input.availableCoordinates()) {
				if (!MathUtil.isDiscrete(input.getAsDouble(c))) {
					return false;
				}
			}
		}
		return true;
	}

	public final int getFeatureCount() {
		return (int) get(0).getAsMatrix(INPUT).toColumnVector(Ret.LINK).getColumnCount();
	}

	public final ListDataSet bootstrap() {
		return bootstrap(size());
	}

	public final Matrix getClassDistribution() {
		Matrix m = Matrix.Factory.zeros(getClassCount(), 1);

		Map<Integer, Double> map = new HashMap<Integer, Double>();

		for (Sample s : this) {
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
			m.setAsDouble(d / size(), i, 0);
		}
		return m;
	}

	public final void appendRMSEMatrix(Matrix m) {
		setMatrix(RMSE, m);
	}

	public final Matrix getTargetMatrix() {
		return getMatrix(TARGET);
	}

	public final Matrix getPredictedMatrix() {
		return getMatrix(PREDICTED);
	}

	public final double getRMSE() {
		Matrix m = getRMSEMatrix();
		if (m == null) {
			return Double.NaN;
		} else {
			return m.getEuklideanValue();
		}
	}

	public final Matrix getRMSEMatrix() {
		return getMatrix(RMSE);
	}

	public final int getClassCount() {
		return (int) get(0).getAsMatrix(TARGET).toColumnVector(Ret.LINK).getColumnCount();
	}

	public final int getErrorCount() {
		return (int) getMetaDataDouble(ERRORCOUNT);
	}

	public final double getAccuracy() {
		return getMetaDataDouble(ACCURACY);
	}

	public final List<ListDataSet> splitByClass() {
		List<ListDataSet> returnDataSets = new ArrayList<ListDataSet>();

		for (int i = 0; i < getClassCount(); i++) {
			ListDataSet ds = DataSet.Factory.labeledDataSet("Class " + i);
			for (Sample s : this) {
				if (s.getTargetClass() == i) {
					ds.add(s.clone());
				}
			}
			returnDataSets.add(ds);
		}

		return returnDataSets;
	}

	public final List<ListDataSet> splitForStratifiedCV(int numberOfCVSets, int idOfCVSet,
			long randomSeed) {
		List<ListDataSet> returnDataSets = new ArrayList<ListDataSet>();
		Random rand = new Random(randomSeed);
		int classCount = getClassCount();

		// create empty lists
		List<List<Sample>> sortedSamples = new ArrayList<List<Sample>>();
		for (int i = 0; i < classCount; i++) {
			sortedSamples.add(new ArrayList<Sample>());
		}

		// add samples to lists according to class
		for (Sample s : this) {
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

				while (to.size() < (double) size() / numberOfCVSets
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
		ListDataSet train = DataSet.Factory.labeledDataSet("TrainingSet " + idOfCVSet + "/"
				+ numberOfCVSets + "(" + randomSeed + ")");
		ListDataSet test = DataSet.Factory.labeledDataSet("TestSet " + idOfCVSet + "/"
				+ numberOfCVSets + "(" + randomSeed + ")");

		test.addAll(cvSets.remove(idOfCVSet));

		for (List<Sample> list : cvSets) {
			train.addAll(list);
		}

		returnDataSets.add(train);
		returnDataSets.add(test);

		return returnDataSets;
	}

	private static final boolean allEmpty(List<List<Sample>> lists) {
		for (List<Sample> list : lists) {
			if (!list.isEmpty()) {
				return false;
			}
		}
		return true;
	}

}
