package org.jdmp.gui.matrix.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.matrix.MatrixGUIObject;
import org.jdmp.core.util.AbstractGUIObject;
import org.jdmp.gui.actions.ObjectAction;
import org.jdmp.matrix.Matrix;

public class ShowInFrameAction extends ObjectAction {
	private static final long serialVersionUID = -5025569936825456099L;

	public ShowInFrameAction(JComponent c, Matrix matrix) {
		this(c, (MatrixGUIObject) matrix.getGUIObject());
	}

	public ShowInFrameAction(JComponent c, AbstractGUIObject object) {
		super(c, object);
		putValue(Action.NAME, object.getLabel());
		putValue(Action.SHORT_DESCRIPTION, "Show " + object.getLabel() + " in a new Window");
	}

	@Override
	public Object call() {
		getObject().showGUI();
		return null;
	}

}
