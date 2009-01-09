package org.jdmp.core.util;

import org.jdmp.core.matrix.MatrixList;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.objectmatrix.AbstractDenseMatrix2D;

public class MatrixListToMatrixWrapper extends AbstractDenseMatrix2D implements Wrapper<MatrixList> {
	private static final long serialVersionUID = 3023715319099124633L;

	private MatrixList matrixList = null;

	public MatrixListToMatrixWrapper(Variable variable) {
		this.matrixList = variable.getMatrixList();
	}

	public long[] getSize() {
		long cols = 0;
		long rows = 0;
		for (Matrix m : matrixList) {
			cols = Math.max(m.getColumnCount(), cols);
			rows += m.getRowCount();
		}
		return new long[] { rows, cols };

	}

	public Object getObject(long row, long column) throws MatrixException {
		long rows = 0;
		for (Matrix m : matrixList) {
			rows += m.getRowCount();
			if (rows > row) {
				long r = row - rows + m.getRowCount();
				if (column < m.getColumnCount()) {
					return m.getObject(r, column);
				} else {
					return 0.0;
				}
			}
		}
		return 0.0;
	}

	public void setObject(Object value, long row, long column) throws MatrixException {
		long rows = 0;
		for (Matrix m : matrixList) {
			rows += m.getRowCount();
			if (rows > row) {
				long r = row - rows + m.getRowCount();
				if (column < m.getColumnCount()) {
					m.setObject(value, r, column);
					return;
				} else {
					return;
				}
			}
		}
	}

	public MatrixList getWrappedObject() {
		return matrixList;
	}

	public void setWrappedObject(MatrixList object) {
		this.matrixList = object;
	}

}
