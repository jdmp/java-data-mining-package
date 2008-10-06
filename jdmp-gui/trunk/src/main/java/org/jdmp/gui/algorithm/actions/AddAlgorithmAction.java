package org.jdmp.gui.algorithm.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.HasAlgorithms;

public class AddAlgorithmAction extends AlgorithmListAction {
	private static final long serialVersionUID = 5647028718703205997L;

	private Algorithm algorithm = null;

	public AddAlgorithmAction(JComponent c, HasAlgorithms i, Algorithm a) {
		this(c, a);
		algorithm = a;
	}

	public AddAlgorithmAction(JComponent c, HasAlgorithms i) {
		super(c, i);
		putValue(Action.NAME, "Add Algorithm...");
		putValue(Action.SHORT_DESCRIPTION, "Add a new Algorithm");
	}

	@Override
	public Object call() {
		if (algorithm == null) {

			// int index = -1;
			// while (index == -1) {
			// index = JOptionPane.showOptionDialog(null, "Chose the type you
			// want to add:", "Select type",
			// JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
			// Algorithm.AlgorithmType.values(),
			// null);
			// }
			// AlgorithmType type = Algorithm.AlgorithmType.values()[index];
			// algorithm = Algorithm.newInstance(type);
		}

		// getIAlgorithms().getAlgorithmList().add(algorithm);
		return algorithm;
	}
}
