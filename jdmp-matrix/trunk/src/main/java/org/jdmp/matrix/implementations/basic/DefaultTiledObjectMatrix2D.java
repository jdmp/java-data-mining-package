package org.jdmp.matrix.implementations.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.GenericMatrix;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.stubs.AbstractMapToTiledMatrix2DWrapper;

public class DefaultTiledObjectMatrix2D<A> extends AbstractMapToTiledMatrix2DWrapper<A> {
	private static final long serialVersionUID = 6745798685307431625L;

	private Map<Coordinates, GenericMatrix<A>> values = null;

	public DefaultTiledObjectMatrix2D(long... size) {
		super(size);
	}

	public DefaultTiledObjectMatrix2D(Matrix source) {
		super(source);
	}

	@Override
	public Map<Coordinates, GenericMatrix<A>> getMap() {
		if (values == null) {
			values = new HashMap<Coordinates, GenericMatrix<A>>();
		}
		return values;
	}

	@Override
	public void setMap(Map<Coordinates, GenericMatrix<A>> map) {
		this.values = map;
	}

}
