package org.jdmp.matrix.interfaces;

import javax.swing.ListSelectionModel;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixList;

public interface HasMatrixList {

	public MatrixList getMatrixList();

	public int getMatrixCount();

	public void fireValueChanged(Matrix m);

	public ListSelectionModel getRowSelectionModel();

	public ListSelectionModel getColumnSelectionModel();

	public Matrix getMatrix(int index);

}
