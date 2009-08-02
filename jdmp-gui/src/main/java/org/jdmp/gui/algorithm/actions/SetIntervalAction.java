package org.jdmp.gui.algorithm.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.jdmp.gui.algorithm.AlgorithmGUIObject;

public class SetIntervalAction extends AlgorithmAction {
	private static final long serialVersionUID = -6991280154534572009L;

	public SetIntervalAction(JComponent c, AlgorithmGUIObject w) {
		super(c, w);
		putValue(Action.NAME, "Set Interval...");
		putValue(Action.SHORT_DESCRIPTION, "Defines the pause between two calculations");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_I,
				InputEvent.ALT_DOWN_MASK));
	}

	public Object call() {
		String s = JOptionPane.showInputDialog("Enter the new Interval for this Algorithm:",
				getAlgorithm().getAlgorithm().getInterval());
		int interval = getAlgorithm().getAlgorithm().getInterval();
		try {
			interval = Integer.parseInt(s);
		} catch (Exception ex) {
		}
		getAlgorithm().getAlgorithm().setInterval(interval);
		return null;
	}

}
