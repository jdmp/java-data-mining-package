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

package org.jdmp.weka.wrappers;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

import weka.core.Instances;

public class DataSetToInstancesWrapper extends Instances {
	private static final long serialVersionUID = -7747912791607084086L;

	public static final Object INPUT = Classifier.INPUT;
	public static final Object WEIGHT = Classifier.WEIGHT;
	public static final Object TARGET = Algorithm.TARGET;

	public DataSetToInstancesWrapper(RegressionDataSet dataSet, boolean discrete,
			boolean includeTarget) throws MatrixException {
		super(dataSet.getLabel(), new DataSetToAttributeInfoWrapper(dataSet, discrete), dataSet
				.getSamples().getSize());

		if (includeTarget) {
			setClassIndex(dataSet.getFeatureCount());
		}

		for (Sample s : dataSet.getSamples()) {
			Matrix input = ((ClassificationSample) s).getMatrix(INPUT);
			Matrix weight = ((ClassificationSample) s).getMatrix(WEIGHT);
			Matrix target = ((ClassificationSample) s).getMatrix(TARGET);
			add(new SampleToInstanceWrapper(input, weight, target, discrete, includeTarget));
		}

	}

}
