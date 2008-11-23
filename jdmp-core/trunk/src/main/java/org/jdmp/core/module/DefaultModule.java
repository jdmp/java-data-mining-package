/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.jdmp.core.module;

import org.jdmp.core.executor.Executor;
import org.jdmp.core.grammar.jdmp.interpreter.Interpreter;
import org.jdmp.core.interpreter.Result;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;

public class DefaultModule extends AbstractModule {
	private static final long serialVersionUID = 4932183248766877797L;

	private String label = "";

	private String description = "";

	private Executor executor = null;

	private Interpreter interpreter = null;

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public final void setLabel(String label) {
		this.label = label;
	}

	@Override
	public Matrix getMatrix(Object variableKey) {
		Variable v = getVariableList().get(variableKey);
		if (v == null) {
			return null;
		} else {
			return v.getMatrix();
		}
	}

	@Override
	public void setMatrix(Object variableKey, Matrix matrix) {
		Variable v = getVariableList().get(variableKey);
		if (v != null) {
			v.addMatrix(matrix);
		}
	}

	public Executor getExecutor() {
		if (executor == null) {
			executor = new Executor(this);
		}
		return executor;
	}

	public Interpreter getInterpreter() {
		if (interpreter == null) {
			interpreter = new Interpreter();
		}
		return interpreter;
	}

	@Override
	public Result execute(String script) throws Exception {
		return getInterpreter().execute(script);
	}
}
