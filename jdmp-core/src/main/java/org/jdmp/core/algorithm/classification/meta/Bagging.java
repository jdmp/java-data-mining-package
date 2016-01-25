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

package org.jdmp.core.algorithm.classification.meta;

import java.util.Arrays;
import java.util.List;

import org.jdmp.core.algorithm.regression.AbstractRegressor;
import org.jdmp.core.algorithm.regression.Regressor;
import org.jdmp.core.dataset.ListDataSet;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.collections.list.FastArrayList;

public class Bagging extends AbstractRegressor {
	private static final long serialVersionUID = -4111544932911087016L;

	private final List<Regressor> learningAlgorithms;

	private final int bootstrapSize;

	public Bagging(Regressor learningAlgorithm, int count) {
		this(learningAlgorithm, count, -1);
	}

	public Bagging(Regressor learningAlgorithm, int count, int bootstrapSize) {
		this.bootstrapSize = bootstrapSize;
		this.learningAlgorithms = new FastArrayList<Regressor>();
		for (int i = 0; i < count; i++) {
			learningAlgorithms.add(learningAlgorithm.emptyCopy());
		}
	}

	public Bagging(Regressor... learningAlgorithms) {
		this(-1, learningAlgorithms);
	}

	public Bagging(int bootstrapSize, Regressor... learningAlgorithms) {
		this.bootstrapSize = bootstrapSize;
		this.learningAlgorithms = Arrays.asList(learningAlgorithms);
	}

	public void trainAll(ListDataSet dataSet) {
		for (Regressor learningAlgorithm : learningAlgorithms) {
			ListDataSet bootstrap;
			if (bootstrapSize > 0) {
				bootstrap = dataSet.bootstrap(bootstrapSize);
			} else {
				bootstrap = dataSet.bootstrap();
			}
			learningAlgorithm.trainAll(bootstrap);
		}
	}

	public void reset() {
		for (Regressor learningAlgorithm : learningAlgorithms) {
			learningAlgorithm.reset();
		}
	}

	public Matrix predictOne(Matrix input) {
		List<Matrix> results = new FastArrayList<Matrix>();
		for (Regressor learningAlgorithm : learningAlgorithms) {
			Matrix result = learningAlgorithm.predictOne(input);
			results.add(result);
		}
		Matrix all = Matrix.Factory.vertCat(results);
		Matrix mean = all.mean(Ret.NEW, Matrix.ROW, true);
		return mean;
	}

	public Regressor emptyCopy() {
		Bagging bagging = new Bagging();
		for (Regressor learningAlgorithm : learningAlgorithms) {
			bagging.learningAlgorithms.add(learningAlgorithm.emptyCopy());
		}
		return bagging;
	}

}
