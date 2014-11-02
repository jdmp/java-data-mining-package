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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdmp.core.sample.MatrixSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableMap;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.listmatrix.AbstractListMatrix;
import org.ujmp.core.util.MathUtil;

public class MatrixDataSet extends AbstractListMatrix<Sample> implements ListDataSet {
	private static final long serialVersionUID = -2697648740183157641L;

	public static final int MAXSAMPLES = 10000;
	private final Map<Integer, Sample> samples = new HashMap<Integer, Sample>();

	public MatrixDataSet(Matrix input) {
		setMetaData(INPUT, input);
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

	public Matrix getPredictedMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

	public Variable getTargetVariable() {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix getTargetMatrix() {
		return getMatrix(TARGET);
	}

	public double getAccuracy() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getEarlyStoppingIndex(int numberOfSteps) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isEarlyStoppingReached(int numberOfSteps) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<ListDataSet> splitForStratifiedCV(int i, int r, long seed) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDiscrete() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getFeatureCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getClassCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public ListDataSet bootstrap() {
		// TODO Auto-generated method stub
		return null;
	}

	public ListDataSet bootstrap(int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListDataSet clone() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ListDataSet> splitByCount(boolean shuffle, int... count) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ListDataSet> splitForCV(int numberOfCVSets, int idOfCVSet, long randomSeed) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ListDataSet> splitByPercent(boolean shuffle, double... percent) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix getInputMatrix() {
		return getMetaDataMatrix(INPUT);
	}

	public Variable getInputVariable() {
		// TODO Auto-generated method stub
		return null;
	}

	public VariableMap getVariableMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized Sample get(int index) {
		Sample sample = samples.get(index);
		if (sample == null) {
			sample = new MatrixSample(this, index);
			samples.put(index, sample);
			while (samples.size() > MAXSAMPLES) {
				samples.remove(samples.keySet().iterator().next());
			}
		}
		return sample;
	}

	@Override
	public boolean addToList(Sample t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addToList(int index, Sample element) {
		// TODO Auto-generated method stub

	}

	@Override
	public Sample removeFromList(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeFromList(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Sample setToList(int index, Sample element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearList() {
		// TODO Auto-generated method stub

	}

	@Override
	public int size() {
		return MathUtil.longToInt(getMetaDataMatrix(INPUT).getRowCount());
	}

	public Matrix getMatrix(Object key) {
		return getMetaDataMatrix(key);
	}

	public Set<String> keySet() {
		return getMetaData().keySet();
	}

	public void setMatrix(String key, Matrix matrix) {
		setMetaData(key, matrix);
	}

	public double getAsDouble(String key) {
		return getMetaDataDouble(key);
	}

	public final String toString() {
		if (getLabel() == null) {
			return getClass().getSimpleName();
		} else {
			return getClass().getSimpleName() + " [" + getLabel() + "]";
		}
	}

}
