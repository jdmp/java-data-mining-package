package org.jdmp.gui.algorithm.actions;

import javax.swing.JComponent;

import org.jdmp.core.algorithm.HasAlgorithms;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.ObjectAction;

public abstract class AlgorithmListAction extends ObjectAction {

	public AlgorithmListAction(JComponent c, HasAlgorithms iAlgorithms) {
		super(c, (GUIObject) iAlgorithms);
	}

	public HasAlgorithms getIAlgorithms() {
		return (HasAlgorithms) getObject();
	}

}
