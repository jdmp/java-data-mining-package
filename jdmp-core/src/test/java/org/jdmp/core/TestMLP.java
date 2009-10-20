/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

import junit.framework.TestCase;

import org.jdmp.core.algorithm.classification.mlp.MultiLayerNetwork;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.DataSetFactory;
import org.ujmp.core.Matrix;

public class TestMLP extends TestCase {

	public void testMLP() throws Exception {
		ClassificationDataSet iris = DataSetFactory.IRIS();

		iris.standardize(Matrix.ROW);
		MultiLayerNetwork mlp = new MultiLayerNetwork(10);
		mlp.setLearningRate(0.01);

		for (int i = 0; i < 100; i++) {
			mlp.trainOnce(iris);
		}
		mlp.predict(iris);

		assertEquals(0.95, iris.getAccuracy(), 0.05);
	}

}
