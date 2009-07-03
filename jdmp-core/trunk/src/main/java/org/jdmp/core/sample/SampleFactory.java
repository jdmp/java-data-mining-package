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

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasDataMap;
import org.ujmp.core.util.MathUtil;

public abstract class SampleFactory {

	public static Sample EMPTYSAMPLE = new DefaultSample();

	public static final Sample createFromObject(Object o) {
		if (o instanceof Map) {
			return linkToMap((Map) o);
		} else if (o instanceof HasDataMap) {
			return linkToMap(((HasDataMap) o).getDataMap());
		} else if (o instanceof Matrix) {
			return linkToMatrix((Matrix) o);
		} else {
			return linkToMatrix(MathUtil.getMatrix(o));
		}
	}

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
		Sample s = new MapSample(map);
		return s;
	}

	public static Sample linkToFile(FileFormat fileFormat, File file, Object... parameters)
			throws MatrixException, IOException {
		Matrix map = MatrixFactory.linkToFile(FileFormat.FILE, file, fileFormat);
		return linkToMap((Map) map);
	}

	public static Sample linkToFile(File file) throws MatrixException, IOException {
		return linkToFile(FileFormat.guess(file), file);
	}

	public static final ClassificationSample classificationSample(Matrix input, Matrix target) {
		ClassificationSample s = new ClassificationSample();
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
