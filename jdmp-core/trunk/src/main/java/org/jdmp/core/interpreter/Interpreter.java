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

package org.jdmp.core.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.FileFormat;

public class Interpreter {

	public Interpreter() {
	}

	private Command parseCommand(String input) {
		if (input.startsWith("#") || input.startsWith("//") || input.startsWith("%")) {
			// this is a comment
			return new CommentCommand(input);
		} else if (input.contains("=") && input.contains("(") && input.contains(")")) {
			// this is an algorithm command
			AlgorithmCommand acommand = new AlgorithmCommand();
			Pattern pattern = Pattern.compile("([^=]*)=([^\\(]*)\\(([^\\)]*)");
			Matcher matcher = pattern.matcher(input);
			matcher.find();
			String targetString = matcher.group(1);
			String algorithm = matcher.group(2);
			String sourceString = matcher.group(3);
			acommand.setAlgorithm(algorithm);
			acommand.setOriginalText(input);

			String[] targets = targetString.replaceAll("[\\[\\]]", "").split(",");
			for (String target : targets) {
				acommand.addTarget(target);
			}

			String[] sources = sourceString.replaceAll("[\\[\\]]", "").split(",");
			for (String source : sources) {
				acommand.addSource(source);
			}

			return acommand;
		} else if (input.contains("=")) {
			// this is a variable command
			VariableCommand vcommand = new VariableCommand();
			Pattern pattern = Pattern.compile("([^=]*)=(.*)");
			Matcher matcher = pattern.matcher(input);
			matcher.find();
			String variable = matcher.group(1);
			String matrixString = matcher.group(2);
			Matrix matrix = MatrixFactory.importFromString(FileFormat.M, matrixString);
			vcommand.setVariable(variable);
			vcommand.setValue(matrix);
			vcommand.setOriginalText(input);
			return vcommand;
		} else {
			// print value of a variable
			PrintCommand pcommand = new PrintCommand();
			String variable = input;
			pcommand.setVariable(variable);
			pcommand.setOriginalText(input);
			return pcommand;
		}
	}

	public List<Command> parseScript(String input) {
		List<Command> commands = new ArrayList<Command>();
		String[] lines = input.split("\n");
		for (String line : lines) {
			Command command = parseCommand(line);
			commands.add(command);
		}
		return commands;
	}

}
