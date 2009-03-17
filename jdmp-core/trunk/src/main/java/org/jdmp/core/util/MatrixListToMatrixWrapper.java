package org.jdmp.core.util;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.objectmatrix.AbstractDenseObjectMatrix2D;

public class MatrixListToMatrixWrapper extends AbstractDenseObjectMatrix2D implements
		Wrapper<ObservableList<Matrix>>, ListDataListener {
	private static final long serialVersionUID = 3023715319099124633L;

	private ObservableList<Matrix> matrixList = null;

	public MatrixListToMatrixWrapper(Variable variable) {
		this.matrixList = variable.getMatrixList();
		matrixList.addListDataListener(this);
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

	public Object getObject(int row, int column) throws MatrixException {
		return getObject((long) row, (long) column);
	}

	public Object getObject(long row, long column) throws MatrixException {
		long rows = 0;
		for (Matrix m : matrixList) {
			rows += m.getRowCount();
			if (rows > row) {
				long r = row - rows + m.getRowCount();
				if (column < m.getColumnCount()) {
					return m.getAsObject(r, column);
				} else {
					return 0.0;
				}
			}
		}
		return 0.0;
	}

	public void setObject(Object value, int row, int column) throws MatrixException {
		setObject(value, (long) row, (long) column);
	}

	public void setObject(Object value, long row, long column) throws MatrixException {
		long rows = 0;
		for (Matrix m : matrixList) {
			rows += m.getRowCount();
			if (rows > row) {
				long r = row - rows + m.getRowCount();
				if (column < m.getColumnCount()) {
					m.setAsObject(value, r, column);
					return;
				} else {
					return;
				}
			}
		}
	}

	public ObservableList<Matrix> getWrappedObject() {
		return matrixList;
	}

	public void setWrappedObject(ObservableList<Matrix> object) {
		this.matrixList = object;
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		notifyGUIObject();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		notifyGUIObject();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		notifyGUIObject();
	}

}
