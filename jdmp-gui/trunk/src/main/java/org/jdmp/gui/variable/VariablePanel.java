/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.gui.variable;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.jdmp.gui.matrix.MatrixListPanel;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.panels.AbstractPanel;
import org.ujmp.gui.panels.MatrixEditorPanel;
import org.ujmp.gui.panels.MatrixPaintPanel;
import org.ujmp.gui.plot.MatrixPlot;

public class VariablePanel extends AbstractPanel {
	private static final long serialVersionUID = -4809155917354071285L;

	private final MatrixGUIObject matrixObject = null;

	private MatrixListPanel matrixListPanel = null;

	private MatrixPaintPanel matrixPaintPanel = null;

	private MatrixEditorPanel matrixEditorPanel = null;

	private MatrixPlot variableChartPanel = null;

	private final JTabbedPane tabbedPane = new JTabbedPane();

	private final JSplitPane split1 = new JSplitPane();

	private final JSplitPane split2 = new JSplitPane();

	public VariablePanel(VariableGUIObject v) throws MatrixException {
		super(v);

		// matrixObject = (MatrixGUIObject) new
		// MatrixListToMatrixWrapper(v.getVariable())
		// .getGUIObject();

		matrixListPanel = new MatrixListPanel(v.getVariable());

		variableChartPanel = new MatrixPlot((MatrixGUIObject) v.getVariable().getAsMatrix()
				.getGUIObject(), true);

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

		matrixPaintPanel.setMatrix(matrixObject);
		matrixEditorPanel.setMatrix(matrixObject);

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
