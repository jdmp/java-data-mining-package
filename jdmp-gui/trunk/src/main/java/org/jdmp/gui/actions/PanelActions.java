package org.jdmp.gui.actions;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenuItem;

import org.jdmp.core.util.AbstractGUIObject;

public class PanelActions extends ArrayList<JComponent> {
	private static final long serialVersionUID = 4269896034756524809L;

	public PanelActions(JComponent c, AbstractGUIObject o) {
		this.add(new JMenuItem(new PrintAction(c, o)));
	}

}
