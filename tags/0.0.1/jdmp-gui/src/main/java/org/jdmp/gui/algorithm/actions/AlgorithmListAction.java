package org.jdmp.gui.algorithm.actions;

import javax.swing.JComponent;

import org.jdmp.core.algorithm.HasAlgorithms;
import org.jdmp.core.util.AbstractGUIObject;
import org.jdmp.gui.actions.ObjectAction;

public abstract class AlgorithmListAction extends ObjectAction {

	public AlgorithmListAction(JComponent c, HasAlgorithms iAlgorithms) {
		super(c, (AbstractGUIObject) iAlgorithms);
	}

	public HasAlgorithms getIAlgorithms() {
		return (HasAlgorithms) getObject();
	}

}
