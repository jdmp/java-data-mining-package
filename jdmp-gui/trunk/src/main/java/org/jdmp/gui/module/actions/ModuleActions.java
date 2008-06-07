package org.jdmp.gui.module.actions;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.jdmp.gui.actions.ExitAction;
import org.jdmp.gui.actions.ObjectActions;
import org.jdmp.gui.module.ModuleGUIObject;

public class ModuleActions extends ObjectActions {

	private static final long serialVersionUID = 4780484608838892213L;

	public ModuleActions(JComponent c, ModuleGUIObject i) {
		super(c, i);
		this.add(new JSeparator());
		this.add(new JSeparator());
		this.add(new JMenuItem(new ExitAction(c, i)));
	}

}
