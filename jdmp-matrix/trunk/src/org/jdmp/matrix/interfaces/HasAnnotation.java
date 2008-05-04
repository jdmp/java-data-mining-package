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

	public String getColumnLabel(int col);

	public String getRowLabel(int row);

	public Object getRowObject(int row);

	public Object getColumnObject(int col);

	public void setColumnLabel(int col, String label);

	public void setRowLabel(int row, String label);

	public void setRowObject(int row, Object o);

	public void setColumnObject(int col, Object o);

}
