package org.jdmp.core.matrix.system;

import java.util.Map;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.stubs.AbstractMapMatrix;

public class MatrixSystemProperties extends AbstractMapMatrix {
	private static final long serialVersionUID = -5746939082111495919L;

	private static MatrixSystemProperties matrix = null;

	public static Matrix getInstance() {
		if (matrix == null) {
			matrix = new MatrixSystemProperties();
		}
		return matrix;
	}

	public Object getMatrixAnnotation() {
		return "System Properties";
	}

	@Override
	public Map getMap() {
		return System.getProperties();
	}

}
