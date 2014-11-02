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
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.util.MathUtil;

public class DefaultDataSet extends AbstractDataSet {
	private static final long serialVersionUID = -2887879051530049677L;

	public DefaultDataSet() {
		super();
	}

	public Matrix getClassDistribution() {
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

	public AbstractDataSet clone() {
		AbstractDataSet ds = null;
		try {
			ds = this.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Sample s : this) {
			ds.add(s.clone());
		}
		return ds;
	}

	public final long getTargetFeatureCount() {
		Matrix m = getTargetMatrix();
		if (m != null) {
			return m.getColumnCount();
		} else {
			return 0;
		}
	}

	public void appendRMSEMatrix(Matrix m) {
		setMatrix(RMSE, m);
	}

	public Matrix getTargetMatrix() {
		return getMatrix(TARGET);
	}

	public Matrix getPredictedMatrix() {
		return getMatrix(PREDICTED);
	}

	public double getRMSE() {
		Matrix m = getRMSEMatrix();
		if (m == null) {
			return Double.NaN;
		} else {
			return m.getEuklideanValue();
		}
	}

	public Matrix getRMSEMatrix() {
		return getMatrix(RMSE);
	}

	public int getClassCount() {
		return (int) get(0).getMatrix(Sample.TARGET).toRowVector(Ret.NEW).getRowCount();
	}

	public int getErrorCount() {
		return (int) getMetaDataDouble(ERRORCOUNT);
	}

	public void appendConfusionMatrix(Matrix m) {
		setMatrix(CONFUSION, m);
	}

	public void appendAccuracyMatrix(Matrix m) {
		setMatrix(ACCURACY, m);
	}

	public void appendErrorCountMatrix(Matrix m) {
		setMatrix(ERRORCOUNT, m);
	}

	public double getAccuracy() {
		return getMetaDataDouble(ACCURACY);
	}

	public List<ListDataSet> splitByClass() {
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

	public List<ListDataSet> splitForStratifiedCV(int numberOfCVSets, int idOfCVSet, long randomSeed) {
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

	private static boolean allEmpty(List<List<Sample>> lists) {
		for (List<Sample> list : lists) {
			if (!list.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ListDataSet bootstrap(int numberOfSamples) {
		ListDataSet ds = DataSet.Factory.labeledDataSet("Bootstrap of " + getLabel());
		for (int i = 0; i < numberOfSamples; i++) {
			int rand = MathUtil.nextInteger(0, size());
			ds.add(get(rand));
		}
		return ds;
	}

	public boolean isDiscrete() {
		for (Sample s : this) {
			Matrix input = s.getMatrix(INPUT);
			for (long[] c : input.availableCoordinates()) {
				if (!MathUtil.isDiscrete(input.getAsDouble(c))) {
					return false;
				}
			}
		}
		return true;
	}

	public int getFeatureCount() {
		return (int) get(0).getMatrix(INPUT).getValueCount();
	}

	public ListDataSet bootstrap() {
		return bootstrap(size());
	}

}
