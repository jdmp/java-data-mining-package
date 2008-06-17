package org.jdmp.gui.matrix.panels;

import javax.swing.JPanel;

import org.jdmp.gui.matrix.MatrixGUIObject;

public class RPanel extends JPanel {
	private static final long serialVersionUID = -3779245352485347462L;

	private MatrixGUIObject matrix = null;

	public RPanel(MatrixGUIObject m) {
		this.matrix = m;
	}

}
