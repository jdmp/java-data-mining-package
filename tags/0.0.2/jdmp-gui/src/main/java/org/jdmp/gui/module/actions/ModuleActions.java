package org.jdmp.gui.module.actions;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.jdmp.gui.module.ModuleGUIObject;
import org.ujmp.gui.actions.ExitAction;

public class ModuleActions extends ArrayList<JComponent> {

	private static final long serialVersionUID = 4780484608838892213L;

	public ModuleActions(JComponent c, ModuleGUIObject i) {
		this.add(new JSeparator());
		this.add(new JSeparator());
		this.add(new JMenuItem(new ExitAction(c, i)));
	}

}
