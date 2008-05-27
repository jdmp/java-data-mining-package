package org.jdmp.core.matrix.system;

import java.util.Map;

import javax.swing.UIManager;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.stubs.AbstractMapMatrix;

public class MatrixUIDefaults extends AbstractMapMatrix {
	private static final long serialVersionUID = 6721967669100263805L;

	private static MatrixUIDefaults matrix = null;

	public static Matrix getInstance() {
		if (matrix == null) {
			matrix = new MatrixUIDefaults();
		}
		return matrix;
	}

	@Override
	public Map getMap() {
		return UIManager.getDefaults();
	}

}
