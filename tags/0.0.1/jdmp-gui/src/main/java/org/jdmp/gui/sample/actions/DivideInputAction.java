package org.jdmp.gui.sample.actions;

import java.util.logging.Level;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.jdmp.core.sample.WeightedSample;

public class DivideInputAction extends SampleAction {
	private static final long serialVersionUID = -3916356419093949650L;

	public DivideInputAction(JComponent c, WeightedSample p) {
		super(c, p);
		putValue(Action.NAME, "Divide input...");
		putValue(Action.SHORT_DESCRIPTION, "Divides the input of this sample");
	}

	public Object call() {
		String s = JOptionPane.showInputDialog("Divide Input by:");
		try {
			double d = Double.parseDouble(s);
			((WeightedSample) getSample()).divideInput(d);
		} catch (Exception e) {
			logger.log(Level.INFO, "error dividing input", e);
		}
		return null;
	}

}
