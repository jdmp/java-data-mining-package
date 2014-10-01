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

import java.util.List;

import org.jdmp.core.sample.HasSampleMap;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.HasVariableMap;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.CoreObject;
import org.ujmp.core.mapmatrix.MapMatrix;

public interface DataSet extends CoreObject, HasVariableMap, HasSampleMap,
		MapMatrix<String, Sample> {

	public static final String INPUT = "Input";

	public static final String RMSE = "RMSE";

	public static final String PREDICTED = "Predicted";

	public static final String TARGET = "Target";

	public static final String ACCURACY = Variable.ACCURACY;

	public static final String CONFUSION = Variable.CONFUSION;

	public static final String ERRORCOUNT = Variable.ERRORCOUNT;

	public List<DataSet> splitByCount(boolean shuffle, int... count);

	public List<DataSet> splitForCV(int numberOfCVSets, int idOfCVSet, long randomSeed);

	public List<DataSet> splitByPercent(boolean shuffle, double... percent);

	public Matrix getInputMatrix();

	public Matrix getPredictedMatrix();

	public Variable getInputVariable();

	public Variable getTargetVariable();

	public DataSet clone();

	public Matrix getTargetMatrix();

	public double getAccuracy();

	public int getEarlyStoppingIndex(int numberOfSteps);

	public boolean isEarlyStoppingReached(int numberOfSteps);

	public List<DataSet> splitForStratifiedCV(int i, int r, long seed);

	public void standardize(int dimension);

	public boolean isDiscrete();

	public int getFeatureCount();

	public int getClassCount();

}
