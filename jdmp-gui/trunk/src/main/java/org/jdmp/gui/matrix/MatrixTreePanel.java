package org.jdmp.gui.matrix;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;

public class MatrixTreePanel extends JPanel {
	private static final long serialVersionUID = 6571680515596275337L;

	public MatrixTreePanel(MatrixGUIObject m) {
		JTree tree = new JTree((TreeModel) m.getMatrix());
		setLayout(new BorderLayout());
		add(new JScrollPane(tree), BorderLayout.CENTER);
	}

}
