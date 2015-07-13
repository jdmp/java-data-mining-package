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

import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;

public class ConstantClassifier extends AbstractClassifier {
	private static final long serialVersionUID = 4554183836593870371L;

	private Matrix prediction = null;

	private long maxClassId = 0;

	public ConstantClassifier() {
		super();
	}

	public Matrix predictOne(Matrix input) {
		return prediction;
	}

	public void reset() {
		prediction = null;
		maxClassId = 0;
	}

	public void trainAll(ListDataSet dataSet) {
		MapMatrix<Long, Integer> count = new DefaultMapMatrix<Long, Integer>();
		for (Sample s : dataSet) {
			Matrix m = s.getAsMatrix(Variable.TARGET);
			long target = m.getCoordinatesOfMaximum()[COLUMN];
			maxClassId = Math.max(maxClassId, target);
			Integer c = count.get(target);
			if (c == null) {
				c = 0;
			}
			c++;
			count.put(target, c);
		}
		int max = 0;
		long pc = 0;
		for (Long t : count.keySet()) {
			Integer c = count.get(t);
			if (c > max) {
				max = c;
				pc = t;
			}
		}
		prediction = Matrix.Factory.zeros(1, maxClassId + 1);
		prediction.setAsDouble(1.0, 0, pc);
	}

	public Classifier emptyCopy() {
		return new ConstantClassifier();
	}

}
