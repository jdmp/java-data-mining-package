/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

import static org.junit.Assert.assertEquals;

import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.CrossValidation;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.mallet.classifier.MalletClassifier;
import org.jdmp.mallet.classifier.MalletClassifier.MalletClassifiers;
import org.junit.Test;
import org.ujmp.core.listmatrix.ListMatrix;

public class TestMalletClassifier {

	@Test
	public void testMalletDecisionTree() throws Exception {
		ListDataSet iris = ListDataSet.Factory.IRIS();
		Classifier c = new MalletClassifier(MalletClassifiers.DecisionTree);
		ListMatrix<Double> results = CrossValidation.run(c, iris, 10, 10, 0);
		assertEquals(0.934, results.getMeanValue(), 0.01);
	}

	@Test
	public void testMalletAdaBoost() throws Exception {
		ListDataSet iris = ListDataSet.Factory.IRIS();
		Classifier c = new MalletClassifier(MalletClassifiers.AdaBoost);
		ListMatrix<Double> results = CrossValidation.run(c, iris, 10, 10, 0);
		assertEquals(0.93, results.getMeanValue(), 0.02);
	}

	@Test
	public void testMalletNaiveBayes() throws Exception {
		ListDataSet iris = ListDataSet.Factory.IRIS();
		Classifier c = new MalletClassifier(MalletClassifiers.NaiveBayes);
		ListMatrix<Double> results = CrossValidation.run(c, iris, 10, 10, 0);
		assertEquals(0.9273, results.getMeanValue(), 0.01);
	}

}
