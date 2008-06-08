package org.jdmp.gui.matrix;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class MatrixEditorPanel extends JPanel {
	private static final long serialVersionUID = 1466769193543607213L;

	MatrixTableEditorPanel editor = null;

	public MatrixEditorPanel(MatrixGUIObject m) {
		setLayout(new BorderLayout());
		editor = new MatrixTableEditorPanel(m);
		add(editor, BorderLayout.CENTER);
	}

	public void setMatrix(MatrixGUIObject matrix) {
		editor.setMatrix(matrix);
	}

}
