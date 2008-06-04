package org.jdmp.gui.variable.actions;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenuItem;

import org.jdmp.core.variable.HasVariables;

public class VariableListActions extends ArrayList<JComponent> {
	private static final long serialVersionUID = 7548868172222455405L;

	public VariableListActions(JComponent c, HasVariables v) {
		this.add(new JMenuItem(new AddVariableAction(c, v)));
	}

}
