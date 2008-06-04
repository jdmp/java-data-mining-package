package org.jdmp.jexcelapi;

import java.io.IOException;

import org.jdmp.matrix.AbstractMatrixTest;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;

public class TestDenseExcelMatrix2D extends AbstractMatrixTest {

	@Override
	public Matrix createMatrix(long... size) throws MatrixException, IOException {
		return new DenseExcelMatrix2D(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws MatrixException, IOException {
		return new DenseExcelMatrix2D(source);
	}

}
