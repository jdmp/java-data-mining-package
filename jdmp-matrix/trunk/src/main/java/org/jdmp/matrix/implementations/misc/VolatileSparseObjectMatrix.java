package org.jdmp.matrix.implementations.misc;

import java.util.Map;

import org.jdmp.matrix.CoordinateIterator2D;
import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.stubs.AbstractSparseObjectMatrix;
import org.jdmp.matrix.util.CoordinateSetToLongWrapper;
import org.jdmp.matrix.util.collections.SoftHashMap;

public class VolatileSparseObjectMatrix extends AbstractSparseObjectMatrix {
	private static final long serialVersionUID = 392817709394048419L;

	private Map<Coordinates, Object> values = new SoftHashMap<Coordinates, Object>();

	private long[] size = null;

	public VolatileSparseObjectMatrix(Matrix m) throws MatrixException {
		this.size = Coordinates.copyOf(m.getSize());
		for (long[] c : m.allCoordinates()) {
			setDouble(m.getDouble(c), c);
		}
	}

	public boolean isTransient() {
		return true;
	}

	public VolatileSparseObjectMatrix(long... size) {
		this.size = Coordinates.copyOf(size);
	}

	public long[] getSize() {
		return size;
	}

	public Object getObject(long... coordinates) {
		return values.get(new Coordinates(coordinates));
	}

	public long getValueCount() {
		return values.size();
	}

	public void setObject(Object value, long... coordinates) {
		values.put(new Coordinates(coordinates), value);
	}

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public Iterable<long[]> entries() {
		return new CoordinateSetToLongWrapper(values.keySet());
	}

	public boolean contains(long... coordinates) {
		return values.containsKey(new Coordinates(coordinates));
	}

}
