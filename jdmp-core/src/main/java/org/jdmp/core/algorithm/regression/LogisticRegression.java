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

package org.jdmp.core.algorithm.regression;

import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public class LogisticRegression extends LinearRegression {
	private static final long serialVersionUID = -9024531407583444066L;

	public LogisticRegression() {
		super();
	}

	public LogisticRegression(int numberOfPrincipalComponents) {
		super(numberOfPrincipalComponents);
	}

	public void predictOne(Sample sample) {
		Matrix input = sample.getAsMatrix(INPUT);
		// TODO: check
		super.predictOne(sample);
		Matrix result = sample.getAsMatrix(PREDICTED);
		result = result.logistic(Ret.NEW);
		double sum = result.getValueSum();
		if (sum > 0) {
			result = result.divide(sum);
		}
		sample.put(PREDICTED, result);
	}

}
