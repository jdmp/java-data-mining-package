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

package org.jdmp.core.algorithm.classification;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;

public class KNNClassifier extends AbstractClassifier {
	private static final long serialVersionUID = 5971192321313837066L;

	private final int k;

	public KNNClassifier(int k) {
		this.k = k;
	}

	private DataSet dataSet = null;

	public Classifier emptyCopy() throws Exception {
		return new KNNClassifier(k);
	}

	@Override
	public void train(DataSet dataSet) throws Exception {
		this.dataSet = dataSet;
	}

	@Override
	public void reset() throws Exception {
		this.dataSet = null;
	}

	@Override
	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		Matrix bestMatrix = null;
		double bestDistance = Double.MAX_VALUE;
		for (Sample s : dataSet.getSampleMap().values()) {
			Matrix reference = s.getMatrix(INPUT);
			double distance = input.euklideanDistanceTo(reference, true);
			if (distance < bestDistance) {
				bestMatrix = s.getMatrix(TARGET);
				bestDistance = distance;
			}
		}
		return bestMatrix;
	}

}
