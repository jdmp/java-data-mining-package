package org.jdmp.gui.algorithm.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.HasAlgorithms;

public class RemoveAlgorithmAction extends AlgorithmListAction {
	private static final long serialVersionUID = -6954027136187768858L;

	private Algorithm algorithm = null;

	public RemoveAlgorithmAction(JComponent c, HasAlgorithms i, Algorithm a) {
		this(c, i);
		this.algorithm = a;
	}

	public RemoveAlgorithmAction(JComponent c, HasAlgorithms i) {
		super(c, i);
		putValue(Action.NAME, "Remove Algorithm...");
		putValue(Action.SHORT_DESCRIPTION, "Remove a Algorithm");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
	}

	public Object call() {
		if (algorithm != null) {
			getIAlgorithms().removeAlgorithm(algorithm);
			return algorithm;
		}
		return null;
	}

}
