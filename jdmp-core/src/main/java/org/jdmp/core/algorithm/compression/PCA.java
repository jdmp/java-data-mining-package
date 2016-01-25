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

package org.jdmp.core.algorithm.compression;

import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public class PCA extends AbstractCompressor {
	private static final long serialVersionUID = 4559351198783166902L;

	private Matrix mean = null;
	private Matrix std = null;
	private Matrix u = null;
	private int numberOfPrincipalComponents = -1;

	public PCA(int numberOfPrincipalComponents) {
		this.numberOfPrincipalComponents = numberOfPrincipalComponents;
	}

	public PCA() {
		this(0);
	}

	public void reset() {
		mean = null;
		std = null;
		u = null;
	}

	public void train(ListDataSet dataSet) {
		System.out.println("training started");

		Matrix x = Matrix.Factory.zeros(dataSet.size(), getFeatureCount(dataSet));

		int i = 0;
		for (Sample s : dataSet) {
			Matrix input = s.getAsMatrix(getInputLabel()).toColumnVector(Ret.LINK);
			for (int c = 0; c < input.getColumnCount(); c++) {
				x.setAsDouble(input.getAsDouble(0, c), i, c);
			}
			i++;
		}

		System.out.println("data loaded");

		mean = x.mean(Ret.NEW, ROW, true);

		for (int r = 0; r < x.getRowCount(); r++) {
			for (int c = 0; c < x.getColumnCount(); c++) {
				x.setAsDouble(x.getAsDouble(r, c) - mean.getAsDouble(0, c), r, c);
			}
		}

		std = x.std(Ret.NEW, ROW, true, true);

		for (int r = 0; r < x.getRowCount(); r++) {
			for (int c = 0; c < x.getColumnCount(); c++) {
				x.setAsDouble(x.getAsDouble(r, c) / std.getAsDouble(0, c), r, c);
			}
		}

		Matrix xtx = x.transpose().mtimes(x);
		Matrix[] svd;
		if (numberOfPrincipalComponents == 0) {
			svd = xtx.svd();
		} else {
			svd = xtx.svd(numberOfPrincipalComponents);
		}
		u = svd[0];

		System.out.println("training finished");
	}

	public Matrix compress(Matrix input) {
		return input.toColumnVector(Ret.LINK).minus(Ret.LINK, true, mean).divide(std).mtimes(u);
	}

	public Matrix decompress(Matrix input) {
		return null;
	}

}
