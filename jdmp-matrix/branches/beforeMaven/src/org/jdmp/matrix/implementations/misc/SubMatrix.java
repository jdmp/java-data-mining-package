package org.jdmp.matrix.implementations.misc;

import org.jdmp.matrix.CoordinateIterator2D;
import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.DefaultMatrixList;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixList;
import org.jdmp.matrix.interfaces.HasSourceMatrix;
import org.jdmp.matrix.stubs.AbstractGenericMatrix;

public class SubMatrix<A> extends AbstractGenericMatrix<A> implements HasSourceMatrix {
	private static final long serialVersionUID = 8230514498244193787L;

	private Matrix matrix = null;

	private long[] start = null;

	private long[] size = null;

	public SubMatrix(Matrix source, long startR, long startC, long sizeR, long sizeC) {
		this(source, new long[] { startR, startC }, new long[] { sizeR, sizeC });
	}

	public SubMatrix(Matrix source, long[] start, long[] size) {
		if (source == null || start == null || size == null || start.length != source.getSize().length
				|| size.length != source.getSize().length) {
			throw (new IllegalArgumentException("wrong parameters"));
		}
		this.matrix = source;
		this.start = Coordinates.copyOf(start);
		this.size = Coordinates.copyOf(size);
	}

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public long[] getSize() {
		return size;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		long[] c = Coordinates.plus(coordinates, start);
		return matrix.getDouble(c);
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		long[] c = Coordinates.plus(coordinates, start);
		matrix.setDouble(value, c);
	}

	public Object getMatrixAnnotation() {
		return matrix.getMatrixAnnotation();

	}

	public void setMatrixAnnotation(Object value) {
		matrix.setMatrixAnnotation(value);
	}

	public A getObject(long... coordinates) throws MatrixException {
		return (A) matrix.getObject(Coordinates.plus(coordinates, start));
	}

	public void setObject(Object value, long... coordinates) throws MatrixException {
		matrix.setObject(Coordinates.plus(coordinates, start));
	}

	public final boolean contains(long... coordinates) {
		return Coordinates.isSmallerThan(coordinates, getSize());
	}

	public Object getAxisAnnotation(int axis, int positionOnAxis) {
		int newPos = (int) (positionOnAxis + start[axis]);
		return matrix.getAxisAnnotation(axis, newPos);
	}

	public void setAxisAnnotation(int axis, int positionOnAxis, Object value) {
		int newPos = (int) (positionOnAxis + start[axis]);
		matrix.setAxisAnnotation(axis, newPos, value);
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

	public boolean isSparse() {
		return matrix.isSparse();
	}

	public EntryType getEntryType() {
		return matrix.getEntryType();
	}

}
