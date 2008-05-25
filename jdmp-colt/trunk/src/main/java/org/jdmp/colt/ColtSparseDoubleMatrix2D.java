package org.jdmp.colt;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.DefaultAnnotation;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractSparseDoubleMatrix2D;
import org.jdmp.matrix.util.CoordinateSetToLongWrapper;

import cern.colt.matrix.impl.SparseDoubleMatrix2D;

public class ColtSparseDoubleMatrix2D extends AbstractSparseDoubleMatrix2D implements Wrapper<SparseDoubleMatrix2D> {
	private static final long serialVersionUID = -3223474248020842822L;

	private DefaultAnnotation annotation = null;

	private SparseDoubleMatrix2D matrix = null;

	public ColtSparseDoubleMatrix2D(long... size) {
		this.matrix = new SparseDoubleMatrix2D((int) size[ROW], (int) size[COLUMN]);
	}

	public ColtSparseDoubleMatrix2D(SparseDoubleMatrix2D m) {
		this.matrix = m;
	}

	public ColtSparseDoubleMatrix2D(Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getDouble(c), c);
		}
	}

	public double getDouble(long... coordinates) {
		return matrix.getQuick((int) coordinates[ROW], (int) coordinates[COLUMN]);
	}

	public long[] getSize() {
		return new long[] { matrix.rows(), matrix.columns() };
	}

	public void setDouble(double value, long... coordinates) {
		matrix.setQuick((int) coordinates[ROW], (int) coordinates[COLUMN], value);
	}

	public SparseDoubleMatrix2D getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(SparseDoubleMatrix2D object) {
		this.matrix = object;
	}

	public Object getMatrixAnnotation() {
		return annotation == null ? null : annotation.getMatrixAnnotation();
	}

	public void setMatrixAnnotation(Object value) {
		if (annotation == null) {
			annotation = new DefaultAnnotation();
		}
		annotation.setMatrixAnnotation(value);
	}

	public Object getAxisAnnotation(int axis, int positionOnAxis) {
		return annotation == null ? null : annotation.getAxisAnnotation(axis, positionOnAxis);
	}

	@Override
	public Iterable<long[]> availableCoordinates() {
		return new AvailableCoordinateIterable();
	}

	class AvailableCoordinateIterable implements Iterable<long[]> {

		public Iterator<long[]> iterator() {
			Set<Coordinates> cset = new HashSet<Coordinates>();
			for (long r = getRowCount() - 1; r >= 0; r--) {
				for (long c = getColumnCount() - 1; c >= 0; c--) {
					if (getDouble(r, c) != 0.0) {
						cset.add(new Coordinates(r, c));
					}
				}
			}
			return new CoordinateSetToLongWrapper(cset).iterator();
		}
	}

	public Object getAxisAnnotation(int axis) {
		return annotation == null ? null : annotation.getAxisAnnotation(axis);
	}

	public void setAxisAnnotation(int axis, int positionOnAxis, Object value) {
		if (annotation == null) {
			annotation = new DefaultAnnotation();
		}
		annotation.setAxisAnnotation(axis, positionOnAxis, value);
	}

	public void setAxisAnnotation(int axis, Object value) {
		if (annotation == null) {
			annotation = new DefaultAnnotation();
		}
		annotation.setAxisAnnotation(axis, value);
	}

	public final boolean contains(long... coordinates) {
		return getDouble(coordinates) != 0.0;
	}
}
