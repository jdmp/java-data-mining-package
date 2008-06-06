package org.jdmp.gui.sample;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jdmp.core.variable.HasVariables;
import org.jdmp.gui.util.AbstractPanel;
import org.jdmp.gui.util.ObjectListPanel;

public class SamplePanel extends AbstractPanel {
	private static final long serialVersionUID = -3851417196195726237L;

	private JSplitPane splitPane = new JSplitPane();

	private JPanel leftPanel = new JPanel();

	private JPanel rightPanel = new JPanel();

	public SamplePanel(SampleGUIObject s) {
		super(s);

		rightPanel.setLayout(new GridLayout(1, 1));
		rightPanel.add(new ObjectListPanel((HasVariables) s.getSample()));

		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rightPanel);
		add(splitPane, BorderLayout.CENTER);
	}

}
