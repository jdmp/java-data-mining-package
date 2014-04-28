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
import java.util.List;
import java.util.Random;

import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.DefaultObservableMap;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.DefaultVariableMap;
import org.jdmp.core.variable.VariableMap;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;

public abstract class AbstractDataSet extends DefaultMapMatrix<String, Sample> implements DataSet {
	private static final long serialVersionUID = -4168834188998259018L;

	private final VariableMap variableMap = new DefaultVariableMap();
	private final ObservableMap<Sample> sampleMap = new DefaultObservableMap<Sample>();

	public AbstractDataSet() {
		super();
		setId("DataSet" + getCoreObjectId());
	}

	public final ObservableMap<Sample> getSampleMap() {
		return sampleMap;
	}

	public final VariableMap getVariableMap() {
		return variableMap;
	}

	protected final void clearMap() {
		getSampleMap().clear();
		getVariableMap().clear();
	}

	public final List<DataSet> splitByCount(boolean shuffle, int... count) {
		List<DataSet> dataSets = new ArrayList<DataSet>();

		List<Integer> ids = new ArrayList<Integer>(getSampleMap().getSize());
		for (int i = getSampleMap().getSize() - 1; i != -1; i--) {
			ids.add(i);
		}

		if (shuffle) {
			Collections.shuffle(ids);
		}

		for (int i = 0; i < count.length; i++) {
			DataSet ds = DataSetFactory.classificationDataSet("DataSet" + i);
			for (int c = 0; c < count[i]; c++) {
				ds.getSampleMap().add(getSampleMap().getElementAt(ids.remove(0)).clone());
			}
			dataSets.add(ds);
		}
		DataSet ds = DataSetFactory.classificationDataSet("DataSet" + count.length);
		while (ids.size() != 0) {
			ds.getSampleMap().add(getSampleMap().getElementAt(ids.remove(0)).clone());
		}
		dataSets.add(ds);

		return dataSets;
	}

	public final List<DataSet> splitForCV(int numberOfCVSets, int idOfCVSet, long randomSeed) {
		List<DataSet> returnDataSets = new ArrayList<DataSet>();
		List<List<Sample>> tempSampleLists = new ArrayList<List<Sample>>();
		List<Sample> allSamples = new ArrayList<Sample>(getSampleMap().values());
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

		DataSet testSet = DataSetFactory.classificationDataSet("TestSet" + randomSeed + "-"
				+ idOfCVSet);
		testSet.getSampleMap().addAll(tempSampleLists.get(idOfCVSet));
		DataSet trainingSet = DataSetFactory.classificationDataSet("TrainingSet" + randomSeed + "-"
				+ idOfCVSet);
		for (int i = 0; i < numberOfCVSets; i++) {
			if (i != idOfCVSet) {
				trainingSet.getSampleMap().addAll(tempSampleLists.get(i));
			}
		}

		returnDataSets.add(trainingSet);
		returnDataSets.add(testSet);

		return returnDataSets;
	}

	public final List<DataSet> splitByPercent(boolean shuffle, double... percent) {
		int[] counts = new int[percent.length];
		int sampleCount = getSampleMap().getSize();
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

	public GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Constructor<?> con = null;
				Class<?> c = Class.forName("org.jdmp.gui.dataset.DataSetGUIObject");
				con = c.getConstructor(new Class<?>[] { DataSet.class });
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return guiObject;
	}

	public abstract DataSet clone();
}
