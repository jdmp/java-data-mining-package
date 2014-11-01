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

import java.util.HashMap;
import java.util.Map;

public class GeneralDensityEstimator extends AbstractDensityEstimator {

	private final Map<Object, Double> counts;
	private double sumOfCounts;

	public GeneralDensityEstimator() {
		counts = new HashMap<Object, Double>();
	}

	public void addValue(final Object value, final double weight) {
		Double count = counts.get(value);
		if (count == null) {
			count = weight;
			counts.put(value, count);
		}
		sumOfCounts += weight;
	}

	public void addValue(final double value, final double weight) {
		addValue(Double.valueOf(value), weight);
	}

	public double getProbability(final double value) {
		return getProbability(Double.valueOf(value));
	}

	public double getProbability(final Object value) {
		if (sumOfCounts == 0.0) {
			return MINPROBABILITY;
		} else {
			Double count = counts.get(value);
			if (count == 0) {
				return MINPROBABILITY;
			} else {
				double probability = count / sumOfCounts;
				return probability < MINPROBABILITY ? MINPROBABILITY : probability;
			}
		}
	}

	public void removeValue(final Object value, final double weight) {
		Double count = counts.get(value);
		if (count != null) {
			count -= weight;
			counts.put(value, count);
		}
		sumOfCounts -= weight;
	}

	public void removeValue(double value, double weight) {
		removeValue(Double.valueOf(value), weight);
	}

}
