/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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
import java.util.Map;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;

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

	public static final Sample linkToMap(Map<?, ?> map) {
		Sample s = new DefaultSample();
		for (Object k : map.keySet()) {
			Object v = map.get(k);
			Matrix m = MatrixFactory.linkToValue(v);
			s.setMatrix(k, m);
		}
		return s;
	}

	public static final ClassificationSample classificationSample(Matrix input, Matrix target) {
		ClassificationSample s = new ClassificationSample();
		s.setMatrix(Sample.INPUT, input);
		s.setMatrix(Sample.TARGET, target);
		return s;
	}

	public static final Sample emptySample() {
		Sample s = new DefaultSample();
		return s;
	}

	public static final RelationalSample relationalSample(Collection<?> items) {
		RelationalSample s = new RelationalSample();
		for (Object o : items) {
			s.addObject(o);
		}
		return s;
	}

	public static final ClassificationSample classificationSample() {
		ClassificationSample s = new ClassificationSample();
		return s;
	}

	public static final ClassificationSample classificationSample(String label) {
		ClassificationSample s = new ClassificationSample();
		s.setLabel(label);
		return s;
	}

	public static final RelationalSample relationalSample(String label) {
		RelationalSample s = new RelationalSample();
		s.setLabel(label);
		return s;
	}

}
