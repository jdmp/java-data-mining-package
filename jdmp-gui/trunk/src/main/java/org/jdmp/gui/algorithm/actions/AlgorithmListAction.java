package org.jdmp.gui.algorithm.actions;

import javax.swing.JComponent;

import org.jdmp.core.algorithm.HasAlgorithms;
import org.jdmp.gui.actions.ObjectAction;
import org.ujmp.core.interfaces.GUIObject;

public abstract class AlgorithmListAction extends ObjectAction {

	public AlgorithmListAction(JComponent c, HasAlgorithms iAlgorithms) {
		super(c, (GUIObject) iAlgorithms);
	}

	public HasAlgorithms getIAlgorithms() {
		return (HasAlgorithms) getObject();
	}

}
