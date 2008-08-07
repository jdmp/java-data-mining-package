package org.jdmp.gui.matrix.actions;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.jdmp.gui.actions.ExitAction;
import org.jdmp.gui.actions.ObjectActions;
import org.jdmp.gui.matrix.MatrixGUIObject;
import org.ujmp.core.interfaces.HasMatrixList;

public class MatrixActions extends ObjectActions {
	private static final long serialVersionUID = -8960033736161810590L;

	public MatrixActions(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m);
		this.add(new JMenuItem(new AddMissingValuesAction(c, m, v)));
		this.add(new JMenuItem(new ReplaceByMeanAction(c, m, v)));
		this.add(new JMenuItem(new ReplaceByNearestNeighborAction(c, m, v)));
		this.add(new JMenuItem(new FillGaussianAction(c, m, v)));
		this.add(new JMenuItem(new FillUniformAction(c, m, v)));
		this.add(new JMenuItem(new FillWithValueAction(c, m, v)));
		this.add(new JMenuItem(new CopyMatrixAction(c, m, v)));
		this.add(new JMenuItem(new LinkMatrixAction(c, m, v)));
		this.add(new JMenuItem(new TransposeMatrixAction(c, m, v)));
		this.add(new JMenuItem(new RescaleMatrixAction(c, m, v)));
		this.add(new JMenuItem(new FadeInAction(c, m, v)));
		this.add(new JMenuItem(new FadeOutAction(c, m, v)));
		this.add(new JMenuItem(new CreateDataSetAction(c, m, v)));
		this.add(new JMenuItem(new MinAction(c, m, v)));
		this.add(new JMenuItem(new MaxAction(c, m, v)));
		this.add(new JMenuItem(new SumAction(c, m, v)));
		this.add(new JMenuItem(new MeanAction(c, m, v)));
		this.add(new JMenuItem(new VarianceAction(c, m, v)));
		this.add(new JMenuItem(new StandardDeviationAction(c, m, v)));
		this.add(new JMenuItem(new CenterAction(c, m, v)));
		this.add(new JMenuItem(new StandardizeAction(c, m, v)));

		this.add(new JSeparator());
		this.add(new JMenuItem(new ExitAction(c, m)));
	}

}
