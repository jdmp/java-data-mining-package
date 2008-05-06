package org.jdmp.matrix.interfaces;

import java.util.Map;

import org.jdmp.matrix.GenericMatrix;

public interface MapMatrix<K, V> extends GenericMatrix<K>, Map<K, V> {

}
