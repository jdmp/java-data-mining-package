/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import java.util.Collections;
import java.util.List;

import org.jdmp.core.algorithm.regression.AbstractRegressor;
import org.jdmp.core.algorithm.regression.Regressor;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.collections.list.FastArrayList;
import org.ujmp.core.util.Sortable;

public class KNNClassifier extends AbstractRegressor {
	private static final long serialVersionUID = 5971192321313837066L;

	private final int k;

	public KNNClassifier(int k) {
		this.k = k;
	}

	private ListDataSet dataSet = null;

	public Regressor emptyCopy() {
		return new KNNClassifier(k);
	}

	public void trainAll(ListDataSet dataSet) {
		this.dataSet = dataSet;
	}

	public void reset() {
		this.dataSet = null;
	}

	public Matrix predictOne(Matrix input) {
		List<Sortable<Double, Matrix>> bestResults = new FastArrayList<Sortable<Double, Matrix>>();
		for (Sample s : dataSet) {
			Matrix reference = s.getAsMatrix(getInputLabel());
			double distance = input.euklideanDistanceTo(reference, true);
			if (bestResults.size() < k) {
				bestResults.add(new Sortable<Double, Matrix>(distance, s
						.getAsMatrix(getTargetLabel())));
				Collections.sort(bestResults);
			} else if (distance < bestResults.get(k - 1).getComparable()) {
				bestResults.remove(k - 1);
				bestResults.add(new Sortable<Double, Matrix>(distance, s
						.getAsMatrix(getTargetLabel())));
				Collections.sort(bestResults);
			}
		}
		List<Matrix> results = new FastArrayList<Matrix>();
		for (Sortable<Double, Matrix> s : bestResults) {
			results.add(s.getObject().toColumnVector(Ret.LINK));
		}
		Matrix resultMatrix = Matrix.Factory.vertCat(results);
		Matrix mean = resultMatrix.mean(Ret.NEW, Matrix.ROW, true);
		return mean;
	}
}
