package org.jdmp.gui.algorithm.actions;

import javax.swing.JComponent;

import org.jdmp.gui.algorithm.AlgorithmGUIObject;
import org.ujmp.gui.actions.ObjectAction;

public abstract class AlgorithmAction extends ObjectAction {

	public AlgorithmAction(JComponent c, AlgorithmGUIObject a) {
		super(c, a);
	}

	public AlgorithmGUIObject getAlgorithm() {
		return (AlgorithmGUIObject) getObject();
	}
}
