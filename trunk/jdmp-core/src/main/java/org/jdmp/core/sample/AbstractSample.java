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

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.variable.DefaultVariables;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.Variables;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.StringUtil;

public abstract class AbstractSample extends AbstractCoreObject implements Sample {
	private static final long serialVersionUID = 1693258179407382419L;

	private Variables variables = null;

	public AbstractSample() {
		super();
	}

	public final Variables getVariables() {
		if (variables == null) {
			variables = new DefaultVariables();
		}
		return variables;
	}

	public final void setVariables(Variables variables) {
		this.variables = variables;
	}

	public boolean isCorrect() throws MatrixException {
		return getTargetClass() == getRecognizedClass();
	}

	public int getTargetClass() throws MatrixException {
		return (int) getVariables().getMatrix(TARGET).getCoordinatesOfMaximum()[COLUMN];
	}

	public int getRecognizedClass() throws MatrixException {
		return (int) getVariables().getMatrix(PREDICTED).getCoordinatesOfMaximum()[COLUMN];
	}

	public abstract Sample clone();

	public final String getId() {
		String id = getVariables().getAsString(Sample.ID);
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
		for (Object key : getVariables().keySet()) {
			Variable v = getVariables().get(key);
			s.append(key + "=");
			s.append(StringUtil.format(v.getMatrix()));
			s.append(" ");
		}
		s.append("]");
		return s.toString();
	}

	public final void setId(String id) {
		getVariables().setObject(Sample.ID, id);
	}

	public final String getDescription() {
		return getVariables().getAsString(Sample.DESCRIPTION);
	}

	public final void setDescription(String description) {
		getVariables().setObject(Sample.DESCRIPTION, description);
	}

	public final String getLabel() {
		return getVariables().getAsString(Sample.LABEL);
	}

	public final void setLabel(String label) {
		getVariables().setObject(Sample.LABEL, label);
	}

	public final void clear() {
		if (variables != null) {
			variables.clear();
		}
	}

}
