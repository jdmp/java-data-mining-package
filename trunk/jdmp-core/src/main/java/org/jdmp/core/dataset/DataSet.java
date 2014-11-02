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
import java.util.Set;

import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.CoreObject;

public interface DataSet extends CoreObject {

	public static final String INPUT = "Input";

	public static final String RMSE = "RMSE";

	public static final String PREDICTED = "Predicted";

	public static final String TARGET = "Target";

	public static final String ACCURACY = Variable.ACCURACY;

	public static final String CONFUSION = Variable.CONFUSION;

	public static final String ERRORCOUNT = Variable.ERRORCOUNT;

	public static final DataSetFactory Factory = new DefaultDataSetFactory();

	public List<ListDataSet> splitByCount(boolean shuffle, int... count);

	public List<ListDataSet> splitForCV(int numberOfCVSets, int idOfCVSet, long randomSeed);

	public List<ListDataSet> splitByPercent(boolean shuffle, double... percent);

	public Matrix getInputMatrix();

	public Matrix getPredictedMatrix();

	public ListDataSet clone();

	public Matrix getTargetMatrix();

	public double getAccuracy();

	public List<ListDataSet> splitForStratifiedCV(int i, int r, long seed);

	public boolean isDiscrete();

	public int getFeatureCount();

	public int getClassCount();

	public ListDataSet bootstrap();

	public ListDataSet bootstrap(int count);

	public Matrix getMatrix(Object key);

	public Set<String> keySet();

	public int size();

	public boolean isEmpty();

	public void setMatrix(String key, Matrix matrix);
	
	public double getAsDouble(String key);

}
