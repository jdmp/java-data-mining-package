package org.jdmp.gui.algorithm.actions;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenuItem;

import org.jdmp.core.algorithm.HasAlgorithms;

public class AlgorithmListActions extends ArrayList<JComponent> {
	private static final long serialVersionUID = -6581043219923491806L;

	public AlgorithmListActions(JComponent c, HasAlgorithms a) {
		this.add(new JMenuItem(new AddAlgorithmAction(c, a)));
	}
}
