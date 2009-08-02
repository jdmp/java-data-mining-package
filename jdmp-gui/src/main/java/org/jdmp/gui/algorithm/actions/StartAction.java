package org.jdmp.gui.algorithm.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.gui.algorithm.AlgorithmGUIObject;

public class StartAction extends AlgorithmAction {
	private static final long serialVersionUID = -6119686184444719226L;

	public StartAction(JComponent c, AlgorithmGUIObject w) {
		super(c, w);
		putValue(Action.NAME, "Start");
		putValue(Action.SHORT_DESCRIPTION, "Executes this algorithm repeatedly");
	}

	public Object call() {
		getAlgorithm().getAlgorithm().start();
		return null;
	}

}
