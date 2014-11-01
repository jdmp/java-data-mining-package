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

package org.jdmp.core.algorithm.classification.mlp;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.AlgorithmTwoSources;
import org.jdmp.core.algorithm.basic.Clone;
import org.jdmp.core.algorithm.classification.mlp.MultiLayerNetwork.BiasType;
import org.jdmp.core.algorithm.classification.mlp.MultiLayerNetwork.Transfer;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;

public class NetworkLayerBackward extends AbstractAlgorithm {
	private static final long serialVersionUID = -8051094919417324985L;

	public static final String WEIGHT = "Weight";

	public static final String OUTPUT = "Output";

	public static final String OUTPUTDEVIATION = "OutputDeviation";

	public static final String RETURNINGFUNCTION = "ReturningFunction";

	public static final String NETDEVIATION = "NetDeviation";

	public static final String SPLITTINGFUNCTION = "SplittingFunction";

	public static final String CONTACTDEVIATION = "ContactDeviation";

	public static final String DIMMINGFUNCTION = "DimmingFunction";

	public static final String INPUTDEVIATION = "InputDeviation";

	public NetworkLayerBackward(Transfer transferFunction, BiasType biasType) {
		setDescription("One layer of a multi-layer network, backward path");
		setVariable(NETDEVIATION, Variable.Factory.labeledVariable("Net Deviation"));
		setVariable(CONTACTDEVIATION, Variable.Factory.labeledVariable("Contact Deviation"));
		setVariable(INPUTDEVIATION, Variable.Factory.labeledVariable("Input Deviation"));

		AlgorithmTwoSources ar = null;
		switch (transferFunction) {
		case TANH:
			ar = new ReturningFunctionTanh();
			break;
		case TANHPLUSONE:
			ar = new ReturningFunctionTanh();
			break;
		case LINEAR:
			ar = new ReturningFunctionOne();
			break;
		default:
			throw new RuntimeException("not implemented: " + transferFunction);
		}

		ar.setVariable(ReturningFunctionTanh.TARGET, getNetDeviationVariable());
		setAlgorithm(RETURNINGFUNCTION, ar);

		Clone as = new Clone();
		as.setVariable(Clone.SOURCE, getNetDeviationVariable());
		as.setVariable(Clone.TARGET, getContactDeviationVariable());
		setAlgorithm(SPLITTINGFUNCTION, as);

		DimmingFunction ad = new DimmingFunction(biasType);
		ad.setVariable(DimmingFunction.SOURCE2, getContactDeviationVariable());
		ad.setVariable(DimmingFunction.TARGET, getInputDeviationVariable());
		setAlgorithm(DIMMINGFUNCTION, ad);

	}

	public static void main(String[] args) throws Exception {
		NetworkLayerBackward a = new NetworkLayerBackward(Transfer.TANH, BiasType.SINGLE);

		Variable outputDeviation = Variable.Factory.labeledVariable("Output Deviation");
		Matrix d = Matrix.Factory.linkToArray(new double[][] { { 1 }, { 2 }, { 3 }, { 4 }, { 5 },
				{ 6 } });
		outputDeviation.add(d);
		a.setOutputDeviationVariable(outputDeviation);

		Variable output = Variable.Factory.labeledVariable("Output");
		Matrix o = Matrix.Factory.linkToArray(new double[][] { { 5 }, { 6 }, { 7 }, { 8 }, { 9 },
				{ 10 } });
		output.add(o);
		a.setOutputVariable(output);

		Variable weight = Variable.Factory.labeledVariable("Weight");
		Matrix w = Matrix.Factory.linkToArray(new double[][] { { 0.1, 0.2, 0.3, 0.4 },
				{ 0.1, 0.2, 0.3, 0.4 }, { 0.1, 0.2, 0.3, 0.4 }, { 0.1, 0.2, 0.3, 0.4 },
				{ 0.1, 0.2, 0.3, 0.4 }, { 0.1, 0.2, 0.3, 0.4 } });
		weight.add(w);
		a.setWeightVariable(weight);
		System.out.println(a.calculate());
	}

	public Map<String, Object> calculateObjects(Map<String, Object> input) {
		Map<String, Object> result = new HashMap<String, Object>();

		Algorithm returningFunction = getReturningFunction();
		returningFunction.calculate();

		Algorithm splittingFunction = getSplittingFunction();
		splittingFunction.calculate();

		Algorithm dimmingFunction = getDimmingFunction();
		dimmingFunction.calculate();

		return result;
	}

	public Variable getOutputDeviationVariable() {
		Variable v = getVariableMap().get(OUTPUTDEVIATION);
		return v;
	}

	public void setOutputDeviationVariable(Variable v) {
		setVariable(OUTPUTDEVIATION, v);
		getReturningFunction().setVariable(AlgorithmTwoSources.SOURCE2, v);
	}

	public Matrix getOutputDeviationMatrix() {
		return getVariableMap().getMatrix(OUTPUTDEVIATION);
	}

	public Algorithm getReturningFunction() {
		Algorithm a = getAlgorithmMap().get(RETURNINGFUNCTION);
		return a;
	}

	public void setReturningFunction(Algorithm a) {
		setAlgorithm(RETURNINGFUNCTION, a);
	}

	public Algorithm getSplittingFunction() {
		Algorithm a = getAlgorithmMap().get(SPLITTINGFUNCTION);
		return a;
	}

	public void setSplittingFunction(Algorithm a) {
		setAlgorithm(SPLITTINGFUNCTION, a);
	}

	public Algorithm getDimmingFunction() {
		Algorithm a = getAlgorithmMap().get(DIMMINGFUNCTION);
		return a;
	}

	public void setDimmingFunction(Algorithm a) {
		setAlgorithm(DIMMINGFUNCTION, a);
	}

	public Variable getNetDeviationVariable() {
		Variable v = getVariableMap().get(NETDEVIATION);
		return v;
	}

	public void setNetDeviationVariable(Variable v) {
		setVariable(NETDEVIATION, v);
	}

	public Matrix getNetDeviationMatrix() {
		return getVariableMap().getMatrix(NETDEVIATION);
	}

	public Variable getContactDeviationVariable() {
		Variable v = getVariableMap().get(CONTACTDEVIATION);
		return v;
	}

	public void setContactDeviationVariable(Variable v) {
		setVariable(CONTACTDEVIATION, v);
	}

	public Matrix getContactDeviationMatrix() {
		return getVariableMap().getMatrix(CONTACTDEVIATION);
	}

	public Variable getInputDeviationVariable() {
		Variable v = getVariableMap().get(INPUTDEVIATION);
		return v;
	}

	public void setInputDeviationVariable(Variable v) {
		setVariable(INPUTDEVIATION, v);
	}

	public Matrix getInputDeviationMatrix() {
		return getVariableMap().getMatrix(INPUTDEVIATION);
	}

	public Variable getWeightVariable() {
		Variable v = getVariableMap().get(WEIGHT);
		return v;
	}

	public void setWeightVariable(Variable v) {
		setVariable(WEIGHT, v);
		getDimmingFunction().setVariable(AlgorithmTwoSources.SOURCE1, v);
	}

	public Matrix getWeightMatrix() {
		return getVariableMap().getMatrix(WEIGHT);
	}

	public Variable getOutputVariable() {
		Variable v = getVariableMap().get(OUTPUT);
		return v;
	}

	public void setOutputVariable(Variable v) {
		setVariable(OUTPUT, v);
		getReturningFunction().setVariable(AlgorithmTwoSources.SOURCE1, v);
	}

	public Matrix getOutputMatrix() {
		return getVariableMap().getMatrix(OUTPUT);
	}

	public void setLayer(int nr) {
		setLabel("Network Layer Backward (" + nr + ")");
	}
}
