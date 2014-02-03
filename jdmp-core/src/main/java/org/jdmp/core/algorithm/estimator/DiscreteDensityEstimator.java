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

package org.jdmp.core.algorithm.estimator;

public class DiscreteDensityEstimator implements DensityEstimator {

	private double[] counts;

	private double sumOfCounts;

	public DiscreteDensityEstimator(int nmbVals, boolean laplace) {
		counts = new double[nmbVals];
		sumOfCounts = 0;
		if (laplace) {
			for (int i = 0; i < nmbVals; i++) {
				counts[i] = 1;
			}
			sumOfCounts = (double) nmbVals;
		}
	}

	public DiscreteDensityEstimator(int nmbVals, double correction) {
		counts = new double[nmbVals];
		sumOfCounts = 0;
		for (int i = 0; i < nmbVals; i++) {
			counts[i] = correction;
		}
		sumOfCounts = (double) nmbVals;
	}

	public void addValue(int val, double weight) {
		counts[val] += weight;
		sumOfCounts += weight;
	}

	public void addValue(int val) {
		counts[val]++;
		sumOfCounts++;
	}

	public double getProbability(int val) {
		if (sumOfCounts == 0) {
			return 0;
		}
		return counts[val] / sumOfCounts;
	}

}
