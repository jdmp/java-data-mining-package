package org.jdmp.matrix.interfaces;

public interface HasAnnotation {

	public Annotation getAnnotation();

	public void setAnnotation(Annotation annotation);

	public Object getAxisAnnotation(int axis, int positionOnAxis);

	public Object getAxisAnnotation(int axis);

	public void setAxisAnnotation(int axis, int positionOnAxis, Object value);

	public void setAxisAnnotation(int axis, Object value);

	public Object getMatrixAnnotation();

	public void setMatrixAnnotation(Object annotation);

}
