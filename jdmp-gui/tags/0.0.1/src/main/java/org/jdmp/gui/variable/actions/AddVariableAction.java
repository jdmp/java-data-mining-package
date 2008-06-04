package org.jdmp.gui.variable.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.variable.DefaultVariable;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.core.variable.Variable;

public class AddVariableAction extends VariableListAction {
	private static final long serialVersionUID = -8909819040875168634L;

	private Variable variable = null;

	public AddVariableAction(JComponent c, HasVariables iv, Variable v) {
		this(c, iv);
		variable = v;
	}

	public AddVariableAction(JComponent c, HasVariables iv) {
		super(c, iv);
		putValue(Action.NAME, "Add Variable");
		putValue(Action.SHORT_DESCRIPTION, "Add Variable");
	}

	public Object call() {
		if (variable == null)
			variable = new DefaultVariable();
		getIVariables().addVariable(variable);
		return variable;
	}
}
