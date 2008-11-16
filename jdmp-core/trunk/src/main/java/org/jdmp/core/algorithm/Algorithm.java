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

package org.jdmp.core.algorithm;

import java.util.List;
import java.util.Map;

import org.jdmp.core.CoreObject;
import org.jdmp.core.variable.HasVariableMap;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;

public interface Algorithm extends CoreObject, HasVariableMap, HasAlgorithmMap {
	public static final Class<?>[] ALGORITHMARRAY = new Class<?>[] { new Algorithm[] {}.getClass() };

	public enum EdgeDirection {
		NotConnected, Incoming, Outgoing, Bidirectional
	};

	public void setVariable(Object key, Variable variable);

	public void setAlgorithm(Object key, Algorithm a);

	public Map<Object, Matrix> calculate() throws Exception;

	public Map<Object, Matrix> calculate(Matrix... input) throws Exception;

	public Map<Object, Matrix> calculate(double... input) throws Exception;

	public Map<Object, Matrix> calculate(List<Matrix> matrices) throws Exception;

	public Map<Object, Matrix> calculate(Map<Object, Matrix> matrices) throws Exception;

	public EdgeDirection getEdgeDirection(Object key);

	public String getEdgeLabel(Object key);

	public void setEdgeDirection(Object key, EdgeDirection direction);

	public void setEdgeLabel(Object key, String edgeLabel);

	public void addVariableKey(Object key);

	public void setVariables(Variable... variables);

	public List<Object> getVariableKeys();

	public List<Object> getInputKeys();

	public List<Object> getOutputKeys();

}
