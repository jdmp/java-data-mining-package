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

import org.jdmp.core.util.DefaultObservableMap;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;

public class DefaultSample extends AbstractSample {
	private static final long serialVersionUID = -3649758882404748630L;

	private final DefaultObservableMap<Variable> variableMap = new DefaultObservableMap<Variable>();

	public DefaultSample() {
		super();
	}

	public DefaultObservableMap<Variable> getVariables() {
		return variableMap;
	}

	@Override
	public final DefaultSample clone() {
		DefaultSample s = null;
		try {
			s = this.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Object k : getVariables().keySet()) {
			Variable v = getVariables().get(k);
			s.getVariables().put(k, v.clone());
		}
		return s;
	}

	public void setMatrix(Object variableKey, Matrix matrix) {
		Variable v = getVariables().get(variableKey);
		if (v == null) {
			v = VariableFactory.labeledVariable(variableKey.toString());
			getVariables().put(variableKey, v);
		}
		v.addMatrix(matrix);
	}

	@Override
	public void clear() {
		variableMap.clear();
	}

}
