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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.DefaultObservableMap;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.DefaultVariableMap;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableMap;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractAlgorithm extends AbstractCoreObject implements Algorithm {
	private static final long serialVersionUID = 3219035032582720106L;

	private final VariableMap variableMap = new DefaultVariableMap();
	private final ObservableMap<Algorithm> algorithmMap = new DefaultObservableMap<Algorithm>();
	private final ObservableMap<DataSet> dataSetMap = new DefaultObservableMap<DataSet>();

	private final Map<String, String> edgeLabels = new HashMap<String, String>(2);
	private final Map<String, EdgeDirection> edgeDirections = new HashMap<String, EdgeDirection>(2);
	private final List<String> variableKeys = new ArrayList<String>(2);

	public final void setEdgeLabel(String key, String edgeLabel) {
		edgeLabels.put(key, edgeLabel);
	}

	public final void addVariableKey(String key) {
		variableKeys.add(key);
	}

	public final void setVariables(Variable... variables) {
		for (int i = 0; i < variables.length && i < getVariableKeys().size(); i++) {
			this.variableMap.put(variableKeys.get(i), variables[i]);
		}
	}

	public final List<String> getVariableKeys() {
		return variableKeys;
	}

	public final Object getLabelObject() {
		return getVariableMap().getAsObject(Sample.LABEL);
	}

	public final String getLabel() {
		return getVariableMap().getAsString(Algorithm.LABEL);
	}

	public final void setLabel(Object label) {
		getVariableMap().setObject(Algorithm.LABEL, label);
	}

	public final String getId() {
		String id = getVariableMap().getAsString(Algorithm.ID);
		if (id == null) {
			id = "Algorithm" + getCoreObjectId();
			setId(id);
		}
		return id;
	}

	public final void setId(String id) {
		getVariableMap().setObject(Algorithm.ID, id);
	}

	public final String getDescription() {
		return getVariableMap().getAsString(Algorithm.DESCRIPTION);
	}

	public final void setDescription(String description) {
		getVariableMap().setObject(Algorithm.DESCRIPTION, description);
	}

	public AbstractAlgorithm() {
		super();
		setLabel(getClass().getSimpleName());
	}

	public final void setEdgeDirection(String key, EdgeDirection direction) {
		edgeDirections.put(key, direction);
	}

	public void setVariable(String index, Variable variable) {
		variableMap.put(index, variable);
	}

	public final void setAlgorithm(String index, Algorithm a) {
		algorithmMap.put(index, a);
	}

	public final void clear() {
		if (algorithmMap != null) {
			algorithmMap.clear();
		}
		if (variableMap != null) {
			variableMap.clear();
		}
		if (dataSetMap != null) {
			dataSetMap.clear();
		}
	}

	public final Map<String, Object> calculate() throws Exception {
		Map<String, Object> input = new HashMap<String, Object>();

		for (String v : getInputKeys()) {
			input.put(v, getVariableMap().getMatrix(v));
		}

		Map<String, Object> output = calculateObjects(input);

		for (String v : getOutputKeys()) {
			getVariableMap().setMatrix(v, MathUtil.getMatrix(output.get(v)));
		}

		return output;
	}

	public final List<String> getInputKeys() {
		List<String> inputKeys = new ArrayList<String>();
		for (String v : getVariableKeys()) {
			if (EdgeDirection.Incoming.equals(getEdgeDirection(v))
					|| EdgeDirection.Bidirectional.equals(getEdgeDirection(v))) {
				inputKeys.add(v);
			}
		}
		return inputKeys;
	}

	public final List<String> getOutputKeys() {
		List<String> outputKeys = new ArrayList<String>();
		for (String v : getVariableKeys()) {
			if (EdgeDirection.Outgoing.equals(getEdgeDirection(v))
					|| EdgeDirection.Bidirectional.equals(getEdgeDirection(v))) {
				outputKeys.add(v);
			}
		}
		return outputKeys;
	}

	public final Map<String, Object> calculate(Matrix... input) throws Exception {
		return calculate(Arrays.asList(input));
	}

	public final Map<String, Object> calculateObjects(Object... input) throws Exception {
		return calculateObjects(Arrays.asList(input));
	}

	public final Map<String, Object> calculate(double... input) throws Exception {
		List<Matrix> inputA = new LinkedList<Matrix>();
		for (int i = 0; i < input.length; i++) {
			inputA.add(Matrix.Factory.linkToValue(input[i]));
		}
		return calculate(inputA);
	}

	public final Map<String, Object> calculate(List<Matrix> matrices) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> keys = getInputKeys();
		for (int i = 0; i < matrices.size(); i++) {
			String key = keys.get(i);
			map.put(key, matrices.get(i));
		}
		return calculateObjects(map);
	}

	public final Map<String, Object> calculateObjects(List<Object> matrices) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> keys = getInputKeys();
		for (int i = 0; i < matrices.size(); i++) {
			String key = keys.get(i);
			map.put(key, matrices.get(i));
		}
		return calculateObjects(map);
	}

	// this is not the best way
	public Map<String, Object> calculateObjects(Map<String, Object> objects) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		return result;
	}

	public final EdgeDirection getEdgeDirection(String key) {
		return edgeDirections.get(key);
	}

	public final VariableMap getVariableMap() {
		return variableMap;
	}

	public final ObservableMap<Algorithm> getAlgorithmMap() {
		return algorithmMap;
	}

	public final ObservableMap<DataSet> getDataSetMap() {
		return dataSetMap;
	}

	public final String getEdgeLabel(String key) {
		return edgeLabels.get(key);
	}

	public final String toString() {
		if (getLabel() == null) {
			return getClass().getSimpleName();
		} else {
			return getClass().getSimpleName() + " [" + getLabel() + "]";
		}
	}

}
