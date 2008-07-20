package org.jdmp.gui.matrix.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasMatrixList;

public class CenterAction extends MatrixAction {
	private static final long serialVersionUID = 7686574264688126405L;

	public CenterAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Center");
		putValue(Action.SHORT_DESCRIPTION, "Rescales all entries to mean 0");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));
	}

	@Override
	public Object call() throws MatrixException {
		getMatrixObject().getMatrix().center(Ret.ORIG, getDimension(), true);
		return null;
	}

}
