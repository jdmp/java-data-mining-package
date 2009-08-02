package org.jdmp.ehcache;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.Map;

import org.jdmp.matrix.GenericMatrix;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.coordinates.Coordinates;
import org.jdmp.matrix.stubs.AbstractMapToTiledMatrix2DWrapper;

public class TiledEhcacheMatrix2D<A> extends AbstractMapToTiledMatrix2DWrapper<A> {
	private static final long serialVersionUID = 4324063544046176423L;

	private transient Map<Coordinates, GenericMatrix<A>> values = null;

	public TiledEhcacheMatrix2D(long... size) {
		super(size);
	}

	public TiledEhcacheMatrix2D(Matrix source) {
		super(source);
	}

	@Override
	public Map<Coordinates, GenericMatrix<A>> getMap() {
		if (values == null) {
			try {
				values = new EhcacheMap<Coordinates, GenericMatrix<A>>();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return values;
	}

	@Override
	public void setMap(Map<Coordinates, GenericMatrix<A>> map) {
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
