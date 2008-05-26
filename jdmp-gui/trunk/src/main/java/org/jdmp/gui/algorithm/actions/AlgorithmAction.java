package org.jdmp.gui.algorithm.actions;

import javax.swing.JComponent;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.gui.actions.ObjectAction;

public abstract class AlgorithmAction extends ObjectAction {

	public AlgorithmAction(JComponent c, Algorithm a) {
		super(c, a);
	}

	public Algorithm getAlgorithm() {
		return (Algorithm) getObject();
	}
}
