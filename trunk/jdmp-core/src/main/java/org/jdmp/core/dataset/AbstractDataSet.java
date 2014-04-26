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
import java.util.List;
import java.util.Random;

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.DefaultObservableList;
import org.jdmp.core.util.DefaultObservableMap;
import org.jdmp.core.util.ObservableList;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.DefaultVariables;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.Variables;
import org.ujmp.core.Matrix;

public abstract class AbstractDataSet extends AbstractCoreObject implements DataSet {
	private static final long serialVersionUID = -4168834188998259018L;

	private ObservableMap<Sample> sampleMap = null;

	private Variables variables = null;

	private ObservableList<DataSet> dataSetList = null;

	public final ObservableMap<Sample> getSamples() {
		if (sampleMap == null) {
			sampleMap = new DefaultObservableMap<Sample>();
		}
		return sampleMap;
	}

	public final void setLabelObject(Object label) {
		getVariables().setObject(Sample.LABEL, label);
	}

	public final Object getLabelObject() {
		return getVariables().getAsObject(Sample.LABEL);
	}

	public final Variables getVariables() {
		if (variables == null) {
			variables = new DefaultVariables();
		}
		return variables;
	}

	public final ObservableList<DataSet> getDataSets() {
		if (dataSetList == null) {
			dataSetList = new DefaultObservableList<DataSet>();
		}
		return dataSetList;
	}

	public final String getDescription() {
		return getVariables().getAsString(DataSet.DESCRIPTION);
	}

	public final void setDescription(String description) {
		getVariables().setObject(DataSet.DESCRIPTION, description);
	}

	public String getLabel() {
		return getVariables().getAsString(DataSet.LABEL);
	}

	public final void setLabel(String label) {
		getVariables().setObject(DataSet.LABEL, label);
	}

	public final String getId() {
		String id = getVariables().getAsString(DataSet.ID);
		if (id == null) {
			id = "DataSet" + getCoreObjectId();
			setId(id);
		}
		return id;
	}

	public final void setId(String id) {
		getVariables().setObject(DataSet.ID, id);
	}

	public AbstractDataSet() {
		super();
	}

	public final Matrix getMatrix(String variableKey) {
		Variable v = getVariables().get(variableKey);
		if (v != null) {
			return v.getLatestMatrix();
		} else {
			return null;
		}
	}

	public final void clear() {
		getSamples().clear();
		getVariables().clear();
		getDataSets().clear();
	}

	public final List<DataSet> splitByCount(boolean shuffle, int... count) {
		List<DataSet> dataSets = new ArrayList<DataSet>();

		List<Integer> ids = new ArrayList<Integer>(getSamples().getSize());
		for (int i = getSamples().getSize() - 1; i != -1; i--) {
			ids.add(i);
		}

		if (shuffle) {
			Collections.shuffle(ids);
		}

		for (int i = 0; i < count.length; i++) {
			DataSet ds = DataSetFactory.classificationDataSet("DataSet" + i);
			for (int c = 0; c < count[i]; c++) {
				ds.getSamples().add(getSamples().getElementAt(ids.remove(0)).clone());
			}
			dataSets.add(ds);
		}
		DataSet ds = DataSetFactory.classificationDataSet("DataSet" + count.length);
		while (ids.size() != 0) {
			ds.getSamples().add(getSamples().getElementAt(ids.remove(0)).clone());
		}
		dataSets.add(ds);

		return dataSets;
	}

	public final List<DataSet> splitForCV(int numberOfCVSets, int idOfCVSet, long randomSeed) {
		List<DataSet> returnDataSets = new ArrayList<DataSet>();
		List<List<Sample>> tempSampleLists = new ArrayList<List<Sample>>();
		List<Sample> allSamples = new ArrayList<Sample>(getSamples().toCollection());
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
		testSet.getSamples().addAll(tempSampleLists.get(idOfCVSet));
		DataSet trainingSet = DataSetFactory.classificationDataSet("TrainingSet" + randomSeed + "-"
				+ idOfCVSet);
		for (int i = 0; i < numberOfCVSets; i++) {
			if (i != idOfCVSet) {
				trainingSet.getSamples().addAll(tempSampleLists.get(i));
			}
		}

		returnDataSets.add(trainingSet);
		returnDataSets.add(testSet);

		return returnDataSets;
	}

	public final List<DataSet> splitByPercent(boolean shuffle, double... percent) {
		int[] counts = new int[percent.length];
		int sampleCount = getSamples().getSize();
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

	public final void setSamples(ObservableMap<Sample> samples) {
		this.sampleMap = samples;
	}

	public final void setVariables(Variables variables) {
		this.variables = variables;
	}

	public final void setDataSets(ObservableList<DataSet> dataSets) {
		this.dataSetList = dataSets;
	}

	public abstract DataSet clone();
}
