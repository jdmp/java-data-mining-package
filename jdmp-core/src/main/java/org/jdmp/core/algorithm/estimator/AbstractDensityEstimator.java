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

import org.ujmp.core.util.MathUtil;

public abstract class AbstractDensityEstimator implements DensityEstimator {

	public static final double MINPROBABILITY = 1e-15;

	public final void removeValue(Object value) {
		removeValue(value, 1.0);
	}

	public final void addValue(final double value) {
		addValue(value, 1.0);
	}

	public final void addValue(final Object value) {
		addValue(value, 1.0);
	}

	public final void removeValue(final double value) {
		removeValue(value, 1.0);
	}

	public void addValue(Object value, double weight) {
		addValue(MathUtil.getDouble(value), weight);
	}

	public void removeValue(Object value, double weight) {
		removeValue(MathUtil.getDouble(value), weight);
	}

	public double getProbability(Object value) {
		return getProbability(MathUtil.getDouble(value));
	}

	public final void addValue(boolean value, double weight) {
		addValue(value ? 1.0 : 0.0, weight);
	}

	public final void addValue(boolean value) {
		addValue(value ? 1.0 : 0.0, 1.0);
	}

	public final void removeValue(boolean value, double weight) {
		removeValue(value ? 1.0 : 0.0, weight);
	}

	public final void removeValue(boolean value) {
		removeValue(value ? 1.0 : 0.0, 1.0);
	}

	public final double getProbability(boolean value) {
		return getProbability(value ? 1.0 : 0.0);
	}

}
