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

package org.jdmp.core.algorithm.compression;

import org.jdmp.core.dataset.DataSet;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public class PCA extends AbstractCompressor {
	private static final long serialVersionUID = 4559351198783166902L;

	private Matrix mean = null;
	private Matrix std = null;
	private Matrix u = null;
	private int dimensions = -1;

	public PCA(int dimensions) {
		this.dimensions = dimensions;
	}

	public void reset() throws Exception {
		mean = null;
		std = null;
		u = null;
	}

	public void train(DataSet dataSet) throws Exception {
		Matrix x = dataSet.getInputMatrix();
		mean = x.mean(Ret.NEW, ROW, true);
		Matrix meanMatrix = Matrix.Factory.repmat(Ret.LINK, mean, x.getRowCount(), 1);
		x = x.minus(meanMatrix);
		mean.showGUI();
		x.showGUI();
		std = x.std(Ret.NEW, ROW, true, true);
		std.showGUI();
		Matrix stdMatrix = Matrix.Factory.repmat(Ret.LINK, std, x.getRowCount(), 1);
		x = x.divide(stdMatrix);
		x.showGUI();
		Matrix xtx = x.transpose().mtimes(x);
		Matrix[] svd = xtx.svd();
		u = svd[0];
	}

	public Matrix compress(Matrix input) throws Exception {
		return input.mtimes(u);
	}

	public Matrix decompress(Matrix input) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
