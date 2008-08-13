package org.jdmp.gui.variable.actions;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.jdmp.gui.variable.VariableGUIObject;
import org.ujmp.gui.actions.ExitAction;

public class VariableActions extends ArrayList<JComponent> {
	private static final long serialVersionUID = 8087631357836093790L;

	public VariableActions(JComponent c, VariableGUIObject v) {
		this.add(new JMenuItem(new FillUniformAction(c, v)));
		this.add(new JMenuItem(new FillGaussianAction(c, v)));
		this.add(new JMenuItem(new FillWithValueAction(c, v)));
		this.add(new JMenuItem(new DivideAction(c, v)));

		this.add(new JSeparator());
		this.add(new JMenuItem(new ExitAction(c, v)));
	}

}
