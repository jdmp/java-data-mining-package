package org.jdmp.matrix.stubs;

import java.util.Map;

import org.jdmp.matrix.CoordinateIterator2D;
import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.GenericMatrix;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.implementations.basic.DefaultDenseObjectMatrix2D;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.util.MathUtil;

public abstract class AbstractMapToTiledMatrix2DWrapper<A> extends AbstractDenseMatrix<A> implements
		Wrapper<Map<Coordinates, GenericMatrix<A>>> {

	private long tileSize = 100;

	private long[] size = null;

	public AbstractMapToTiledMatrix2DWrapper(Matrix m) {
		this.size = Coordinates.copyOf(m.getSize());
		for (long[] c : m.allCoordinates()) {
			setObject(m.getObject(c), c);
		}
	}

	public AbstractMapToTiledMatrix2DWrapper(Matrix m, int maximumNumberOfEntries2) {
		this.size = Coordinates.copyOf(m.getSize());
		for (long[] c : m.allCoordinates()) {
			setObject(m.getObject(c), c);
		}
	}

	public AbstractMapToTiledMatrix2DWrapper(long... size) {
		this.size = Coordinates.copyOf(size);
	}

	@Override
	public A getObject(long... coordinates) throws MatrixException {
		Coordinates c = new Coordinates(coordinates[ROW] / tileSize, coordinates[COLUMN] / tileSize);
		Matrix m = getMap().get(c);
		if (m == null) {
			return null;
		} else {
			return (A) m.getObject(coordinates[ROW] % tileSize, coordinates[COLUMN] % tileSize);
		}
	}

	public Map<Coordinates, GenericMatrix<A>> getWrappedObject() {
		return getMap();
	}

	public abstract Map<Coordinates, GenericMatrix<A>> getMap();

	public void setWrappedObject(Map<Coordinates, GenericMatrix<A>> object) {
		setMap(object);
	}

	public abstract void setMap(Map<Coordinates, GenericMatrix<A>> map);

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public boolean contains(long... coordinates) {
		if (Coordinates.isSmallerThan(coordinates, size)) {
			Coordinates c = new Coordinates(coordinates[ROW] / tileSize, coordinates[COLUMN]
					/ tileSize);
			Matrix m = getMap().get(c);
			return m != null;
		} else {
			return false;
		}
	}

	public final double getDouble(long... coordinates) throws MatrixException {
		return MathUtil.getDouble(getObject(coordinates));
	}

	public final void setDouble(double v, long... coordinates) throws MatrixException {
		setObject(v, coordinates);
	}

	public void setObject(Object o, long... coordinates) throws MatrixException {
		Coordinates c = new Coordinates(coordinates[ROW] / tileSize, coordinates[COLUMN] / tileSize);
		GenericMatrix<A> m = getMap().get(c);
		if (m == null) {
			m = (GenericMatrix<A>) new DefaultDenseObjectMatrix2D(tileSize, tileSize);
		}
		m.setObject(o, coordinates[ROW] % tileSize, coordinates[COLUMN] % tileSize);
		getMap().put(c, m);
	}

	public EntryType getEntryType() {
		return EntryType.GENERIC;
	}

	public long[] getSize() {
		return size;
	}

	public long getTileSize() {
		return tileSize;
	}

	public void setTileSize(long tileSize) {
		this.tileSize = tileSize;
	}

}
