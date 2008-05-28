package org.jdmp.jgroups;

import org.jdmp.jgroups.ReplicatedSparseMatrix;
import org.jdmp.matrix.AbstractMatrixTest;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;

public class TestReplicatedSparseMatrix extends AbstractMatrixTest {

	@Override
	public Matrix createMatrix(long... size) throws MatrixException {
		return new ReplicatedSparseMatrix(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws MatrixException {
		return new ReplicatedSparseMatrix(source);
	}

}
