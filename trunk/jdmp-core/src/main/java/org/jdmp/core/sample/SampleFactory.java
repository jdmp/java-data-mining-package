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

package org.jdmp.core.sample;

import java.util.Collection;

import org.ujmp.core.Matrix;

public abstract class SampleFactory {

	public static final Sample labeledSample(String label) {
		Sample s = new DefaultSample();
		s.setLabel(label);
		return s;
	}

	public static final Sample linkToMatrix(Matrix input) {
		Sample s = new DefaultSample();
		s.setMatrix(Sample.INPUT, input);
		s.setLabel(input.getLabel());
		return s;
	}

	public static final Sample classificationSample(Matrix input, Matrix target) {
		Sample s = new DefaultSample();
		s.setMatrix(Sample.INPUT, input);
		s.setMatrix(Sample.TARGET, target);
		return s;
	}

	public static final Sample emptySample() {
		return new DefaultSample();
	}

	public static final RelationalSample relationalSample(Collection<?> items) {
		RelationalSample s = new RelationalSample();
		for (Object o : items) {
			s.addObject(o);
		}
		return s;
	}

	public static final RelationalSample relationalSample(String label) {
		RelationalSample s = new RelationalSample();
		s.setLabel(label);
		return s;
	}

	public static Sample clone(Sample s) {
		Sample ret = SampleFactory.emptySample();
		ret.setLabel(s.getLabel());
		ret.setDescription(s.getDescription());
		for (String k : s.keySet()) {
			Matrix v = s.get(k);
			if (v != null) {
				ret.put(k, v.clone());
			}
		}
		return ret;
	}

}
