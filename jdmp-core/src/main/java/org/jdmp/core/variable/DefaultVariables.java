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

package org.jdmp.core.variable;

import org.ujmp.core.Matrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public class DefaultVariables extends AbstractVariables {
	private static final long serialVersionUID = -863822584023419772L;

	public final Matrix getMatrix(String variableKey) {
		Variable v = get(variableKey);
		if (v != null) {
			return v.getLatestMatrix();
		} else {
			return null;
		}
	}

	public void setMatrix(String variableKey, Matrix matrix) {
		Variable v = get(variableKey);
		if (v == null) {
			v = VariableFactory.labeledVariable(variableKey.toString());
			put(variableKey, v);
		}
		v.addInnerMatrix(matrix);
	}

	public final void setObject(String variableKey, Object value) {
		setMatrix(variableKey, MathUtil.getMatrix(value));
	}

	@Override
	public Object getObject(String variableKey) {
		return MathUtil.getObject(getMatrix(variableKey));
	}

	public final String getAllAsString(String variableKey) {
		Variable v = get(variableKey);
		if (v != null) {
			return StringUtil.getAllAsString(v.getMatrixList().toCollection());
		} else {
			return null;
		}
	}

}
