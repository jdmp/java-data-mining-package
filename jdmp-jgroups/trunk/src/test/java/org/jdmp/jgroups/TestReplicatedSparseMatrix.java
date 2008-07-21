package org.jdmp.jgroups;

import org.jdmp.jgroups.ReplicatedSparseMatrix;
import org.ujmp.core.AbstractMatrixTest;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

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
