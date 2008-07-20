package org.jdmp.core.matrix.system;

import java.util.Map;

import org.ujmp.core.Matrix;
import org.ujmp.core.matrices.stubs.AbstractMapMatrix;

public class MatrixSystemEnvironment extends AbstractMapMatrix {
	private static final long serialVersionUID = -8952532238520266448L;

	private static MatrixSystemEnvironment matrix = null;

	public static Matrix getInstance() {
		if (matrix == null) {
			matrix = new MatrixSystemEnvironment();
		}
		return matrix;
	}

	@Override
	public Map getMap() {
		return System.getenv();
	}

}
