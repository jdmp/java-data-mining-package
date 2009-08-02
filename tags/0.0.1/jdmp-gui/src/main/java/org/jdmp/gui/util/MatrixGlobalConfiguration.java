package org.jdmp.gui.util;

import java.util.Map;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.stubs.AbstractMapMatrix;

public class MatrixGlobalConfiguration extends AbstractMapMatrix {
	private static final long serialVersionUID = 5766713621201901985L;

	private static MatrixGlobalConfiguration matrix = null;

	public static Matrix getInstance() {
		if (matrix == null) {
			matrix = new MatrixGlobalConfiguration();
		}
		return matrix;
	}

	@Override
	public Map getMap() {
		return GlobalConfiguration.getInstance();
	}

}
