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

public interface DensityEstimator {

	public void addValue(Object value, double weight);

	public void addValue(Object value);

	public void addValue(double value, double weight);

	public void addValue(double value);

	public void addValue(boolean value, double weight);

	public void addValue(boolean value);

	public void removeValue(double value, double weight);

	public void removeValue(double value);

	public void removeValue(boolean value, double weight);

	public void removeValue(boolean value);

	public void removeValue(Object value, double weight);

	public void removeValue(Object value);

	public double getProbability(double value);

	public double getProbability(boolean value);

	public double getProbability(Object value);

}
