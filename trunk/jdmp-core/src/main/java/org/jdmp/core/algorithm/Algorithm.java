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

package org.jdmp.core.algorithm;

import java.util.List;
import java.util.Map;

import org.jdmp.core.dataset.HasDataSetMap;
import org.jdmp.core.variable.HasVariableMap;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.CoreObject;

public interface Algorithm extends CoreObject, HasVariableMap, HasDataSetMap, HasAlgorithmMap {
	public static final Class<?>[] ALGORITHMARRAY = new Class<?>[] { new Algorithm[] {}.getClass() };

	public enum EdgeDirection {
		NotConnected, Incoming, Outgoing, Bidirectional
	};

	public static final String SOURCE = Variable.SOURCE;
	public static final String DIMENSION = Variable.DIMENSION;
	public static final String IGNORENAN = Variable.IGNORENAN;
	public static final String TARGET = Variable.TARGET;
	public static final String INPUT = Variable.INPUT;
	public static final String RMSE = Variable.RMSE;
	public static final String WEIGHT = Variable.WEIGHT;
	public static final String DIFFERENCE = Variable.DIFFERENCE;
	public static final String PREDICTED = Variable.PREDICTED;
	public static final String COMPRESSED = Variable.COMPRESSED;
	public static final String DECOMPRESSED = Variable.DECOMPRESSED;
	public static final String HASH = Variable.HASH;

	public void setVariable(String key, Variable variable);

	public void setAlgorithm(String key, Algorithm a);

	public Map<String, Object> calculate();

	public Map<String, Object> calculate(Matrix... input);

	public Map<String, Object> calculateObjects(Object... input);

	public Map<String, Object> calculate(double... input);

	public Map<String, Object> calculate(List<Matrix> matrices);

	public Map<String, Object> calculateObjects(List<Object> matrices);

	public Map<String, Object> calculateObjects(Map<String, Object> objects);

	public EdgeDirection getEdgeDirection(String key);

	public String getEdgeLabel(String key);

	public void setEdgeDirection(String key, EdgeDirection direction);

	public void setEdgeLabel(String key, String edgeLabel);

	public void addVariableKey(String key);

	public List<String> getVariableKeys();

	public List<String> getInputKeys();

	public List<String> getOutputKeys();

}
