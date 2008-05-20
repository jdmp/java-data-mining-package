package org.jdmp.core.matrix.wrappers;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;

import org.jdmp.matrix.DefaultMatrixList;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixList;
import org.jdmp.matrix.interfaces.HasMatrix;
import org.jdmp.matrix.interfaces.HasMatrixList;
import org.jdmp.matrix.interfaces.Wrapper;

public class SingleMatrixWrapper implements Wrapper<Matrix>, HasMatrixList {

	private MatrixList matrixList = new DefaultMatrixList(1);

	private ListSelectionModel rowSelectionModel = null;

	private ListSelectionModel columnSelectionModel = null;

	public SingleMatrixWrapper(HasMatrix im) {
		matrixList.add(im.getMatrix());
	}

	public int getMatrixCount() {
		return 1;
	}

	public MatrixList getMatrixList() {
		return matrixList;
	}

	public void fireValueChanged(Matrix m) {
	}

	public ListSelectionModel getColumnSelectionModel() {
		if (columnSelectionModel == null) {
			columnSelectionModel = new DefaultListSelectionModel();
		}
		return columnSelectionModel;
	}

	public ListSelectionModel getRowSelectionModel() {
		if (rowSelectionModel == null) {
			rowSelectionModel = new DefaultListSelectionModel();
		}
		return rowSelectionModel;
	}

	public Matrix getMatrix(int index) {
		return matrixList.get(index);
	}

	public Matrix getWrappedObject() {
		return matrixList.get(0);
	}

	public void setWrappedObject(Matrix object) {
		matrixList.set(0, object);
	}
}