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

import org.jdmp.core.variable.DefaultVariableMap;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.jdmp.core.variable.VariableMap;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public abstract class AbstractSample extends DefaultMapMatrix<String, Variable> implements Sample {
	private static final long serialVersionUID = 1693258179407382419L;

	private final VariableMap variables = new DefaultVariableMap(this);

	public AbstractSample() {
		super();
		setId("Sample" + getCoreObjectId());
	}

	public final VariableMap getVariableMap2() {
		return variables;
	}

	public boolean isCorrect() {
		return getTargetClass() == getRecognizedClass();
	}

	public int getTargetClass() {
		return (int) get(TARGET).getLast().toRowVector(Ret.NEW).getCoordinatesOfMaximum()[ROW];
	}

	public int getRecognizedClass() {
		return (int) get(PREDICTED).getLast().toRowVector(Ret.NEW).getCoordinatesOfMaximum()[ROW];
	}

	public abstract Sample clone();

	public void setLabel(Object label) {
		setMatrix(Sample.LABEL, Matrix.Factory.linkToValue(label));
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(getClass().getSimpleName());
		s.append(" [ ");
		for (Object key : keySet()) {
			Variable v = get(key);
			s.append(key + "=");
			s.append(StringUtil.format(v.getLast()));
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
		Variable variable = get(id);
		if (variable == null) {
			variable = VariableFactory.labeledVariable(id);
			put(id, variable);
		}
		Matrix matrix = Matrix.Factory.linkToValue(object);
		variable.add(matrix);
	}

	public void setMatrix(String id, Matrix matrix) {
		Variable variable = get(id);
		if (variable == null) {
			variable = VariableFactory.labeledVariable(id);
			put(id, variable);
		}
		variable.add(matrix);
	}

	public final String getAllAsString(String variableKey) {
		Variable v = get(variableKey);
		if (v != null) {
			return StringUtil.getAllAsString(v);
		} else {
			return null;
		}
	}

	public final String getAsString(String variableKey) {
		return StringUtil.convert(getObject(variableKey));
	}

	public Object getObject(String variableKey) {
		return MathUtil.getObject(getMatrix(variableKey));
	}

	public Matrix getMatrix(String id) {
		Variable variable = get(id);
		if (variable == null) {
			return null;
		} else if (variable.isEmpty()) {
			return null;
		} else {
			return variable.getLast();
		}
	}

}
