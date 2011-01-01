/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.jdmp.weka.classifier;

import java.lang.reflect.Constructor;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.weka.wrappers.DataSetToInstancesWrapper;
import org.jdmp.weka.wrappers.SampleToInstanceWrapper;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class WekaClassifier extends AbstractClassifier {
	private static final long serialVersionUID = 29290678735702499L;

	public enum WekaClassifierType {
		AODE, BayesNet, ComplementNaiveBayes, HNB, NaiveBayes, NaiveBayesMultinomial, NaiveBayesMultinomialUpdateable, NaiveBayesSimple, NaiveBayesUpdateable, WAODE, GaussianProcesses, IsotonicRegression, LeastMedSq, LinearRegression, Logistic, MultilayerPerceptron, PaceRegression, PLSClassifier, RBFNetwork, SimpleLinearRegression, SimpleLogistic, SMO, SMOreg, SVMreg, VotedPerceptron, Winnow, IB1, IBk, KStar, LBR, LWL, AdaBoostM1, AdditiveRegression, AttributeSelectedClassifier, Bagging, ClassificationViaRegression, CostSensitiveClassifier, CVParameterSelection, Dagging, Decorate, END, EnsembleSelection, FilteredClassifier, Grading, GridSearch, LogitBoost, MetaCost, MultiBoostAB, MultiClassClassifier, MultiScheme, OrdinalClassClassifier, RacedIncrementalLogitBoost, RandomCommittee, RandomSubSpace, RegressionByDiscretization, Stacking, StackingC, ThresholdSelector, Vote, CitationKNN, MDD, MIBoost, MIDD, MIEMDD, MILR, MINND, MIOptimalBall, MISMO, MISVM, MIWrapper, SimpleMI, TLD, TLDSimple, FLR, HyperPipes, MinMaxExtension, OLM, OSDL, SerializedClassifier, VFI, ConjunctiveRule, DecisionTable, JRip, M5Rules, NNge, OneR, PART, Prism, Ridor, ZeroR, ADTree, BFTree, DecisionStump, Id3, J48, LMT, M5P, NBTree, RandomForest, RandomTree, REPTree, SimpleCart
	};

	private Classifier wekaClassifier = null;

	private Instances instances = null;

	private WekaClassifierType classifierName = null;

	private String[] options = null;

	private boolean discrete = false;

	public WekaClassifier(WekaClassifierType classifierName, boolean discrete, String... options)
			throws Exception {
		setLabel("Weka-" + classifierName);
		this.classifierName = classifierName;
		this.options = options;
		this.discrete = discrete;
		createAlgorithm();
	}

	public void createAlgorithm() throws Exception {
		Class<?> c = null;
		if (c == null) {
			try {
				c = Class.forName(classifierName.name());
			} catch (ClassNotFoundException e) {
			}
		}
		if (c == null) {
			try {
				c = Class.forName("weka.classifiers." + classifierName.name());
			} catch (ClassNotFoundException e) {
			}
		}
		if (c == null) {
			try {
				c = Class.forName("weka.classifiers.bayes." + classifierName.name());
			} catch (ClassNotFoundException e) {
			}
		}
		if (c == null) {
			try {
				c = Class.forName("weka.classifiers.functions." + classifierName);
			} catch (ClassNotFoundException e) {
			}
		}
		if (c == null) {
			try {
				c = Class.forName("weka.classifiers.lazy." + classifierName);
			} catch (ClassNotFoundException e) {
			}
		}
		if (c == null) {
			try {
				c = Class.forName("weka.classifiers.meta." + classifierName);
			} catch (ClassNotFoundException e) {
			}
		}
		if (c == null) {
			try {
				c = Class.forName("weka.classifiers.mi." + classifierName);
			} catch (ClassNotFoundException e) {
			}
		}
		if (c == null) {
			try {
				c = Class.forName("weka.classifiers.misc." + classifierName);
			} catch (ClassNotFoundException e) {
			}
		}
		if (c == null) {
			try {
				c = Class.forName("weka.classifiers.trees." + classifierName);
			} catch (ClassNotFoundException e) {
			}
		}
		if (c == null) {
			try {
				c = Class.forName("weka.classifiers.rules." + classifierName);
			} catch (ClassNotFoundException e) {
			}
		}
		if (c == null) {
			throw new ClassNotFoundException("class not found: " + classifierName);
		} else {
			Constructor<?> constr = c.getConstructor(new Class[] {});
			wekaClassifier = (Classifier) constr.newInstance(new Object[] {});
			if (options != null || options.length != 0) {
				wekaClassifier.setOptions(options);
			}
		}
	}

	
	public Matrix predict(Matrix input, Matrix weight) throws Exception {
		double[] probabilities = null;
		Instance instance = new SampleToInstanceWrapper(input, weight, null, discrete, true);
		instance.setDataset(instances);
		probabilities = wekaClassifier.distributionForInstance(instance);
		double[][] v = new double[1][];
		v[0] = probabilities;
		Matrix output = MatrixFactory.linkToArray(v);
		return output;
	}

	
	public void train(RegressionDataSet dataSet) throws Exception {
		instances = new DataSetToInstancesWrapper(dataSet, discrete, true);
		wekaClassifier.buildClassifier(instances);
	}

	
	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) {
		throw new RuntimeException("this method is not supported for WEKA classifiers");
	}

	
	public void reset() throws Exception {
		createAlgorithm();
	}

	public org.jdmp.core.algorithm.classification.Classifier emptyCopy() throws Exception {
		return new WekaClassifier(classifierName, discrete);
	}

}
