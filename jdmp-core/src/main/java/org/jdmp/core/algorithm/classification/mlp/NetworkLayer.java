/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

import java.io.Serializable;

import org.jdmp.core.algorithm.classification.mlp.MultiLayerNetwork.Aggregation;
import org.jdmp.core.algorithm.classification.mlp.MultiLayerNetwork.BiasType;
import org.jdmp.core.algorithm.classification.mlp.MultiLayerNetwork.Transfer;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.exceptions.MatrixException;

public class NetworkLayer implements Serializable {
	private static final long serialVersionUID = 3933537008613985003L;

	private NetworkLayer previousLayer = null;

	private NetworkLayer nextLayer = null;

	private NetworkLayerForward algorithmForward = null;

	private NetworkLayerBackward algorithmBackward = null;

	private WeightUpdate algorithmWeightUpdate = null;

	private BiasType biasType = null;

	private Aggregation aggregation = null;

	public NetworkLayer(Aggregation aggregationFunction, Transfer transferFunction,
			BiasType biasType) {
		algorithmForward = new NetworkLayerForward(aggregationFunction, transferFunction, biasType);
		algorithmBackward = new NetworkLayerBackward(transferFunction, biasType);
		algorithmWeightUpdate = new WeightUpdate(biasType);
		algorithmWeightUpdate.setContactDeviationVariable(algorithmBackward
				.getContactDeviationVariable());
		this.biasType = biasType;
		this.aggregation = aggregationFunction;
	}

	public NetworkLayer(Aggregation aggregationFunction, Transfer transferFunction,
			BiasType biasType, int neuronCount) {
		this(aggregationFunction, transferFunction, biasType);
		Variable outputDeviation = VariableFactory.labeledVariable("Output Deviation");
		outputDeviation.setSize(neuronCount, 1);
		setOutputDeviationVariable(outputDeviation);
		Variable output = VariableFactory.labeledVariable("Output");
		output.setSize(neuronCount, 1);
		setOutputVariable(output);
	}

	public void setLayer(int nr) {
		algorithmBackward.setLayer(nr);
		algorithmForward.setLayer(nr);
		algorithmWeightUpdate.setLayer(nr);
	}

	public NetworkLayerBackward getAlgorithmBackward() {
		return algorithmBackward;
	}

	public void setLearningRate(double v) {
		getAlgorithmWeightUpdate().setLearningRate(v);
	}

	public double getLearningRate() {
		return getAlgorithmWeightUpdate().getLearningRate();
	}

	public void setSampleWeight(double v) {
		getAlgorithmWeightUpdate().setSampleWeight(v);
	}

	public WeightUpdate getAlgorithmWeightUpdate() {
		return algorithmWeightUpdate;
	}

	public void setAlgorithmBackward(NetworkLayerBackward algorithmBackward) {
		this.algorithmBackward = algorithmBackward;
	}

	public NetworkLayerForward getAlgorithmForward() {
		return algorithmForward;
	}

	public void setAlgorithmForward(NetworkLayerForward algorithmForward) {
		this.algorithmForward = algorithmForward;
	}

	public NetworkLayer getNextLayer() {
		return nextLayer;
	}

	public void setNextLayer(NetworkLayer nextLayer) {
		this.nextLayer = nextLayer;

		nextLayer.setInputVariable(getOutputVariable());
		setOutputDeviationVariable(nextLayer.getInputDeviationVariable());

	}

	public NetworkLayer getPreviousLayer() {
		return previousLayer;
	}

	public void setPreviousLayer(NetworkLayer previousLayer) {
		this.previousLayer = previousLayer;

		if (previousLayer != null) {
			setInputVariable(previousLayer.getOutputVariable());
			previousLayer.setOutputDeviationVariable(getInputDeviationVariable());
		}
	}

	public Variable getInputDeviationVariable() {
		return algorithmBackward.getInputDeviationVariable();
	}

	public void setWeightVariable(Variable weight) {
		algorithmForward.setWeightVariable(weight);
		algorithmBackward.setWeightVariable(weight);
		algorithmWeightUpdate.setWeightVariable(weight);
	}

	public void calculateForward() throws Exception {
		createVariablesIfNecessary();
		algorithmForward.calculate();
	}

	public void calculateBackward() throws Exception {
		createVariablesIfNecessary();
		algorithmBackward.calculate();
	}

	public void calculateWeightUpdate() throws Exception {
		algorithmWeightUpdate.calculate();
	}

	public Variable getWeightVariable() {
		return algorithmForward.getWeightVariable();
	}

	public void createVariablesIfNecessary() throws MatrixException {
		if (getWeightVariable() == null) {
			Variable weight = VariableFactory.labeledVariable("Weight");
			Matrix w = null;

			double scale = 1;
			if (aggregation == Aggregation.SUM) {
				scale = 1.0 / getInputVariable().getRowCount()
						/ getInputVariable().getColumnCount();
			}

			switch (biasType) {
			case SINGLE:
				w = MatrixFactory.randn(
						getOutputVariable().getRowCount() * getOutputVariable().getColumnCount(),
						getInputVariable().getRowCount() * getInputVariable().getColumnCount() + 1)
						.times(scale);
				break;
			case MULTIPLE:
				w = MatrixFactory.randn(
						getOutputVariable().getRowCount() * getOutputVariable().getColumnCount(),
						getInputVariable().getRowCount() * getInputVariable().getColumnCount() * 2)
						.times(scale);
				break;
			case NONE:
				w = MatrixFactory.randn(
						getOutputVariable().getRowCount() * getOutputVariable().getColumnCount(),
						getInputVariable().getRowCount() * getInputVariable().getColumnCount())
						.times(scale);
				break;
			}

			weight.addMatrix(w);
			setWeightVariable(weight);
			// System.out.println("weight " + getLabel() + ": " +
			// weight.getColumnCount() + "->" + weight.getRowCount());
		}
	}

	public String getLabel() {
		return algorithmForward.getLabel();
	}

	public void reset() throws MatrixException {
		createVariablesIfNecessary();
		Matrix w = MatrixFactory.randn(
				getOutputVariable().getRowCount() * getOutputVariable().getColumnCount(),
				getInputVariable().getRowCount() * getInputVariable().getColumnCount()).times(0.01);
		getWeightVariable().addMatrix(w);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		if (getPreviousLayer() != null) {
			s.append("(" + getPreviousLayer().getNeuronCount() + " -->) ");
		}
		s.append(getNeuronCount());
		if (getNextLayer() != null) {
			s.append(" (--> " + getNextLayer().getNeuronCount() + ")");
		}
		return s.toString();
	}

	public int getNeuronCount() {
		return (int) getOutputDeviationVariable().getRowCount();
	}

	public void setInputVariable(Variable v) {
		algorithmForward.setInputVariable(v);
		algorithmWeightUpdate.setInputVariable(v);
	}

	public void setOutputDeviationVariable(Variable v) {
		algorithmBackward.setOutputDeviationVariable(v);
	}

	public void setOutputVariable(Variable v) {
		algorithmForward.setOutputVariable(v);
		algorithmBackward.setOutputVariable(v);
	}

	public Variable getInputVariable() {
		return algorithmForward.getInputVariable();
	}

	public Variable getOutputDeviationVariable() {
		return algorithmBackward.getOutputDeviationVariable();
	}

	public Variable getOutputVariable() {
		return algorithmForward.getOutputVariable();
	}

	public void addInputMatrix(Matrix matrix) {
		algorithmForward.setMatrix(NetworkLayerForward.INPUT, matrix);
	}

}
