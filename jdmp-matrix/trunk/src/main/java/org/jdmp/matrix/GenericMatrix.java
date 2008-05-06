package org.jdmp.matrix;

public interface GenericMatrix<A> extends Matrix {

  public A getObject(long... coordinates) throws MatrixException;
  
}
