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

package org.jdmp.weka.wrappers;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

import weka.core.DenseInstance;

public class SampleToInstanceWrapper extends DenseInstance {
	private static final long serialVersionUID = 6525723600252564795L;

	public SampleToInstanceWrapper(Matrix input, Matrix sampleWeight, Matrix targetOutput,
			boolean discrete, boolean includeTarget) {
		super((int) input.toRowVector(Ret.LINK).getRowCount() + 1);
		input = input.toRowVector(Ret.LINK);
		if (sampleWeight != null) {
			setWeight(sampleWeight.doubleValue());
		} else {
			setWeight(1.0);
		}
		for (int i = 0; i < input.getRowCount(); i++) {
			if (discrete) {
				setValue(i, (int) input.getAsDouble(i, 0));
			} else {
				setValue(i, input.getAsDouble(i, 0));
			}
		}
		if (includeTarget && targetOutput != null) {
			long[] c = targetOutput.toRowVector(Ret.NEW).getCoordinatesOfMaximum();
			setValue((int) input.getRowCount(), c[Matrix.ROW]);
		}
	}

}
