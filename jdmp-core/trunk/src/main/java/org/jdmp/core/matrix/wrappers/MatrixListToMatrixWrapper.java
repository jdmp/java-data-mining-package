package org.jdmp.core.matrix.wrappers;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixList;
import org.jdmp.matrix.interfaces.HasLabel;
import org.jdmp.matrix.interfaces.HasMatrixList;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class MatrixListToMatrixWrapper extends AbstractDenseDoubleMatrix2D implements Wrapper<MatrixList> {
	private static final long serialVersionUID = 3023715319099124633L;

	private MatrixList matrixList = null;

	private HasLabel labelObject = null;

	public MatrixListToMatrixWrapper(HasMatrixList hasMatrixList) {
		this(hasMatrixList.getMatrixList());
		this.labelObject = (HasLabel) hasMatrixList;
	}

	private MatrixListToMatrixWrapper(MatrixList matrixList) {
		this.matrixList = matrixList;
	}

	public long[] getSize() {
		if (matrixList.isEmpty()) {
			return new long[] { 0, 0 };
		} else {
			return new long[] { matrixList.size(), matrixList.getLast().getColumnCount() };
		}
	}

	public double getDouble(long... coordinates) throws MatrixException {
		Matrix m = matrixList.get((int) coordinates[ROW]);
		if (m != null) {
			return m.getDouble(0, coordinates[COLUMN]);
		}
		return 0.0;
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		Matrix m = matrixList.get((int) coordinates[ROW]);
		if (m != null) {
			m.setDouble(value, 0, coordinates[COLUMN]);
		}
	}

	public MatrixList getWrappedObject() {
		return matrixList;
	}

	public void setWrappedObject(MatrixList object) {
		this.matrixList = object;
	}

	public Object getMatrixAnnotation() {
		return labelObject.getLabel();
	}

	public void setMatrixAnnotation(Object value) {
		if (value == null) {
			labelObject.setLabel(null);
		} else {
			labelObject.setLabel(value.toString());
		}
	}

}
