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

package org.jdmp.core.algorithm.classification;

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;

public class RandomClassifier extends AbstractClassifier {
	private static final long serialVersionUID = -8043103888877795342L;

	private int classCount = 0;

	public RandomClassifier() {
		super();
	}

	
	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		return MatrixFactory.rand(1, classCount);
	}

	
	public void reset() throws Exception {
		classCount = 0;
	}

	
	public void train(RegressionDataSet dataSet) throws Exception {
		for (Sample s : dataSet.getSamples()) {
			Matrix m = s.getMatrix(Variable.TARGET);
			classCount = (int) Math.max(classCount, m.getColumnCount());
		}
	}

	
	public Classifier emptyCopy() throws Exception {
		return new RandomClassifier();
	}

}
