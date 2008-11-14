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

package org.jdmp.core.executor;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.interpreter.AlgorithmCommand;
import org.jdmp.core.interpreter.Command;
import org.jdmp.core.interpreter.PrintCommand;
import org.jdmp.core.interpreter.Result;
import org.jdmp.core.interpreter.VariableCommand;
import org.jdmp.core.module.Module;
import org.jdmp.core.variable.DefaultVariable;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;

public class Executor {

	private Module module = null;

	public Executor(Module module) {
		this.module = module;
	}

	public Result execute(Command... commands) throws Exception {
		Result result = new Result();
		for (Command command : commands) {
			Result r = null;
			if (command instanceof AlgorithmCommand) {
				r = executeAlgorithmCommand((AlgorithmCommand) command);
			} else if (command instanceof VariableCommand) {
				r = executeVariableCommand((VariableCommand) command);
			} else if (command instanceof PrintCommand) {
				r = executePrintCommand((PrintCommand) command);
			}
			result.setText(result.getText() + r.getText() + "\n");
		}
		return result;
	}

	private Result executeVariableCommand(VariableCommand command) {
		String variableName = command.getVariable();
		Matrix matrix = command.getValue();

		Variable variable = module.getVariableList().get(variableName);
		if (variable == null) {
			variable = new DefaultVariable(variableName);
			module.getVariableList().put(variableName, variable);
		}
		variable.addMatrix(matrix);
		return new Result();
	}

	private Result executePrintCommand(PrintCommand command) {
		String variableName = command.getVariable();

		Variable variable = module.getVariableList().get(variableName);
		if (variable == null) {
			return new Result("variable not known");
		}
		Matrix m = variable.getMatrix();
		if (m == null) {
			return new Result("value not set");
		}
		return new Result(m.toString());
	}

	private Result executeAlgorithmCommand(AlgorithmCommand command) throws Exception {
		List<Variable> vall = new ArrayList<Variable>();
		List<String> sources = command.getSources();
		List<Variable> vsources = new ArrayList<Variable>();

		for (String s : sources) {
			Variable variable = module.getVariableList().get(s);
			if (variable == null) {
				return new Result("variable unknown: " + s);
			}
			vsources.add(variable);
			vall.add(variable);
		}

		List<String> targets = command.getTargets();
		List<Variable> vtargets = new ArrayList<Variable>();

		for (String s : targets) {
			Variable variable = module.getVariableList().get(s);
			if (variable == null) {
				variable = VariableFactory.labeledVariable(s);
				module.getVariableList().put(s, variable);
			}
			vtargets.add(variable);
			vall.add(variable);
		}

		String algorithmName = command.getAlgorithm();
		algorithmName = algorithmName.substring(0, 1).toUpperCase()
				+ algorithmName.substring(1).toLowerCase();
		Class<?> c = null;
		if (c == null) {
			try {
				c = Class.forName("org.jdmp.core.algorithm.basic." + algorithmName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Algorithm algorithm = null;
		try {
			Constructor<?> con = c.getConstructor(Variable.VARIABLEARRAY);
			Variable[] vs = new Variable[vall.size()];
			vs = vall.toArray(vs);
			algorithm = (Algorithm) con.newInstance((Object) vs);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("error");
		}

		if (algorithm == null) {
			return new Result("function not defined");
		}

		algorithm.calculate();

		return new Result();
	}

	public Result execute(List<Command> commands) throws Exception {
		Command[] c = new Command[commands.size()];
		return execute(commands.toArray(c));
	}

}
