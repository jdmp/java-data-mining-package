package org.jdmp.ehcache;

import java.io.IOException;
import java.util.Map;

import org.jdmp.matrix.CoordinateIterator2D;
import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.DefaultAnnotation;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.interfaces.Annotation;
import org.jdmp.matrix.stubs.AbstractSparseMatrix;
import org.jdmp.matrix.util.CoordinateSetToLongWrapper;

public class SparseEhcacheMatrix<A> extends AbstractSparseMatrix<A> {
	private static final long serialVersionUID = -7743149828558906127L;

	private Map<Coordinates, A> values = null;

	private Annotation annotation = null;

	private long[] size = null;

	private int maximumNumberOfEntries = -1;

	private A defaultValue = null;

	public SparseEhcacheMatrix(Matrix m) throws MatrixException {
		try {
			values = new EhcacheMap<Coordinates, A>("matrix" + System.nanoTime());
		} catch (IOException e) {
			throw new MatrixException(e);
		}
		this.size = Coordinates.copyOf(m.getSize());
		for (long[] c : m.allCoordinates()) {
			setDouble(m.getDouble(c), c);
		}
	}

	public SparseEhcacheMatrix(Matrix m, int maximumNumberOfEntries) throws MatrixException, IOException {
		values = new EhcacheMap<Coordinates, A>("matrix" + System.nanoTime());
		this.size = Coordinates.copyOf(m.getSize());
		this.maximumNumberOfEntries = maximumNumberOfEntries;
		for (long[] c : m.allCoordinates()) {
			setDouble(m.getDouble(c), c);
		}
	}

	public SparseEhcacheMatrix(long... size) throws MatrixException, IOException {
		values = new EhcacheMap<Coordinates, A>("matrix" + System.nanoTime());
		this.size = Coordinates.copyOf(size);
	}

	public long[] getSize() {
		return size;
	}

	public A getObject(long... coordinates) {
		A v = values.get(new Coordinates(coordinates));
		return v == null ? defaultValue : v;
	}

	@Override
	public long getValueCount() {
		return values.size();
	}

	public void setObject(Object value, long... coordinates) {
		while (maximumNumberOfEntries > 0 && values.size() > maximumNumberOfEntries) {
			values.remove(values.keySet().iterator().next());
		}
		if (Coordinates.isSmallerThan(coordinates, size)) {
			values.put(new Coordinates(coordinates), (A) value);
		}
	}

	public Iterable<long[]> availableCoordinates() {
		return new CoordinateSetToLongWrapper(values.keySet());
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

	public void setAxisAnnotation(int axis, int positionOnAxis, Object value) {
		if (annotation == null) {
			annotation = new DefaultAnnotation();
		}
		annotation.setAxisAnnotation(axis, positionOnAxis, value);
	}

	public boolean contains(long... coordinates) {
		return values.containsKey(new Coordinates(coordinates));
	}

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return 0;
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {

	}

	public org.jdmp.matrix.Matrix.EntryType getEntryType() {
		return null;
	}

}
