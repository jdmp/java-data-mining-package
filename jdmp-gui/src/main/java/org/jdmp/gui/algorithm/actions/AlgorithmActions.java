package org.jdmp.gui.algorithm.actions;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.gui.actions.ExitAction;
import org.jdmp.gui.actions.ObjectActions;

public class AlgorithmActions extends ObjectActions {
	private static final long serialVersionUID = -4508932056911168672L;

	public AlgorithmActions(JComponent c, Algorithm algorithm) {
		super(c, algorithm);
		add(new JMenuItem(new SetIntervalAction(c, algorithm)));
		add(new JMenuItem(new CalculateOnceAction(c, algorithm)));

		this.add(new JSeparator());
		this.add(new JMenuItem(new ExitAction(c, algorithm)));
	}
}
