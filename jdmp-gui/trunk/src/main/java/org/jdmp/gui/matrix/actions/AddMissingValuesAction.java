package org.jdmp.gui.matrix.actions;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class AddMissingValuesAction extends MatrixAction {
	private static final long serialVersionUID = -7585669703654474086L;

	private double percentMissing = 0.0;

	public AddMissingValuesAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Add missing values...");
		putValue(Action.SHORT_DESCRIPTION, "replaces a chosen percentage of the values with NaN");
	}

	@Override
	public Object call() throws MatrixException {
		while (!(percentMissing > 0.0 && percentMissing < 1.0)) {
			String s = JOptionPane.showInputDialog("Percent missing:", "");
			try {
				percentMissing = Double.parseDouble(s);
			} catch (Exception ex) {
			}
		}
		getMatrixObject().getMatrix().addMissing(Ret.ORIG, ALL, percentMissing);
		return getMatrixObject();
	}
}
