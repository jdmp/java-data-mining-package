package org.jdmp.matrix.implementations.misc;

import java.util.Iterator;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.DefaultMatrixList;
import org.jdmp.matrix.GenericMatrix;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixList;
import org.jdmp.matrix.interfaces.HasSourceMatrix;

public class ReshapedMatrix extends GenericMatrix implements HasSourceMatrix {
	private static final long serialVersionUID = -4298270756453090584L;

	private Matrix source = null;

	private long[] newSize = null;

	private long[] oldSize = null;

	public ReshapedMatrix(Matrix source, long... newSize) {
		this.source = source;
		this.newSize = newSize;
		this.oldSize = source.getSize();
	}

	public boolean contains(long... coordinates) {
		return false;
	}

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterable();
	}

	private class CoordinateIterable implements Iterable<long[]> {

		public Iterator<long[]> iterator() {
			return new CoordinateIterator();
		}

	}

	private class CoordinateIterator implements Iterator<long[]> {

		private Iterator<long[]> iterator = source.allCoordinates().iterator();

		public boolean hasNext() {
			return iterator.hasNext();
		}

		public long[] next() {
			return getNewCoordinates(iterator.next());
		}

		public void remove() {
		}

	}

	private long[] getOldCoordinates(long[] newCoordinates) {
		long[] oldCoordinates = Coordinates.copyOf(newCoordinates);
		long valueNumber = newCoordinates[ROW] * newSize[COLUMN] + newCoordinates[COLUMN];
		oldCoordinates[ROW] = valueNumber / oldSize[COLUMN];
		oldCoordinates[COLUMN] = valueNumber % oldSize[COLUMN];
		return oldCoordinates;
	}

	private long[] getNewCoordinates(long[] oldCoordinates) {
		long[] newCoordinates = Coordinates.copyOf(oldCoordinates);
		long valueNumber = oldCoordinates[ROW] * oldSize[COLUMN] + oldCoordinates[COLUMN];
		newCoordinates[ROW] = (valueNumber / newSize[COLUMN]);
		newCoordinates[COLUMN] = (valueNumber % newSize[COLUMN]);
		return newCoordinates;
	}

	public long[] getSize() {
		return newSize;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return source.getDouble(getOldCoordinates(coordinates));
	}

	public Object getObject(long... coordinates) throws MatrixException {
		return source.getObject(getOldCoordinates(coordinates));
	}

	@Override
	public long getValueCount() {
		return source.getValueCount();
	}

	@Override
	public boolean isReadOnly() {
		return source.isReadOnly();
	}

	public boolean isSparse() {
		return source.isSparse();
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		source.setDouble(value, getOldCoordinates(coordinates));
	}

	public void setObject(Object value, long... coordinates) throws MatrixException {
		source.setObject(value, getOldCoordinates(coordinates));
	}

	public MatrixList getSourceMatrices() {
		MatrixList matrices = new DefaultMatrixList();
		if (getSourceMatrix() instanceof HasSourceMatrix) {
			matrices.addAll(((HasSourceMatrix) getSourceMatrix()).getSourceMatrices());
		}
		matrices.add(getSourceMatrix());
		return matrices;
	}

	public Matrix getSourceMatrix() {
		return source;
	}

	public EntryType getEntryType() {
		return null;
	}

}
