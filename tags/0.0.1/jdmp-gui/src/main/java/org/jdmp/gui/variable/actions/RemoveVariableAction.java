package org.jdmp.gui.variable.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.variable.HasVariables;
import org.jdmp.core.variable.Variable;

public class RemoveVariableAction extends VariableListAction {
	private static final long serialVersionUID = 5848730507868012386L;

	private Variable variable = null;

	public RemoveVariableAction(JComponent c, HasVariables i, Variable v) {
		this(c, i);
		this.variable = v;
	}

	public RemoveVariableAction(JComponent c, HasVariables i) {
		super(c, i);
		putValue(Action.NAME, "Remove Variable...");
		putValue(Action.SHORT_DESCRIPTION, "Remove a Variable");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
	}

	public Object call() {
		if (variable != null) {
			getIVariables().removeVariable(variable);
			return variable;
		}
		return null;
	}

}
