package org.jdmp.ehcache;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.Map;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.coordinates.Coordinates;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.stubs.AbstractMapToSparseMatrixWrapper;

public class SparseEhcacheMatrix<A> extends AbstractMapToSparseMatrixWrapper<A> {
	private static final long serialVersionUID = -7743149828558906127L;

	private transient Map<Coordinates, Object> values = null;

	public SparseEhcacheMatrix(Matrix m) throws MatrixException, IOException {
		super(m);
	}

	public SparseEhcacheMatrix(Matrix m, int maximumNumberOfEntries) throws MatrixException,
			IOException {
		super(m, maximumNumberOfEntries);
	}

	public SparseEhcacheMatrix(long... size) throws MatrixException, IOException {
		super(size);
	}

	@Override
	public Map<Coordinates, Object> getMap() {
		if (values == null) {
			try {
				values = new EhcacheMap<Coordinates, Object>("matrix" + System.nanoTime());
			} catch (IOException e) {
				throw new MatrixException(e);
			}
		}
		return values;
	}

	@Override
	public void setMap(Map<Coordinates, Object> map) {
		this.values = map;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		for (long[] c : availableCoordinates()) {
			s.writeObject(c);
			s.writeObject(getObject(c));
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		while (true) {
			try {
				long[] c = (long[]) s.readObject();
				Object o = s.readObject();
				setObject(o, c);
			} catch (OptionalDataException e) {
				return;
			}
		}
	}

}
