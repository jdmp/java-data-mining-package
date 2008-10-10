package org.jdmp.core.executor;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.interpreter.AlgorithmCommand;
import org.jdmp.core.interpreter.Command;
import org.jdmp.core.interpreter.PrintCommand;
import org.jdmp.core.interpreter.Result;
import org.jdmp.core.interpreter.VariableCommand;
import org.jdmp.core.module.DefaultModule;
import org.jdmp.core.module.Module;
import org.jdmp.core.variable.DefaultVariable;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;

public class Executor {

	private Module module = null;

	public Executor(Module module) {
		this.module = module;
	}

	public Result execute(Command... commands) {
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

	private Result executeAlgorithmCommand(AlgorithmCommand command) {
		List<String> sources = command.getSources();
		List<Variable> vsources = new ArrayList<Variable>();

		for (String s : sources) {
			Variable variable = module.getVariableList().get(s);
			if (variable == null) {
				new Result("variable unknown: " + s);
			}
			vsources.add(variable);
		}

		List<String> targets = command.getTargets();
		List<Variable> vtargets = new ArrayList<Variable>();

		for (String s : targets) {
			Variable variable = module.getVariableList().get(s);
			if (variable == null) {
				new Result("variable unknown: " + s);
			}
			vtargets.add(variable);
		}

		String algorithmName = command.getAlgorithm();
		// Algorithm algorithm = module.getAlgorithmList().get(algorithmName);

		return new Result("function not defined");
	}

	public Result execute(List<Command> commands) {
		Command[] c = new Command[commands.size()];
		return execute(commands.toArray(c));
	}

	public static void main(String[] args) throws Exception {
		Module m = new DefaultModule();
		System.out.println(m.execute("a=5"));
		System.out.println(m.execute("a"));
		System.out.println(m.execute("b=3"));
		System.out.println(m.execute("b"));
		System.out.println(m.execute("c=plus(a,b)"));
		System.out.println(m.execute("c"));
	}

}
