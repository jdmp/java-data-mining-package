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

package org.jdmp.mallet.matrix;

import java.util.Arrays;

import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.Wrapper;

import cc.mallet.types.FeatureVector;

public class MalletInputMatrix extends AbstractDenseDoubleMatrix2D implements
		Wrapper<FeatureVector> {
	private static final long serialVersionUID = -6470168950903884715L;

	private FeatureVector featureVector = null;

	public MalletInputMatrix(FeatureVector fv) {
		this.featureVector = fv;
	}

	public long[] getSize() {
		return new long[] { 1, featureVector.getAlphabet().size() };
	}

	public FeatureVector getWrappedObject() {
		return featureVector;
	}

	public void setWrappedObject(FeatureVector object) {
		this.featureVector = object;
	}

	public double getDouble(long row, long column) {
		return Arrays.binarySearch(featureVector.getIndices(), 0, featureVector
				.numLocations(), (int) column) >= 0 ? 1.0 : 0.0;
	}

	public double getDouble(int row, int column) {
		return Arrays.binarySearch(featureVector.getIndices(), 0, featureVector
				.numLocations(), column) >= 0 ? 1.0 : 0.0;
	}

	public void setDouble(double value, long row, long column) {
	}

	public void setDouble(double value, int row, int column) {
	}

}
