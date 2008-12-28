/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

import org.jdmp.core.CoreObject;
import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.regression.Regressor;
import org.jdmp.core.variable.HasVariableMap;

public interface Sample extends CoreObject, HasVariableMap {

	public static final Object INPUT = Regressor.INPUT;

	public static final Object WEIGHT = Regressor.WEIGHT;

	public static final Object TARGET = Algorithm.TARGET;

	public static final Object PREDICTED = Regressor.PREDICTED;

	public static final Object RMSE = Regressor.RMSE;

	public static final Object DIFFERENCE = Regressor.DIFFERENCE;

	public Sample clone();

}
