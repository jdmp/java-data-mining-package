package org.jdmp.matrix.calculation.basic;

import java.util.Iterator;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Transpose extends DoubleCalculation {
	private static final long serialVersionUID = -2749226948849267413L;

	private int swap1 = ROW;

	private int swap2 = COLUMN;

	public Transpose(Matrix m) {
		super(m);
	}

	public Transpose(Matrix m, int swap1, int swap2) {
		super(m);
		this.swap1 = swap1;
		this.swap2 = swap2;
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return getSource().getDouble(Coordinates.transpose(coordinates, swap1, swap2));
	}

	public long[] getSize() {
		return Coordinates.transpose(getSource().getSize(), swap1, swap2);
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		getSource().setDouble(value, Coordinates.transpose(coordinates, swap1, swap2));
	}

	public boolean contains(long... coordinates) {
		return getSource().contains(Coordinates.transpose(coordinates, swap1, swap2));
	}

	public boolean isSparse() {
		return getSource().isSparse();
	}

	public long getValueCount() {
		return getSource().getValueCount();
	}

	public Iterable<long[]> availableCoordinates() {
		return new Iterable<long[]>() {

			public Iterator<long[]> iterator() {
				return new TransposedIterator();
			}
		};
	}

	class TransposedIterator implements Iterator<long[]> {
		private Iterator<long[]> iterator = getSource().availableCoordinates().iterator();

		public boolean hasNext() {
			return iterator.hasNext();
		}

		public long[] next() {
			return Coordinates.transpose(iterator.next(), swap1, swap2);
		}

		public void remove() {
		}
	}

	public static Matrix calc(Matrix m) throws MatrixException {
		Matrix ret = null;
		if (m.isSparse()) {
			ret = Matrix.sparse(Coordinates.transpose(m.getSize()));
		} else {
			ret = Matrix.zeros(Coordinates.transpose(m.getSize()));
		}
		for (long[] c : m.availableCoordinates()) {
			ret.setDouble(m.getDouble(c), Coordinates.transpose(c));
		}
		return ret;
	}
}
