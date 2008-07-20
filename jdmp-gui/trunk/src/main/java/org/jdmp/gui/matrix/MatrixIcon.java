package org.jdmp.gui.matrix;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

import org.ujmp.core.Matrix;

public class MatrixIcon extends MatrixRenderer implements Icon {
	private static final long serialVersionUID = -3351801048513775919L;

	private Matrix matrix = null;

	public MatrixIcon(Matrix matrix) {
		this.matrix = matrix;
	}

	public int getIconHeight() {
		return 16;
	}

	public int getIconWidth() {
		return 16;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.translate(x, y);
		setSize(getIconWidth(), getIconHeight());
		paintComponent(g);
		g.translate(-x, -y);
	}

	public Matrix getMatrix() {
		return matrix;
	}

	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}

}
