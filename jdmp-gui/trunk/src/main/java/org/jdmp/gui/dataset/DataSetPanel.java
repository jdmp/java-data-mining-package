package org.jdmp.gui.dataset;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jdmp.core.sample.HasSamples;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.gui.matrix.MatrixListPanel;
import org.jdmp.gui.util.AbstractPanel;
import org.jdmp.gui.util.ObjectListPanel;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class DataSetPanel extends AbstractPanel {
	private static final long serialVersionUID = -742923218356821961L;

	private JSplitPane splitPane = new JSplitPane();

	private JPanel leftPanel = new JPanel();

	private JPanel rightPanel = new JPanel();

	public DataSetPanel(DataSetGUIObject ds) {
		super(ds);

		rightPanel.setLayout(new GridLayout(3, 1));
		rightPanel.add(new MatrixListPanel((HasMatrixList) ds.getDataSet()));
		rightPanel.add(new ObjectListPanel((HasVariables) ds.getDataSet()));
		rightPanel.add(new ObjectListPanel((HasSamples) ds.getDataSet()));

		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rightPanel);
		add(splitPane, BorderLayout.CENTER);
	}

}
