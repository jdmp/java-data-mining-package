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
			return vcommand;
		} else {
			// cannot understand this command
			System.out.println("unparseable command: " + input);
			return new CommentCommand("unparseable command: " + input);
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
