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

package org.jdmp.mallet;

import junit.framework.TestCase;

import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.CrossValidation;
import org.jdmp.core.dataset.DataSetFactory;
import org.jdmp.mallet.classifier.MalletClassifier;
import org.jdmp.mallet.classifier.MalletClassifier.MalletClassifiers;
import org.ujmp.core.listmatrix.ListMatrix;

public class TestMalletClassifier extends TestCase {

	public void testMalletDecisionTree() throws Exception {
		ClassificationDataSet iris = DataSetFactory.IRIS();
		Classifier c = new MalletClassifier(MalletClassifiers.DecisionTree);
		ListMatrix<Double> results = CrossValidation.run(c, iris, 10, 10, 0);
		assertEquals(0.934, results.getMeanValue(), 0.001);
	}

	public void testMalletAdaBoost() throws Exception {
		ClassificationDataSet iris = DataSetFactory.IRIS();
		Classifier c = new MalletClassifier(MalletClassifiers.AdaBoost);
		ListMatrix<Double> results = CrossValidation.run(c, iris, 10, 10, 0);
		assertEquals(0.93, results.getMeanValue(), 0.01);
	}

	public void testMalletNaiveBayes() throws Exception {
		ClassificationDataSet iris = DataSetFactory.IRIS();
		Classifier c = new MalletClassifier(MalletClassifiers.NaiveBayes);
		ListMatrix<Double> results = CrossValidation.run(c, iris, 10, 10, 0);
		assertEquals(0.9273, results.getMeanValue(), 0.001);
	}

}
