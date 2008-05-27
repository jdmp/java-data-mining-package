package org.jdmp.matrix.implementations.collections;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.matrix.stubs.AbstractMapMatrix;

public class DefaultMapMatrix extends AbstractMapMatrix {
	private static final long serialVersionUID = -1764575977190231155L;

	private Map<Object, Object> map = null;

	public DefaultMapMatrix() {
		this.map = new HashMap<Object, Object>();
	}

	public DefaultMapMatrix(Map<Object, Object> map) {
		this.map = map;
	}

	public Map<Object, Object> getMap() {
		return map;
	}

}
