package org.jdmp.matrix.interfaces;

public interface MatrixIterators {

	public Iterable<long[]> coordinates(String selection);

	public Iterable<long[]> coordinates(int dimension, long... selection);

	public Iterable<long[]> allCoordinates();

	public Iterable<long[]> nonZeroCoordinates();

}
