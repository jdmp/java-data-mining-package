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

package org.jdmp.liblinear;

import de.bwaldvogel.liblinear.Feature;
import de.bwaldvogel.liblinear.FeatureNode;

public class OneFeature implements Feature {

	private final int index;

	public OneFeature(final int index) {
		if (index < 0)
			throw new IllegalArgumentException("index must be >= 0");
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public double getValue() {
		return 1.0;
	}

	public void setValue(double value) {
		throw new RuntimeException("value cannot be changed");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		long temp;
		temp = Double.doubleToLongBits(1.0);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FeatureNode other = (FeatureNode) obj;
		if (index != other.index)
			return false;
		if (Double.doubleToLongBits(1.0) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FeatureNode(idx=" + index + ", value=1.0)";
	}

}