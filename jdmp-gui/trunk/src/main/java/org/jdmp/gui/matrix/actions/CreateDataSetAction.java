package org.jdmp.gui.matrix.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.gui.matrix.MatrixGUIObject;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasMatrixList;

public class CreateDataSetAction extends MatrixAction {
	private static final long serialVersionUID = -2894363441883457747L;

	public CreateDataSetAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Create DataSet");
		putValue(Action.SHORT_DESCRIPTION,
				"creates a DataSet from this matrix with input and target output");
	}

	@Override
	public Object call() throws MatrixException {
		ClassificationDataSet ds = new ClassificationDataSet();
		ds.createFromMatrix(getMatrixObject().getMatrix());
		ds.showGUI();
		return ds;
	}

}
