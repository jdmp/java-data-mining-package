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

package org.jdmp.core.algorithm.regression;

import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;

public interface Regressor {

	public void trainAll(ListDataSet dataSet);

	public void reset();

	public void trainOne(Matrix input, Matrix sampleWeight, Matrix target);

	public void predictOne(Sample sample);

	public Matrix predictOne(Matrix input);

	public void trainOne(Matrix input, Matrix target);

	public void trainOne(Sample sample);

	public void predictAll(ListDataSet dataSet);

	public Regressor emptyCopy();

	public String getInputLabel();

	public String getTargetLabel();

	public int getClassCount(ListDataSet dataSet);

	public int getFeatureCount(ListDataSet dataSet);

	public boolean isDiscrete(ListDataSet dataSet);

}
