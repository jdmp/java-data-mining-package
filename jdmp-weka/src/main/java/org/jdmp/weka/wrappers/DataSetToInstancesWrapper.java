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

package org.jdmp.weka.wrappers;

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;

import weka.core.Instances;

public class DataSetToInstancesWrapper extends Instances {
	private static final long serialVersionUID = -7747912791607084086L;

	public static final String INPUT = Variable.INPUT;
	public static final String WEIGHT = Variable.WEIGHT;
	public static final String TARGET = Variable.TARGET;

	public DataSetToInstancesWrapper(RegressionDataSet dataSet, boolean discrete,
			boolean includeTarget) {
		super(dataSet.getLabel(), new DataSetToAttributeInfoWrapper(dataSet, discrete), dataSet
				.getSampleMap().getSize());

		if (includeTarget) {
			setClassIndex(dataSet.getFeatureCount());
		}

		for (Sample s : dataSet.getSampleMap()) {
			Matrix input = s.getMatrix(INPUT);
			Matrix weight = s.getMatrix(WEIGHT);
			Matrix target = s.getMatrix(TARGET);
			add(new SampleToInstanceWrapper(input, weight, target, discrete, includeTarget));
		}

	}

}
