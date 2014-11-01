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
import org.jdmp.core.variable.DefaultVariableMap;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableMap;
import org.ujmp.core.Matrix;
import org.ujmp.core.collections.list.FastArrayList;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractDataSet extends DefaultListMatrix<Sample> implements ListDataSet {
	private static final long serialVersionUID = -4168834188998259018L;

	private final VariableMap variableMap = new DefaultVariableMap();

	public AbstractDataSet() {
		super();
		setId("DataSet" + getCoreObjectId());
	}

	public final VariableMap getVariableMap() {
		return variableMap;
	}

	public Matrix getInputMatrix() {
		return getInputVariable().getLast();
	}

	public final Variable getInputVariable() {
		return getVariableMap().get(INPUT);
	}

	protected final void clearMap() {
		clear();
		getVariableMap().clear();
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

	public GUIObject getGUIObject() {
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
}
