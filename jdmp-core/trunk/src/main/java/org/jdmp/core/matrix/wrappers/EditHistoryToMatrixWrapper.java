package org.jdmp.core.matrix.wrappers;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.interfaces.HasMatrixList;
import org.jdmp.matrix.interfaces.HasSourceMatrix;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.util.collections.MatrixList;

public class EditHistoryToMatrixWrapper implements Wrapper<HasSourceMatrix>, HasMatrixList {

	private HasSourceMatrix matrix = null;

	private ListSelectionModel rowSelection = new DefaultListSelectionModel();

	private ListSelectionModel columnSelection = new DefaultListSelectionModel();

	public EditHistoryToMatrixWrapper(HasSourceMatrix matrix) {
		this.matrix = matrix;
	}

	public void fireValueChanged(Matrix m) {
	}

	public ListSelectionModel getColumnSelectionModel() {
		return columnSelection;
	}

	public int getMatrixCount() {
		return matrix.getSourceMatrices().size();
	}

	public MatrixList getMatrixList() {
		return matrix.getSourceMatrices();
	}

	public ListSelectionModel getRowSelectionModel() {
		return rowSelection;
	}

	public Matrix getMatrix(int index) {
		return matrix.getSourceMatrices().get(index);
	}

	public HasSourceMatrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(HasSourceMatrix object) {
		this.matrix = object;
	}

}
