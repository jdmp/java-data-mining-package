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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jdmp.core.algorithm.AlgorithmTwoSources;
import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.listmatrix.DefaultListMatrix;

public class MultiLayerNetwork extends AbstractClassifier {
	private static final long serialVersionUID = -6875411556504865444L;

	private Aggregation aggregationInput = null;

	private Transfer transferInput = null;

	private BiasType biasInput = null;

	private Aggregation aggregationDefault = null;

	private Transfer transferDefault = null;

	private BiasType biasDefault = null;

	private Aggregation aggregationOutput = null;

	private Transfer transferOutput = null;

	private BiasType biasOutput = null;

	private int[] neuronCount = null;

	public enum Aggregation {
		MEAN, SUM
	};

	public enum Transfer {
		TANH, TANHPLUSONE, SIGMOID, LINEAR, SIN, LOG, GAUSS
	};

	public enum BiasType {
		NONE, SINGLE, MULTIPLE
	};

	private final List<NetworkLayer> networkLayers = new ArrayList<NetworkLayer>();

	public MultiLayerNetwork(Aggregation aggregationInput, Transfer transferInput,
			BiasType biasInput, Aggregation aggregationDefault, Transfer transferDefault,
			BiasType biasDefault, Aggregation aggregationOutput, Transfer transferOutput,
			BiasType biasOutput, int... hiddenNeurons) {
		super();

		this.aggregationInput = aggregationInput;
		this.transferInput = transferInput;
		this.biasInput = biasInput;
		this.aggregationDefault = aggregationDefault;
		this.transferDefault = transferDefault;
		this.biasDefault = biasDefault;
		this.aggregationOutput = aggregationOutput;
		this.transferOutput = transferOutput;
		this.biasOutput = biasOutput;
		this.neuronCount = hiddenNeurons;

		NetworkLayer previousLayer = null;
		if (hiddenNeurons != null) {
			NetworkLayer hiddenLayer = null;
			for (int i = 0; i < hiddenNeurons.length; i++) {
				if (i == 0) {
					hiddenLayer = new NetworkLayer(aggregationInput, transferInput, biasInput,
							hiddenNeurons[i]);
				} else {
					hiddenLayer = new NetworkLayer(aggregationDefault, transferDefault,
							biasDefault, hiddenNeurons[i]);
				}
				getAlgorithmMap().put("hidden" + i + "-forward", hiddenLayer.getAlgorithmForward());
				getAlgorithmMap().put("hidden" + i + "-backward", hiddenLayer.getAlgorithmBackward());
				getAlgorithmMap().put("hidden" + i + "-update",
						hiddenLayer.getAlgorithmWeightUpdate());
				hiddenLayer.setLayer(i);
				networkLayers.add(hiddenLayer);
				if (previousLayer != null) {
					previousLayer.setNextLayer(hiddenLayer);
				}
				hiddenLayer.setPreviousLayer(previousLayer);
				previousLayer = hiddenLayer;
			}
		}

		NetworkLayer outputLayer = new NetworkLayer(aggregationOutput, transferOutput, biasOutput);
		getAlgorithmMap().put("output" + "-forward", outputLayer.getAlgorithmForward());
		getAlgorithmMap().put("output" + "-backward", outputLayer.getAlgorithmBackward());
		getAlgorithmMap().put("output" + "-update", outputLayer.getAlgorithmWeightUpdate());
		outputLayer.setLayer(hiddenNeurons.length);
		networkLayers.add(outputLayer);
		if (previousLayer != null) {
			previousLayer.setNextLayer(outputLayer);
			outputLayer.setPreviousLayer(previousLayer);
		}

		Variable output = VariableFactory.labeledVariable("Output");
		setOutputVariable(output);

		Variable outputDeviation = VariableFactory.labeledVariable("Output Deviation");
		setOutputDeviationVariable(outputDeviation);

		Variable input = VariableFactory.labeledVariable("Input");
		setInputVariable(input);

		Variable desiredOutput = VariableFactory.labeledVariable("Desired Output");
		setDesiredOutputVariable(desiredOutput);
	}

	public MultiLayerNetwork(int... neuronCount) {
		this(Aggregation.SUM, Transfer.TANH, BiasType.SINGLE, Aggregation.SUM, Transfer.TANH,
				BiasType.SINGLE, Aggregation.SUM, Transfer.TANH, BiasType.SINGLE, neuronCount);
	}

	public MultiLayerNetwork(Aggregation aggregation, Transfer transfer, int... neuronCount) {
		this(aggregation, transfer, BiasType.SINGLE, aggregation, transfer, BiasType.SINGLE,
				aggregation, transfer, BiasType.SINGLE, neuronCount);
	}

	public MultiLayerNetwork(Aggregation aggregation, BiasType bias, int... neuronCount) {
		this(aggregation, Transfer.TANH, bias, aggregation, Transfer.TANH, bias, aggregation,
				Transfer.TANH, bias, neuronCount);
	}

	public MultiLayerNetwork(Aggregation aggregation, int... neuronCount) {
		this(aggregation, Transfer.TANH, BiasType.SINGLE, aggregation, Transfer.TANH,
				BiasType.SINGLE, aggregation, Transfer.TANH, BiasType.SINGLE, neuronCount);
	}

	public MultiLayerNetwork(Aggregation aggregation, Transfer transfer, BiasType biasType,
			int... neurons) {
		this(aggregation, transfer, biasType, aggregation, transfer, biasType, aggregation,
				transfer, biasType, neurons);
	}

	public void setOutputVariable(Variable v) {
		getOutputErrorAlgorithm().setVariable(AlgorithmTwoSources.SOURCE1, v);
		getOutputLayer().setOutputVariable(v);
	}

	public void setOutputDeviationVariable(Variable v) {
		getOutputErrorAlgorithm().setVariable(AlgorithmTwoSources.TARGET, v);
		getOutputLayer().setOutputDeviationVariable(v);
	}

	public void setLearningRate(double v) {
		for (NetworkLayer networkLayer : networkLayers) {
			networkLayer.setLearningRate(v);
		}
	}

	public double getLearningRate() {
		return networkLayers.get(0).getLearningRate();
	}

	public void reset() {
		for (NetworkLayer networkLayer : networkLayers) {
			networkLayer.reset();
		}
	}

	public NetworkLayer getOutputLayer() {
		return networkLayers.get(networkLayers.size() - 1);
	}

	public NetworkLayer getInputLayer() {
		return networkLayers.get(0);
	}

	public Variable getInputVariable() {
		return getInputLayer().getInputVariable();
	}

	public void setInputVariable(Variable v) {
		getInputLayer().setInputVariable(v);
	}

	public void setDesiredOutputVariable(Variable v) {
		getOutputErrorAlgorithm().setVariable(AlgorithmTwoSources.SOURCE2, v);
		getOutputLayer().getOutputVariable().setInnerSize(v.getInnerRowCount(),
				v.getInnerColumnCount());
		getOutputLayer().getOutputDeviationVariable().setInnerSize(v.getInnerRowCount(),
				v.getInnerColumnCount());
	}

	public void addInputMatrix(Matrix m) {
		getInputLayer().addInputMatrix(m);
	}

	public void setSampleWeight(double weight) {
		for (NetworkLayer networkLayer : networkLayers) {
			networkLayer.setSampleWeight(weight);
		}
	}

	public void addDesiredOutputMatrix(Matrix m) {
		getOutputErrorAlgorithm().getVariableMap().setMatrix(AlgorithmTwoSources.SOURCE2, m);
		if (Coordinates.product(getOutputVariable().getInnerSize()) == 0) {
			getOutputVariable().setInnerSize(m.getRowCount(), m.getColumnCount());
		}
		if (Coordinates.product(getOutputDeviationVariable().getInnerSize()) == 0) {
			getOutputDeviationVariable().setInnerSize(m.getRowCount(), m.getColumnCount());
		}
	}

	public Variable getOutputVariable() {
		return getOutputLayer().getOutputVariable();
	}

	public Variable getOutputDeviationVariable() {
		return getOutputLayer().getOutputDeviationVariable();
	}

	public List<NetworkLayer> getNetworkLayerList() {
		return networkLayers;
	}

	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		// transpose and add bias unit
		// Matrix inputWithBias = Matrix.zeros(input.getColumnCount() + 1,
		// input.getRowCount());
		// for (long i = input.getColumnCount() - 1; i != -1; i--) {
		// inputWithBias.setDouble(input.getDouble(0, i), i, 0);
		// }
		// inputWithBias.setDouble(1.0, inputWithBias.getRowCount() - 1, 0);
		addInputMatrix(input);

		for (NetworkLayer networkLayer : getNetworkLayerList()) {
			networkLayer.calculateForward();
		}

		Matrix actualOutput = getOutputMatrix().transpose();
		return actualOutput;
	}

	public Matrix getOutputMatrix() {
		return getOutputVariable().getLatestMatrix();
	}

	public void train(Matrix input, Matrix sampleWeight, Matrix desiredOutput) throws Exception {
		addDesiredOutputMatrix(desiredOutput.toRowVector(Ret.NEW));
		if (sampleWeight == null) {
			sampleWeight = Matrix.Factory.linkToValue(1.0);
		}
		setSampleWeight(sampleWeight.doubleValue());
		predict(input, sampleWeight);
		getOutputErrorAlgorithm().calculate();

		for (int i = networkLayers.size() - 1; i != -1; i--) {
			networkLayers.get(i).calculateBackward();
		}

		for (int i = networkLayers.size() - 1; i != -1; i--) {
			networkLayers.get(i).calculateWeightUpdate();
		}

	}

	public int determineOptimalTrainingDuration(ClassificationDataSet dataSet, int numberOfSteps)
			throws Exception {
		long seed = System.currentTimeMillis();
		DefaultListMatrix<Double> duration = new DefaultListMatrix<Double>();
		for (int r = 0; r < 10; r++) {
			List<ClassificationDataSet> dss = dataSet.splitForStratifiedCV(10, r, seed);
			ClassificationDataSet train = dss.get(0);
			ClassificationDataSet test = dss.get(1);
			MultiLayerNetwork a = new MultiLayerNetwork(aggregationInput, transferInput, biasInput,
					aggregationDefault, transferDefault, biasDefault, aggregationOutput,
					transferOutput, biasOutput, neuronCount);
			a.setLearningRate(getLearningRate());
			for (int i = 0; i < 10000; i++) {
				a.train(train);
				a.predict(test);
				// System.out.println(i+": "+test.getRMSE());
				if (test.isEarlyStoppingReached(numberOfSteps)) {
					double d = test.getEarlyStoppingIndex(numberOfSteps);
					duration.add(d);
					// System.out.println("CV"+r+": "+d);
					break;
				}
			}
		}
		int mean = (int) (duration.getMeanValue() * 0.9);
		// System.out.println("Optimal: "+mean);
		if (mean == 0) {
			mean = 1;
		}
		return mean;
	}

	public void train(RegressionDataSet dataSet) throws Exception {

		// TODO: fix!

		List<Sample> samples = new ArrayList<Sample>(dataSet.getSampleMap().values());
		Collections.shuffle(samples);

		int last10Percent = (int) Math.ceil((samples.size() * 0.1));
		int first90Percent = samples.size() - last10Percent;

		for (int i = 0; i < first90Percent; i++) {
			train(samples.get(i));
		}

		// Crossvalidation
		double rmse = 0;
		for (int i = first90Percent; i < samples.size(); i++) {
			Sample rs = samples.get(i);
			Matrix output = predict(rs.getVariableMap().getMatrix(INPUT), rs.getVariableMap()
					.getMatrix(WEIGHT));
			rmse += output.minus(rs.getVariableMap().getMatrix(TARGET)).getRMS();
			train(samples.get(i));
		}
		rmse /= last10Percent;

		System.out.println("RMSE on " + last10Percent + " Samples: " + rmse);

		for (int i = first90Percent; i < samples.size(); i++) {
			train(samples.get(i));
		}
	}

	public void trainOnce(RegressionDataSet dataSet) throws Exception {
		List<Sample> samples = new ArrayList<Sample>(dataSet.getSampleMap().values());
		Collections.shuffle(samples);
		for (Sample s : samples) {
			train(s);
		}
		dataSet.notifyGUIObject();
	}

	public Classifier emptyCopy() throws Exception {
		return null;
	}

}
