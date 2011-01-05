/*
 * Copyright (C) 2008-2011 by Holger Arndt
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
import org.ujmp.core.exceptions.MatrixException;

import weka.core.DenseInstance;

public class SampleToInstanceWrapper extends DenseInstance {
	private static final long serialVersionUID = 6525723600252564795L;

	public SampleToInstanceWrapper(Matrix input, Matrix sampleWeight, Matrix targetOutput,
			boolean discrete, boolean includeTarget) throws MatrixException {
		super((int) input.getColumnCount() + 1);
		if (sampleWeight != null) {
			setWeight(sampleWeight.doubleValue());
		} else {
			setWeight(1.0);
		}
		if (input != null) {
			for (int i = 0; i < input.getColumnCount(); i++) {
				if (discrete) {
					setValue(i, (int) input.getAsDouble(0, i));
				} else {
					setValue(i, input.getAsDouble(0, i));
				}
			}
		}
		if (includeTarget && targetOutput != null) {
			long[] c = targetOutput.getCoordinatesOfMaximum();
			setValue((int) input.getColumnCount(), c[Matrix.COLUMN]);
		}
	}

}
