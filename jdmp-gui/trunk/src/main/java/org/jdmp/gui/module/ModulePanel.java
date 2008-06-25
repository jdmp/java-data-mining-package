package org.jdmp.gui.module;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jdmp.core.algorithm.HasAlgorithms;
import org.jdmp.core.dataset.HasDataSets;
import org.jdmp.core.module.HasModuleList;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.gui.util.AbstractPanel;
import org.jdmp.gui.util.ObjectListPanel;

public class ModulePanel extends AbstractPanel {
	private static final long serialVersionUID = -6907850964484891091L;

	private JSplitPane splitPane = new JSplitPane();

	private JPanel leftPanel = new JPanel();

	private JPanel rightPanel = new JPanel();

	public ModulePanel(ModuleGUIObject m) {
		super(m);

		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBorder(BorderFactory.createTitledBorder("Modules in Module"));

		rightPanel.setLayout(new GridLayout(4, 1));
		rightPanel.add(new ObjectListPanel((HasModuleList) m));
		rightPanel.add(new ObjectListPanel((HasVariables) m));
		rightPanel.add(new ObjectListPanel((HasAlgorithms) m));
		rightPanel.add(new ObjectListPanel((HasDataSets) m));

		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rightPanel);
		add(splitPane, BorderLayout.CENTER);
	}

}
