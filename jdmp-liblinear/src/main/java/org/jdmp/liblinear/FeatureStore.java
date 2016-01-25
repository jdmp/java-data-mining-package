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

package org.jdmp.liblinear;

import java.util.HashMap;
import java.util.Map;

import de.bwaldvogel.liblinear.Feature;
import de.bwaldvogel.liblinear.FeatureNode;

public class FeatureStore {

	private final Map<Integer, Map<Double, Feature>> map = new HashMap<Integer, Map<Double, Feature>>(65536);

	public Feature get(final Integer index, final Double value) {
		Map<Double, Feature> features = map.get(index);
		if (features == null) {
			features = new HashMap<Double, Feature>();
			map.put(index, features);
		}
		Feature feature = features.get(value);
		if (feature == null) {
			if (value == 1.0) {
				feature = new OneFeature(index);
			} else if (value == -1) {
				feature = new MinusOneFeature(index);
			} else if (value == 0) {
				feature = new ZeroFeature(index);
			} else {
				feature = new FeatureNode(index, value);
			}
			features.put(value, feature);
		}
		return feature;
	}

}
