package org.jdmp.matrix.interfaces;

import java.io.Serializable;

public interface Annotation extends Serializable {

	public void setAxisAnnotation(int axis, int positionOnAxis, Object value);

	public Object getAxisAnnotation(int axis, int positionOnAxis);

	public Object getAxisAnnotation(int axis);

	public void setAxisAnnotation(int axis, Object value);

	public Object getMatrixAnnotation();

	public void setMatrixAnnotation(Object matrixAnnotation);

	public Annotation clone();

}
