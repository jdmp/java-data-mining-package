package org.jdmp.gui.dataset;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jdmp.core.sample.HasSamples;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.gui.matrix.MatrixListPanel;
import org.jdmp.gui.util.ObjectListPanel;
import org.ujmp.gui.util.AbstractPanel;

public class DataSetPanel extends AbstractPanel {
	private static final long serialVersionUID = -742923218356821961L;

	private final JSplitPane splitPane = new JSplitPane();

	private final JPanel leftPanel = new JPanel();

	private final JPanel rightPanel = new JPanel();

	public DataSetPanel(DataSetGUIObject ds) {
		super(ds);

		rightPanel.setLayout(new GridLayout(3, 1));
		rightPanel.add(new MatrixListPanel(ds.getDataSet()));
		rightPanel.add(new ObjectListPanel((HasVariables) ds.getDataSet()));
		rightPanel.add(new ObjectListPanel((HasSamples) ds.getDataSet()));

		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rightPanel);
		add(splitPane, BorderLayout.CENTER);
	}

}
