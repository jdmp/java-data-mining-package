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

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.variable.DefaultVariableMap;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableMap;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.StringUtil;

public abstract class AbstractSample extends AbstractCoreObject implements Sample {
	private static final long serialVersionUID = 1693258179407382419L;

	private final VariableMap variables = new DefaultVariableMap();

	public AbstractSample() {
		super();
	}

	public final VariableMap getVariableMap() {
		return variables;
	}

	public Matrix getMatrix(String variableKey) {
		return getVariableMap().getMatrix(variableKey);
	}

	public void setMatrix(String variableKey, Matrix matrix) {
		getVariableMap().setMatrix(variableKey, matrix);
	}

	public boolean isCorrect() {
		return getTargetClass() == getRecognizedClass();
	}

	public int getTargetClass() {
		return (int) getVariableMap().getMatrix(TARGET).getCoordinatesOfMaximum()[COLUMN];
	}

	public int getRecognizedClass() {
		return (int) getVariableMap().getMatrix(PREDICTED).getCoordinatesOfMaximum()[COLUMN];
	}

	public abstract Sample clone();

	public final String getId() {
		String id = getVariableMap().getAsString(Sample.ID);
		if (id == null) {
			id = "Sample" + getCoreObjectId();
			setId(id);
		}
		return id;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(getClass().getSimpleName());
		s.append(" [ ");
		for (Object key : getVariableMap().keySet()) {
			Variable v = getVariableMap().get(key);
			s.append(key + "=");
			s.append(StringUtil.format(v.getLast()));
			s.append(" ");
		}
		s.append("]");
		return s.toString();
	}

	public final void setId(String id) {
		getVariableMap().setObject(Sample.ID, id);
	}

	public final String getDescription() {
		return getVariableMap().getAsString(Sample.DESCRIPTION);
	}

	public final void setDescription(String description) {
		getVariableMap().setObject(Sample.DESCRIPTION, description);
	}

	public final String getLabel() {
		return getVariableMap().getAsString(Sample.LABEL);
	}

	public final void setLabelObject(Object label) {
		getVariableMap().setObject(Sample.LABEL, label);
	}

	public final Object getLabelObject() {
		return getVariableMap().getAsObject(Sample.LABEL);
	}

	public final void setLabel(String label) {
		getVariableMap().setObject(Sample.LABEL, label);
	}

	public final void clear() {
		if (variables != null) {
			variables.clear();
		}
	}

}
