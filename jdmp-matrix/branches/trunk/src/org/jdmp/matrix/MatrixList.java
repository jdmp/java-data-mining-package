package org.jdmp.matrix;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;

public abstract class MatrixList implements Iterable<Matrix> {

	protected static final Logger logger = Logger.getLogger(MatrixList.class.getName());

	private ListSelectionModel rowSelectionModel = null;

	private ListSelectionModel columnSelectionModel = null;

	public abstract int getMaxCount();

	public abstract void setMaxCount(int maxCount);

	public abstract boolean addAll(Collection<? extends Matrix> c);

	public abstract boolean addAll(int index, Collection<? extends Matrix> c);

	public synchronized final Matrix getFirst() {
		if (isEmpty()) {
			return null;
		}
    	return get(0);
	}

	public final synchronized Matrix getLast() {
		if (isEmpty()) {
			return null;
		}
		return get(size() - 1);
	}

	public abstract Matrix get(int i);

	public abstract boolean isEmpty();

	public abstract int size();

	public final double[][] getTrace(int i) {
		try {
			int hsize = size();
			double[][] ret = new double[hsize][2];
			for (int a = 0; a < hsize; a++) {
				Matrix m = get(a);
				ret[a][0] = 0.0;
				ret[a][1] = m.getDouble(i % m.getRowCount(), i / m.getRowCount());
			}
			return ret;
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not return trace " + i);
			return null;
		}
	}

	public final double getMaxTime() {
		Matrix m = getLast();
		return (m == null) ? 0.0 : 0.0;
	}

	public final double getMinTime() {
		Matrix m = getFirst();
		return (m == null) ? 0.0 : 0.0;
	}

	public final double getLength() {
		return getMaxTime() - getMinTime();
	}

	public final long getTraceCount() {
		Matrix m = getFirst();
		return (m == null) ? 0 : m.getColumnCount() * m.getRowCount();
	}

	public ListSelectionModel getRowSelectionModel() {
		if (rowSelectionModel == null) {
			rowSelectionModel = new DefaultListSelectionModel();
		}
		return rowSelectionModel;
	}

	public ListSelectionModel getColumnSelectionModel() {
		if (columnSelectionModel == null) {
			columnSelectionModel = new DefaultListSelectionModel();
		}
		return columnSelectionModel;
	}

	public abstract boolean add(Matrix matrix);

	public final void addAll(MatrixList matrices) {
		for (Matrix m : matrices) {
			add(m);
		}
	}

	public abstract void clear();

	public abstract Matrix set(int index, Matrix m);

	public abstract int indexOf(Object o);

}
