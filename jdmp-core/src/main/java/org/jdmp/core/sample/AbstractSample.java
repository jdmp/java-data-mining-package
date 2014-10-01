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

import java.lang.reflect.Constructor;

import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.util.StringUtil;

public abstract class AbstractSample extends AbstractMapMatrix<String, Matrix> implements Sample {
	private static final long serialVersionUID = 1693258179407382419L;

	public AbstractSample() {
		super();
		setId("Sample" + getCoreObjectId());
	}

	public boolean isCorrect() {
		return getTargetClass() == getRecognizedClass();
	}

	public int getTargetClass() {
		return (int) getMatrix(TARGET).toRowVector(Ret.NEW).getCoordinatesOfMaximum()[ROW];
	}

	public int getRecognizedClass() {
		return (int) getMatrix(PREDICTED).toRowVector(Ret.NEW).getCoordinatesOfMaximum()[ROW];
	}

	public abstract Sample clone();

	public void setLabel(Object label) {
		setMatrix(Sample.LABEL, Matrix.Factory.linkToValue(label));
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(getClass().getSimpleName());
		s.append(" [ ");
		for (String key : keySet()) {
			Matrix v = getMatrix(key);
			s.append(key + "=");
			s.append(StringUtil.format(v));
			s.append(" ");
		}
		s.append("]");
		return s.toString();
	}

	public final GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Constructor<?> con = null;
				Class<?> c = Class.forName("org.jdmp.gui.sample.SampleGUIObject");
				con = c.getConstructor(new Class<?>[] { Sample.class });
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return guiObject;
	}

	public void setObject(String id, Object object) {
		Matrix m = get(id);
		if (m instanceof Variable) {
			Variable variable = (Variable) m;
			Matrix matrix = Matrix.Factory.linkToValue(object);
			variable.add(matrix);
		} else {
			put(id, Matrix.Factory.linkToValue(object));
		}
	}

	public Object getObject(String id) {
		Matrix m = get(id);
		if (m == null) {
			return null;
		} else if (m.isScalar()) {
			return m.getAsObject(0, 0);
		} else {
			return m;
		}
	}

	public void setMatrix(String id, Matrix matrix) {
		Matrix m = get(id);
		if (m instanceof Variable) {
			Variable variable = (Variable) m;
			variable.add(matrix);
		} else {
			put(id, matrix);
		}
	}

	public Matrix getMatrix(String id) {
		Matrix m = get(id);
		if (m instanceof Variable) {
			Variable variable = (Variable) m;
			if (variable.isEmpty()) {
				return null;
			} else {
				return variable.getLast();
			}
		} else {
			return m;
		}
	}

}
