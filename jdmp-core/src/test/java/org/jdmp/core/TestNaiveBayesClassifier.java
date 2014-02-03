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

package org.jdmp.core;

import static org.junit.Assert.assertEquals;

import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.algorithm.classification.bayes.NaiveBayesClassifier;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.CrossValidation;
import org.jdmp.core.dataset.DataSetFactory;
import org.jdmp.core.variable.Variable;
import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.intmatrix.calculation.Discretize.DiscretizationMethod;
import org.ujmp.core.listmatrix.ListMatrix;

public class TestNaiveBayesClassifier {

	@Test
	public void testIrisClassification() throws Exception {
		ClassificationDataSet iris = DataSetFactory.IRIS();
		iris.getMatrix(Variable.INPUT).discretize(Ret.ORIG, Matrix.ROW,
				DiscretizationMethod.STANDARDBINNING, 3);
		Classifier c = new NaiveBayesClassifier();
		ListMatrix<Double> results = CrossValidation.run(c, iris, 10, 10, 0);
		assertEquals(0.959, results.getMeanValue(), 0.04);
	}

}
