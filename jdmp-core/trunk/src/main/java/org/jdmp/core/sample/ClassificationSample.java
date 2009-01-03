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

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public class ClassificationSample extends DefaultSample {
	private static final long serialVersionUID = 7327527059314579381L;

	public ClassificationSample(String label) {
		super(label);
	}

	public ClassificationSample() {
		super();
	}

	public ClassificationSample(Matrix input, Matrix target) {
		super();
		setMatrix(INPUT, input);
		setMatrix(TARGET, target);
	}

	public boolean isCorrect() throws MatrixException {
		return getTargetClass() == getRecognizedClass();
	}

	public ClassificationSample clone() {
		ClassificationSample s = new ClassificationSample();
		s.setMatrix(INPUT, getMatrix(INPUT).clone());
		s.setMatrix(TARGET, getMatrix(TARGET).clone());
		return s;
	}

	public int getTargetClass() throws MatrixException {
		return (int) getMatrix(TARGET).getCoordinatesOfMaximum()[COLUMN];
	}

	public int getRecognizedClass() throws MatrixException {
		return (int) getMatrix(PREDICTED).getCoordinatesOfMaximum()[COLUMN];
	}

}
