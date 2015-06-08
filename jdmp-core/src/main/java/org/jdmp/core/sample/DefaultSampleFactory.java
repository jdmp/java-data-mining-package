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

public class DefaultSampleFactory extends AbstractSampleFactory {

	public final Sample labeledSample(String label) {
		Sample s = new DefaultSample();
		s.setLabel(label);
		return s;
	}

	public final Sample linkToMatrix(Matrix input) {
		Sample s = new DefaultSample();
		s.put(Sample.INPUT, input);
		s.setLabel(input.getLabel());
		return s;
	}

	public final Sample classificationSample(Matrix input, Matrix target) {
		Sample s = new DefaultSample();
		s.put(Sample.INPUT, input);
		s.put(Sample.TARGET, target);
		return s;
	}

	public final Sample classificationSample(Matrix input, int classId, int classCount) {
		Sample s = new DefaultSample();
		s.put(Sample.INPUT, input);
		Matrix target = Matrix.Factory.zeros(1, classCount);
		target.setAsDouble(1, 0, classId);
		s.put(Sample.TARGET, target);
		return s;
	}

	public final Sample classificationSample(Matrix input, boolean target) {
		return classificationSample(input, target ? 1 : 0, 2);
	}

	public final Sample emptySample() {
		return new DefaultSample();
	}

	public final RelationalSample relationalSample(Collection<?> items) {
		RelationalSample s = new RelationalSample();
		for (Object o : items) {
			s.addObject(o);
		}
		return s;
	}

	public final RelationalSample relationalSample(String label) {
		RelationalSample s = new RelationalSample();
		s.setLabel(label);
		return s;
	}

	public Sample clone(Sample s) {
		Sample ret = Sample.Factory.emptySample();
		ret.setLabel(s.getLabel());
		ret.setDescription(s.getDescription());
		for (String k : s.keySet()) {
			Matrix v = s.getAsMatrix(k);
			if (v != null) {
				ret.put(k, v.clone());
			}
		}
		return ret;
	}

}
