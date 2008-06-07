package org.jdmp.gui.module.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.gui.module.ModuleGUIObject;
import org.jdmp.matrix.Matrix;

public class AppendMatrixAction extends ModuleAction {
	private static final long serialVersionUID = -4511306901854037104L;

	private Matrix matrix = null;

	public AppendMatrixAction(JComponent c, ModuleGUIObject module, Matrix m) {
		super(c, module);
		this.matrix = m;

		putValue(Action.NAME, "Set Matrix");
		putValue(Action.SHORT_DESCRIPTION, "Sets the Matrix to a specified value");
	}

	public Object call() {
		// if (getModule() != null) {
		// getModule().appendMatrixForVariableReference(reference, matrix);
		// }
		return null;
	}

}
