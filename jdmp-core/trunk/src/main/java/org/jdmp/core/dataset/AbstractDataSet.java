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

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.dataset.wrappers.SampleListToMatrixWrapper;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.DefaultObservableList;
import org.jdmp.core.util.DefaultObservableMap;
import org.jdmp.core.util.ObservableList;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.util.StringUtil;

public abstract class AbstractDataSet extends AbstractCoreObject implements DataSet {
	private static final long serialVersionUID = -4168834188998259018L;

	private transient GUIObject guiObject = null;

	private ObservableList<Sample> sampleList = new DefaultObservableList<Sample>();

	private final ObservableMap<Variable> variableList = new DefaultObservableMap<Variable>();

	private final ObservableList<DataSet> dataSetList = new DefaultObservableList<DataSet>();

	public final String getDescription() {
		return getString(DataSet.DESCRIPTION);
	}

	public final void setDescription(String description) {
		setObject(DataSet.DESCRIPTION, description);
	}

	public final String getLabel() {
		return getString(DataSet.LABEL);
	}

	public final void setLabel(String label) {
		setObject(DataSet.LABEL, label);
	}

	public AbstractDataSet() {
		super();
		Matrix m = new SampleListToMatrixWrapper(getSamples());
		Variable v = VariableFactory.labeledVariable("AllSamples");
		v.addMatrix(m);
		getVariables().put("AllSamples", v);
	}

	public AbstractDataSet(String label) {
		this();
		setLabel(label);
	}

	public final String getString(Object variableKey) {
		return StringUtil.convert(getMatrix(variableKey));
	}

	public Matrix getMatrix(Object variableKey) {
		Variable v = getVariables().get(variableKey);
		if (v != null) {
			return v.getMatrix();
		} else {
			return null;
		}
	}

	public final String getAsString(Object variableKey) {
		return StringUtil.convert(getMatrix(variableKey));
	}

	public final void setObject(Object variableKey, Object value) {
		if (value == null) {
			setMatrix(variableKey, MatrixFactory.emptyMatrix());
		} else if (value instanceof Matrix) {
			setMatrix(variableKey, (Matrix) value);
		} else {
			setMatrix(variableKey, MatrixFactory.linkToValue(value));
		}
	}

	public void setMatrix(Object variableKey, Matrix matrix) {
		Variable v = getVariables().get(variableKey);
		if (v == null) {
			v = VariableFactory.labeledVariable(variableKey.toString());
			getVariables().put(variableKey, v);
		}
		v.addMatrix(matrix);
	}

	public ObservableList<Sample> getSamples() {
		return sampleList;
	}

	public final ObservableMap<Variable> getVariables() {
		return variableList;
	}

	public final ObservableList<DataSet> getDataSets() {
		return dataSetList;
	}

	public void clear() {
		sampleList.clear();
	}

	public List<DataSet> splitByCount(boolean shuffle, int... count) {
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

	public List<DataSet> splitForCV(int numberOfCVSets, int idOfCVSet, long randomSeed) {
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

	public List<DataSet> splitByPercent(boolean shuffle, double... percent) {
		int[] counts = new int[percent.length];
		int sampleCount = getSamples().getSize();
		for (int i = 0; i < percent.length; i++) {
			counts[i] = (int) Math.round(percent[i] * sampleCount);
		}
		return splitByCount(shuffle, counts);
	}

	public final GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Class<?> c = Class.forName("org.jdmp.gui.dataset.DataSetGUIObject");
				Constructor<?> con = c.getConstructor(new Class<?>[] { DataSet.class });
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				logger.log(Level.WARNING, "cannot create dataset gui object", e);
			}
		}
		return guiObject;
	}

	@Override
	public final String toString() {
		if (getLabel() == null) {
			return getClass().getSimpleName();
		} else {
			return getClass().getSimpleName() + " [" + getLabel() + "]";
		}
	}

	@Override
	public void setSamples(ObservableList<Sample> samples) {
		this.sampleList = samples;
	}

	public void notifyGUIObject() {
		if (guiObject != null) {
			guiObject.fireValueChanged();
		}
	}

	@Override
	public abstract AbstractDataSet clone();
}
