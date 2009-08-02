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

package org.jdmp.core.algorithm.clustering;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;

public abstract class AbstractClusterer extends AbstractAlgorithm implements Clusterer {

	public AbstractClusterer() {
		super();
	}

	public void predict(RegressionDataSet dataSet) throws Exception {
		for (Sample sample : dataSet.getSamples()) {
			predict(sample);
		}
	}

	public final void predict(Sample sample) throws Exception {
		Matrix output = predict(sample.getMatrix(INPUT), sample.getMatrix(WEIGHT));
		sample.setMatrix(PREDICTED, output);
		// List<Matrix> error = getOutputErrorAlgorithm().calculate(output,
		// sample.getTargetMatrix());
		// sample.setOutputErrorMatrix(error.get(0));
		// sample.setRMSEMatrix(MatrixFactory.linkToValue(error.get(0).getRMS()));
	}

}
