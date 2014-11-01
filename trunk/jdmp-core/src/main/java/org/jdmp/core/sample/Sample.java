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

package org.jdmp.core.sample;

import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.CoreObject;
import org.ujmp.core.mapmatrix.MapMatrix;

public interface Sample extends CoreObject, MapMatrix<String, Matrix> {

	public static final String INPUT = Variable.INPUT;
	public static final String PROBABILITY = Variable.PROBABILITY;
	public static final String COUNT = Variable.COUNT;
	public static final String SCORE = Variable.SCORE;
	public static final String WEIGHT = Variable.WEIGHT;
	public static final String TARGET = Variable.TARGET;
	public static final String PREDICTED = Variable.PREDICTED;
	public static final String RMSE = Variable.RMSE;
	public static final String DIFFERENCE = Variable.DIFFERENCE;
	public static final String URL = Variable.URL;
	public static final String TAGS = Variable.TAGS;

	public static final SampleFactory Factory = new DefaultSampleFactory();

	public Sample clone();

	public int getRecognizedClass();

	public int getTargetClass();

	public boolean isCorrect();

	public void setObject(String id, Object object);

	public void setMatrix(String id, Matrix matrix);

	public Matrix getMatrix(String id);

	public Object getObject(String id);

}
