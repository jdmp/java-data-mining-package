package org.jdmp.gui.variable;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.jdmp.core.matrix.wrappers.MatrixListToMatrixWrapper;
import org.jdmp.gui.matrix.MatrixEditorPanel;
import org.jdmp.gui.matrix.MatrixGUIObject;
import org.jdmp.gui.matrix.MatrixGraphPanel;
import org.jdmp.gui.matrix.MatrixListPanel;
import org.jdmp.gui.matrix.MatrixPaintPanel;
import org.jdmp.gui.util.AbstractPanel;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class VariablePanel extends AbstractPanel {
	private static final long serialVersionUID = -4809155917354071285L;

	private MatrixGUIObject matrixObject = null;

	private MatrixListPanel matrixListPanel = null;

	private MatrixGraphPanel matrixGraphPanel = null;

	private MatrixPaintPanel matrixPaintPanel = null;

	private MatrixEditorPanel matrixEditorPanel = null;

	private VariableChartPanel variableChartPanel = null;

	private JTabbedPane tabbedPane = new JTabbedPane();

	private JSplitPane split1 = new JSplitPane();

	private JSplitPane split2 = new JSplitPane();

	public VariablePanel(VariableGUIObject v) throws MatrixException {
		super(v);

		matrixObject = (MatrixGUIObject) new MatrixListToMatrixWrapper(v.getVariable())
				.getGUIObject();

		matrixListPanel = new MatrixListPanel((HasMatrixList) v.getVariable());

		variableChartPanel = new VariableChartPanel(v, true);

		matrixPaintPanel = new MatrixPaintPanel(matrixListPanel.getSelectedMatrix(), false);
		matrixEditorPanel = new MatrixEditorPanel(matrixListPanel.getSelectedMatrix());

		// matrixListPanel.addListSelectionListener(this);

		split1.setLeftComponent(split2);
		split1.setRightComponent(matrixListPanel);

		split1.setDividerLocation(800);

		split2.setOrientation(JSplitPane.VERTICAL_SPLIT);

		split2.setTopComponent(variableChartPanel);
		split2.setBottomComponent(tabbedPane);

		split2.setDividerLocation(200);

		tabbedPane.add("2D Visualization", matrixPaintPanel);
		tabbedPane.add("Matrix Editor", matrixEditorPanel);

		matrixGraphPanel = new MatrixGraphPanel(matrixListPanel.getSelectedMatrix());
		tabbedPane.add("Matrix Graph", matrixGraphPanel);

		matrixPaintPanel.setMatrix(matrixObject);
		matrixEditorPanel.setMatrix(matrixObject);
		matrixGraphPanel.setMatrix(matrixObject);

		add(split1, BorderLayout.CENTER);

	}

	// public void valueChanged(ListSelectionEvent e) {
	// // Ignore extra messages.
	// if (e.getValueIsAdjusting()) {
	// return;
	// }
	//
	// ListSelectionModel lsm = (ListSelectionModel) e.getSource();
	// if (lsm.isSelectionEmpty()) {
	// matrixGraphPanel.setMatrix(null);
	// matrixPaintPanel.setMatrix(null);
	// matrixEditorPanel.setMatrix(null);
	// } else {
	// matrixGraphPanel.setMatrix(matrixListPanel.getSelectedMatrix());
	// matrixPaintPanel.setMatrix(matrixListPanel.getSelectedMatrix());
	// matrixEditorPanel.setMatrix(matrixListPanel.getSelectedMatrix());
	// }
	// }
}
