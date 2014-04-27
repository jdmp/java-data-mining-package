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
import org.ujmp.core.interfaces.CoreObject;
import org.ujmp.core.mapmatrix.MapMatrix;

public interface DataSet extends CoreObject, HasVariableMap, HasSampleMap,
		MapMatrix<String, Sample> {

	public List<DataSet> splitByCount(boolean shuffle, int... count);

	public List<DataSet> splitForCV(int numberOfCVSets, int idOfCVSet, long randomSeed);

	public List<DataSet> splitByPercent(boolean shuffle, double... percent);

	public DataSet clone();
}
