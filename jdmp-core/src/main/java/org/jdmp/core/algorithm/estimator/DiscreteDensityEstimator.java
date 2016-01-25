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

package org.jdmp.core.algorithm.estimator;

import java.util.Arrays;

public class DiscreteDensityEstimator extends AbstractDensityEstimator {

	private final double[] counts;
	private double sumOfCounts;

	public DiscreteDensityEstimator(final int numberOfBins) {
		this(numberOfBins, 1.0);
	}

	public DiscreteDensityEstimator(final int numberOfBins, final boolean useLaplaceCorrection) {
		this(numberOfBins, useLaplaceCorrection ? 1.0 : 0.0);
	}

	public DiscreteDensityEstimator(final int numberOfBins, final double correctionFactor) {
		counts = new double[numberOfBins];
		// avoid zero probabilities with pseudo counts
		sumOfCounts = numberOfBins * correctionFactor;
		Arrays.fill(counts, correctionFactor);
	}

	public void addValue(final double value, final double weight) {
		counts[(int) value] += weight;
		sumOfCounts += weight;
	}

	public double getProbability(final double value) {
		if (sumOfCounts == 0.0) {
			return MINPROBABILITY;
		} else {
			double probability = counts[(int) value] / sumOfCounts;
			return probability < MINPROBABILITY ? MINPROBABILITY : probability;
		}
	}

	public void removeValue(final double value, final double weight) {
		counts[(int) value] -= weight;
		sumOfCounts -= weight;
	}

}
