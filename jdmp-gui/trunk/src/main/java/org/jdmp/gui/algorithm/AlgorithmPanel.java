package org.jdmp.gui.algorithm;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jdmp.core.algorithm.HasAlgorithms;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.gui.util.ObjectListPanel;
import org.ujmp.gui.util.AbstractPanel;

public class AlgorithmPanel extends AbstractPanel {
	private static final long serialVersionUID = -1135182245042463188L;

	private final JSplitPane splitPane = new JSplitPane();

	private final JPanel leftPanel = new JPanel();

	private final JPanel rightPanel = new JPanel();

	public AlgorithmPanel(AlgorithmGUIObject a) {
		super(a);

		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBorder(BorderFactory.createTitledBorder("Algorithms and Variables"));

		rightPanel.setLayout(new GridLayout(2, 1));
		rightPanel.add(new ObjectListPanel((HasVariables) a.getAlgorithm()));
		rightPanel.add(new ObjectListPanel((HasAlgorithms) a.getAlgorithm()));

		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rightPanel);
		add(splitPane, BorderLayout.CENTER);
	}

}
