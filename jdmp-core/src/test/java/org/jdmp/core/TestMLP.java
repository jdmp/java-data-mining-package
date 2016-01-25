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

package org.jdmp.core;

import static org.junit.Assert.assertEquals;

import org.jdmp.core.algorithm.classification.mlp.MultiLayerNetwork;
import org.jdmp.core.dataset.ListDataSet;
import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public class TestMLP {

	@Test
	public void testMLP() throws Exception {
		ListDataSet iris = ListDataSet.Factory.IRIS();
		iris.getInputMatrix().standardize(Ret.ORIG, Matrix.ROW);

		MultiLayerNetwork mlp = new MultiLayerNetwork(10);
		mlp.setLearningRate(0.05);

		for (int i = 0; i < 300; i++) {
			mlp.trainOnce(iris);
		}
		mlp.predictAll(iris);

		assertEquals(0.90, iris.getAccuracy(), 0.2);
	}
}
