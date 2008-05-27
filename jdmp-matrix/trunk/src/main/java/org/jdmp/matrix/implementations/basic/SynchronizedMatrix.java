package org.jdmp.matrix.implementations.basic;

import org.jdmp.matrix.DefaultMatrixList;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixList;
import org.jdmp.matrix.interfaces.HasSourceMatrix;
import org.jdmp.matrix.stubs.AbstractGenericMatrix;

public class SynchronizedMatrix<A> extends AbstractGenericMatrix<A> implements HasSourceMatrix {
	private static final long serialVersionUID = -4456493053286654056L;

	private Matrix matrix = null;

	public SynchronizedMatrix(Matrix source) {
		this.matrix = source;
		setAnnotation(source.getAnnotation());
	}

	public synchronized Iterable<long[]> allCoordinates() {
		return matrix.allCoordinates();
	}

	public synchronized long[] getSize() {
		return matrix.getSize();
	}

	public synchronized double getDouble(long... coordinates) throws MatrixException {
		return matrix.getDouble(coordinates);
	}

	@Override
	public synchronized long getValueCount() {
		return matrix.getValueCount();
	}

	public synchronized boolean isSparse() {
		return matrix.isSparse();
	}

	public synchronized void setDouble(double value, long... coordinates) throws MatrixException {
		matrix.setDouble(value, coordinates);
	}


	public synchronized A getObject(long... c) throws MatrixException {
		return (A) matrix.getObject(c);
	}

	public synchronized void setObject(Object value, long... c) throws MatrixException {
		matrix.setObject(value, c);
	}

	public synchronized boolean contains(long... coordinates) {
		return matrix.contains(coordinates);
	}

	@Override
	public synchronized boolean isReadOnly() {
		return matrix.isReadOnly();
	}

	public Matrix getSourceMatrix() {
		return matrix;
	}

	public MatrixList getSourceMatrices() {
		MatrixList matrices = new DefaultMatrixList();
		if (getSourceMatrix() instanceof HasSourceMatrix) {
			matrices.addAll(((HasSourceMatrix) getSourceMatrix()).getSourceMatrices());
		}
		matrices.add(getSourceMatrix());
		return matrices;
	}

	public EntryType getEntryType() {
		return matrix.getEntryType();
	}

}
