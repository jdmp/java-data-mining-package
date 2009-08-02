package org.jdmp.gui.sample;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jdmp.gui.util.ObjectListPanel;
import org.ujmp.gui.util.AbstractPanel;

public class SamplePanel extends AbstractPanel {
	private static final long serialVersionUID = -3851417196195726237L;

	private final JSplitPane splitPane = new JSplitPane();

	private final JPanel leftPanel = new JPanel();

	private final JPanel rightPanel = new JPanel();

	public SamplePanel(SampleGUIObject s) {
		super(s);

		rightPanel.setLayout(new GridLayout(1, 1));
		rightPanel.add(new ObjectListPanel(s.getSample()));

		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rightPanel);
		add(splitPane, BorderLayout.CENTER);
	}

}
