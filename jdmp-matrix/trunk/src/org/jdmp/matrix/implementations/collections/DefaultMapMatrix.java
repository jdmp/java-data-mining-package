package org.jdmp.matrix.implementations.collections;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.matrix.stubs.AbstractMapMatrix;

public class DefaultMapMatrix<K, V> extends AbstractMapMatrix<K, V> {
	private static final long serialVersionUID = -1764575977190231155L;

	private Map<K, V> map = null;

	private Object matrixAnnotation = null;

	public DefaultMapMatrix() {
		this.map = new HashMap<K, V>();
	}

	public DefaultMapMatrix(Map<K, V> map) {
		this.map = map;
	}

	public Map<K, V> getMap() {
		return map;
	}

	public Object getMatrixAnnotation() {
		return matrixAnnotation;
	}

	public void setMatrixAnnotation(Object value) {
		matrixAnnotation = value;
	}

}
