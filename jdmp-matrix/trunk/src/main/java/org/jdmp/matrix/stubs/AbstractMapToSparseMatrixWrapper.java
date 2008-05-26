package org.jdmp.matrix.stubs;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.Map;

import org.jdmp.matrix.CoordinateIterator2D;
import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.DefaultAnnotation;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.interfaces.Annotation;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.util.CoordinateSetToLongWrapper;
import org.jdmp.matrix.util.MathUtil;


public abstract class AbstractMapToSparseMatrixWrapper<A> extends
    AbstractSparseMatrix<A> implements Wrapper<Map<Coordinates, Object>> {

  private Annotation annotation = null;

  private A defaultValue = null;

  private int maximumNumberOfEntries = -1;

  private long[] size = null;



  public AbstractMapToSparseMatrixWrapper(Matrix m) {
    this.size = Coordinates.copyOf(m.getSize());
    for (long[] c : m.allCoordinates()) {
      setDouble(m.getDouble(c), c);
    }
  }



  public AbstractMapToSparseMatrixWrapper(Matrix m, int maximumNumberOfEntries2) {
    this.size = Coordinates.copyOf(m.getSize());
    setMaximumNumberOfEntries(maximumNumberOfEntries);
    for (long[] c : m.allCoordinates()) {
      setDouble(m.getDouble(c), c);
    }
  }



  public AbstractMapToSparseMatrixWrapper(long... size) {
    this.size = Coordinates.copyOf(size);
  }



  public abstract Map<Coordinates, Object> getMap();



  public abstract void setMap(Map<Coordinates, Object> map);



  public final long[] getSize() {
    return size;
  }



  public final Map<Coordinates, Object> getWrappedObject() {
    return getMap();
  }



  public final void setWrappedObject(Map<Coordinates, Object> object) {
    setMap(object);
  }



  public final A getObject(long... coordinates) throws MatrixException {
    A v = (A) getMap().get(new Coordinates(coordinates));
    return v == null ? defaultValue : v;
  }



  public Iterable<long[]> allCoordinates() {
    return new CoordinateIterator2D(getSize());
  }



  public final boolean contains(long... coordinates) {
    return getMap().containsKey(new Coordinates(coordinates));
  }



  public final double getDouble(long... coordinates) throws MatrixException {
    return MathUtil.getDouble(getObject(coordinates));
  }



  public final void setDouble(double v, long... coordinates)
      throws MatrixException {
    setObject(v, coordinates);
  }



  public final void setObject(Object o, long... coordinates)
      throws MatrixException {
    while (maximumNumberOfEntries > 0
        && getMap().size() > maximumNumberOfEntries) {
      getMap().remove(getMap().keySet().iterator().next());
    }
    if (Coordinates.isSmallerThan(coordinates, getSize())) {
      getMap().put(new Coordinates(coordinates), (A) o);
    }
  }



  public final EntryType getEntryType() {
    return EntryType.GENERIC;
  }



  public final int getMaximumNumberOfEntries() {
    return maximumNumberOfEntries;
  }



  @Override
  public final long getValueCount() {
    return getMap().size();
  }



  public final Iterable<long[]> availableCoordinates() {
    return new CoordinateSetToLongWrapper(getMap().keySet());
  }



  public final void setMaximumNumberOfEntries(int maximumNumberOfEntries) {
    this.maximumNumberOfEntries = maximumNumberOfEntries;
  }



  public Object getMatrixAnnotation() {
    return annotation == null ? null : annotation.getMatrixAnnotation();
  }



  public void setMatrixAnnotation(Object value) {
    if (annotation == null) {
      annotation = new DefaultAnnotation();
    }
    annotation.setMatrixAnnotation(value);
  }



  public Object getAxisAnnotation(int axis, int positionOnAxis) {
    return annotation == null ? null : annotation.getAxisAnnotation(axis,
        positionOnAxis);
  }



  public void setAxisAnnotation(int axis, int positionOnAxis, Object value) {
    if (annotation == null) {
      annotation = new DefaultAnnotation();
    }
    annotation.setAxisAnnotation(axis, positionOnAxis, value);
  }



  



  public Object getAxisAnnotation(int axis) {
    return annotation == null ? null : annotation.getAxisAnnotation(axis);
  }



  public void setAxisAnnotation(int axis, Object value) {
    if (annotation == null) {
      annotation = new DefaultAnnotation();
    }
    annotation.setAxisAnnotation(axis, value);
  }
}
