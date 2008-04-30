package org.jdmp.matrix.implementations.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.matrix.DefaultAnnotation;
import org.jdmp.matrix.CoordinateIterator2D;
import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.stubs.AbstractSparseObjectMatrix;
import org.jdmp.matrix.util.CoordinateSetToLongWrapper;

public class DefaultSparseObjectMatrix extends AbstractSparseObjectMatrix {
	private static final long serialVersionUID = -7139128532871448340L;

	private Map<Coordinates, Object> values = new HashMap<Coordinates, Object>();

	private DefaultAnnotation annotation = null;

	private long[] size = null;

	private int maximumNumberOfEntries = -1;

	private double defaultValue = 0.0;

	public DefaultSparseObjectMatrix(Matrix m) throws MatrixException {
		this.size = Coordinates.copyOf(m.getSize());
		for (long[] c : m.allCoordinates()) {
			setDouble(m.getDouble(c), c);
		}
	}

	public DefaultSparseObjectMatrix(Matrix m, int maximumNumberOfEntries) throws MatrixException {
		this.size = Coordinates.copyOf(m.getSize());
		this.maximumNumberOfEntries = maximumNumberOfEntries;
		for (long[] c : m.allCoordinates()) {
			setDouble(m.getDouble(c), c);
		}
	}

	public DefaultSparseObjectMatrix(long... size) {
		this.size = Coordinates.copyOf(size);
	}

	public DefaultSparseObjectMatrix(int maximumNumberOfEntries, long... size) {
		this.size = Coordinates.copyOf(size);
		this.maximumNumberOfEntries = maximumNumberOfEntries;
	}

	public long[] getSize() {
		return size;
	}
	
	public void setSize(long...size) {
      this.size = size;
    }

	public Object getObject(long... coordinates) {
		return values.get(new Coordinates(coordinates));
	}

	public long getValueCount() {
		return values.size();
	}

	public void setObject(Object value, long... coordinates) {
		while (maximumNumberOfEntries > 0 && values.size() > maximumNumberOfEntries) {
			values.remove(values.keySet().iterator().next());
		}
		if (Coordinates.isSmallerThan(coordinates, size)) {
			values.put(new Coordinates(coordinates), value);
		}
	}

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
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

}
