package org.jdmp.core.module;

import java.util.List;

import javax.swing.event.EventListenerList;

import org.jdmp.core.executor.Executor;
import org.jdmp.core.interpreter.Command;
import org.jdmp.core.interpreter.Interpreter;
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

	public final EventListenerList getListenerList() {
		return null;
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

	@Override
	public void execute(Command... commands) {
		getExecutor().execute(commands);
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
	public void execute(List<Command> commands) {
		getExecutor().execute(commands);
	}

	@Override
	public void execute(String script) {
		List<Command> commands = getInterpreter().parseScript(script);
		execute(commands);
	}
}
