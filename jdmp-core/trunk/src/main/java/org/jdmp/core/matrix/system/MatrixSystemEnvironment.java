package org.jdmp.core.matrix.system;

import java.util.Map;

import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;

public class MatrixSystemEnvironment extends AbstractMapMatrix {
	private static final long serialVersionUID = -8952532238520266448L;

	private static MatrixSystemEnvironment matrix = null;

	public static Matrix getInstance() {
		if (matrix == null) {
			matrix = new MatrixSystemEnvironment();
			matrix.setLabel("System Environment");
		}
		return matrix;
	}

	@Override
	public Map getMap() {
		return System.getenv();
	}

}
