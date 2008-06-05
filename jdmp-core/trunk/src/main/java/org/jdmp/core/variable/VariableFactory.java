package org.jdmp.core.variable;

import org.jdmp.core.matrix.wrappers.MatrixToMatrixListWrapper;
import org.jdmp.matrix.Matrix;

public abstract class VariableFactory {

	public static final Variable fromMatrix(Matrix m) {
		return new DefaultVariable(new MatrixToMatrixListWrapper(m));
	}
	
}
