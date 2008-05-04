package org.jdmp.matrix.interfaces;

import java.util.Map;

import org.jdmp.matrix.Matrix;

public interface MapMatrix<K, V> extends Matrix<K>, Map<K, V> {

}
