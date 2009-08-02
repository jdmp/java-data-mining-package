package org.jdmp.core.variable;

import java.lang.reflect.Constructor;

import org.jdmp.core.matrix.wrappers.MatrixToMatrixListWrapper;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public abstract class VariableFactory {

	public static final Variable fromMatrix(Matrix m) {
		return new DefaultVariable(new MatrixToMatrixListWrapper(m));
	}

	public static final Variable labeledVariable(String label) {
		return new DefaultVariable(label);
	}

	public static final Variable emptyVariable() {
		return new DefaultVariable();
	}

	public static final Variable singleMatrixVariable() {
		return new SingleMatrixVariable();
	}

	public static final Variable sharedInstance(String label) {
		try {
			Class<?> c = Class.forName("org.jdmp.jgroups.ReplicatedVariable");
			Constructor<?> con = c.getConstructor(String.class);
			return (Variable) con.newInstance(label);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

}
