package org.jdmp.gui.matrix;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.tree.TreeModel;

import org.jdmp.gui.matrix.panels.MatlabPanel;
import org.jdmp.gui.matrix.panels.OctavePanel;
import org.jdmp.gui.matrix.panels.RPanel;
import org.jdmp.gui.matrix.plot.MatrixPlot;
import org.jdmp.gui.util.AbstractPanel;
import org.jdmp.gui.util.BufferedPanel;
import org.ujmp.core.util.Matlab;
import org.ujmp.core.util.Octave;
import org.ujmp.core.util.R;

public class MatrixPanel extends AbstractPanel {
	private static final long serialVersionUID = 3912987239953510584L;

	private JSplitPane splitPane1 = new JSplitPane();

	private JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

	private MatrixPaintPanel matrixPaintPanel = null;

	private MatrixPlot matrixPlot = null;

	public MatrixPanel(MatrixGUIObject m) {
		super(m);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setMinimumSize(new Dimension(10, 10));

		matrixPlot = new MatrixPlot(m, true);

		matrixPaintPanel = new MatrixPaintPanel(m, false);
		tabbedPane.add("2D Visualization", matrixPaintPanel);

		if (false) {
			try {
				// tabbedPane.add("3D Visualization", new Matrix3DPanel(m,
				// false));
			} catch (Throwable t) {
			}
		}

		if (m.getMatrix() instanceof TreeModel) {
			tabbedPane.add("Tree View", new MatrixTreePanel(m));
		}

		if (Matlab.isAvailable()) {
			MatlabPanel matlabPanel = new MatlabPanel(m);
			tabbedPane.add("Matlab", matlabPanel);
		}

		if (Octave.isAvailable()) {
			OctavePanel octavePanel = new OctavePanel(m);
			tabbedPane.add("Octave", octavePanel);
		}

		if (R.isAvailable()) {
			RPanel rPanel = new RPanel(m);
			tabbedPane.add("R", rPanel);
		}

		splitPane2.setTopComponent(new BufferedPanel(matrixPlot));
		splitPane2.setBottomComponent(tabbedPane);
		splitPane1.setLeftComponent(splitPane2);

		splitPane1.setRightComponent(new MatrixEditorPanel(m));

		add(splitPane1, BorderLayout.CENTER);

		splitPane1.setDividerLocation(600);
		splitPane2.setDividerLocation(200);
	}

}
