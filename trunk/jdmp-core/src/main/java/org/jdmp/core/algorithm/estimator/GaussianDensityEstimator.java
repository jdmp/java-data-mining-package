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

import org.ujmp.core.util.MathUtil;

public class GaussianDensityEstimator extends AbstractDensityEstimator {

	private static final double MINPROBABILITY = 1e12;

	private double count = 0.0;
	private double runningMean = 0.0;
	private double runningSum = 0.0;
	private double sigma = 1.0;
	private boolean isSigmaUpToDate = false;

	public GaussianDensityEstimator() {
	}

	public void removeValue(final double x) {
		isSigmaUpToDate = false;
		if (count == 0) {
			throw new RuntimeException("density estimator is already empty");
		} else if (count == 1) {
			count = 0;
			runningMean = 0.0;
			runningSum = 0.0;
		} else {
			final double oldMean = (count * runningMean - x) / (count - 1);
			runningSum -= (x - runningMean) * (x - oldMean);
			runningMean = oldMean;
			count--;
		}
	}

	public void addValue(final double x) {
		isSigmaUpToDate = false;
		count++;
		if (count == 1) {
			runningMean = x;
		} else {
			final double newMean = runningMean + (x - runningMean) / count;
			runningSum += (x - runningMean) * (x - newMean);
			runningMean = newMean;
		}
	}

	public double getVariance() {
		if (count == 0 || runningSum == 0) {
			return 1.0;
		} else {
			return runningSum / (count - 1);
		}
	}

	public double getSigma() {
		if (!isSigmaUpToDate) {
			sigma = Math.sqrt(getVariance());
			isSigmaUpToDate = true;
		}
		return sigma;
	}

	public void addValue(final double x, final double weight) {
		if (weight == 1) {
			addValue(x);
		} else {
			isSigmaUpToDate = false;
			count += weight;
			if (Math.abs(count - weight) < MathUtil.EPSILON) {
				runningMean = x;
			} else {
				final double newMean = runningMean + (weight * x - runningMean) / count;
				runningSum += (weight * x - runningMean) * (weight * x - newMean);
				runningMean = newMean;
			}
		}
	}

	public void removeValue(final double x, final double weight) {
		if (weight == 1) {
			removeValue(x);
		} else {
			isSigmaUpToDate = false;
			if (count == 0) {
				throw new RuntimeException("density estimator is already empty");
			} else if (Math.abs(count - weight) < MathUtil.EPSILON) {
				count = 0;
				runningMean = 0.0;
				runningSum = 0.0;
			} else {
				final double oldMean = (count * runningMean - weight * x) / (count - weight);
				runningSum -= (weight * x - runningMean) * (weight * x - oldMean);
				runningMean = oldMean;
				count -= weight;
			}
		}
	}

	public double getProbability(final double value) {
		double probability = MathUtil.gauss(runningMean, getSigma(), value);
		return probability == 0.0 ? MINPROBABILITY : probability;
	}

	public double getMu() {
		return runningMean;
	}

	public String toString() {
		return "mu:" + getMu() + ",sigma:" + getSigma();
	}
}
