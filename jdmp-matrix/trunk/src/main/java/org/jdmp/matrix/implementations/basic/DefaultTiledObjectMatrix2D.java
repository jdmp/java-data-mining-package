package org.jdmp.matrix.implementations.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.stubs.AbstractMapToTiledMatrix2DWrapper;

public class DefaultTiledObjectMatrix2D<A> extends AbstractMapToTiledMatrix2DWrapper<A> {
	private static final long serialVersionUID = 6745798685307431625L;

	private Map<Coordinates, Matrix> values = null;

	public DefaultTiledObjectMatrix2D(long... size) {
		super(size);
	}

	public DefaultTiledObjectMatrix2D(Matrix source) {
		super(source);
	}

	@Override
	public Map<Coordinates, Matrix> getMap() {
		if (values == null) {
			values = new HashMap<Coordinates, Matrix>();
		}
		return values;
	}

	@Override
	public void setMap(Map<Coordinates, Matrix> map) {
		this.values = map;
	}

	public static void main(String[] args) throws Exception {
		Matrix m = new DefaultTiledObjectMatrix2D<Object>(10, 10);
		m.setObject("test", 0, 0);
		System.out.println(m.toString());

	}

}
